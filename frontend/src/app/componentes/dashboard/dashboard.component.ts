import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiServicio } from '../../servicios/api.servicio';
import { AutenticacionServicio } from '../../servicios/autenticacion.servicio';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  usuarioActual: any;
  estadisticas = {
    totalMiembros: 0,
    membresiasActivas: 0,
    proximasVencer: 0,
    instructores: 0,
    clasesSemanales: 0
  };
  cargando = true;

  constructor(
    private apiServicio: ApiServicio,
    private autenticacionServicio: AutenticacionServicio,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.usuarioActual = this.autenticacionServicio.obtenerUsuario();
    this.cargarEstadisticas();
  }

  cargarEstadisticas(): void {
    // Cargar miembros
    this.apiServicio.listarMiembros().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.estadisticas.totalMiembros = respuesta.datos.length;
        }
      }
    });

    // Cargar membresías
    this.apiServicio.membresiasProximasVencer().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.estadisticas.proximasVencer = respuesta.datos.length;
        }
      }
    });

    // Cargar instructores
    this.apiServicio.listarInstructores().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.estadisticas.instructores = respuesta.datos.length;
        }
      }
    });

    // Cargar clases
    this.apiServicio.obtenerClases().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.estadisticas.clasesSemanales = respuesta.datos.length;
        }
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  cerrarSesion(): void {
    this.autenticacionServicio.cerrarSesion();
    this.router.navigate(['/login']);
  }
}
