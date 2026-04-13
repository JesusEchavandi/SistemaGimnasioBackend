import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-miembros',
  templateUrl: './miembros.component.html',
  styleUrls: ['./miembros.component.css']
})
export class MiembrosComponent implements OnInit {
  miembros: any[] = [];
  nuevoMiembro: any = {
    usuarioId: null,
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    numeroMembresia: '',
    telefonoContacto: '',
    fechaNacimiento: '',
    sexo: '',
    telefonoEmergencia: ''
  };
  mostrarFormulario = false;
  cargando = false;
  mensaje = '';

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarMiembros();
  }

  cargarMiembros(): void {
    this.cargando = true;
    this.apiServicio.listarMiembros().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.miembros = respuesta.datos;
        }
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  crearMiembro(): void {
    if (!this.nuevoMiembro.usuarioId || !this.nuevoMiembro.numeroMembresia || !this.nuevoMiembro.telefonoContacto || !this.nuevoMiembro.sexo) {
      this.mensaje = '❌ Por favor completa: usuario, membresía, teléfono y sexo';
      return;
    }

    this.cargando = true;
    this.apiServicio.crearMiembro(this.nuevoMiembro).subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.mensaje = '✅ Miembro creado exitosamente';
          this.cargarMiembros();
          this.limpiarFormulario();
          this.mostrarFormulario = false;
        } else {
          this.mensaje = '❌ ' + respuesta.mensaje;
        }
      },
      error: (err) => {
        this.mensaje = '❌ Error: ' + (err.error?.mensaje || 'No se pudo crear');
        this.cargando = false;
      }
    });
  }

  limpiarFormulario(): void {
    this.nuevoMiembro = {
      usuarioId: null,
      numeroMembresia: '',
      telefonoContacto: '',
      fechaNacimiento: '',
      sexo: '',
      telefonoEmergencia: ''
    };
  }

  toggleFormulario(): void {
    this.mostrarFormulario = !this.mostrarFormulario;
    this.mensaje = '';
  }
}
