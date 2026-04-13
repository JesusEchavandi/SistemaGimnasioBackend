import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-membresias',
  templateUrl: './membresias.component.html',
  styleUrls: ['./membresias.component.css']
})
export class MembresiasComponent implements OnInit {
  membresias: any[] = [];
  planes: any[] = [];
  miembros: any[] = [];
  
  nuevaMembresia = {
    miembroId: null,
    planId: null,
    fechaInicio: ''
  };
  
  mostrarFormulario = false;
  cargando = false;
  mensaje = '';

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarData();
  }

  cargarData(): void {
    this.cargando = true;
    
    // Cargar membresías
    this.apiServicio.membresiasProximasVencer().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.membresias = respuesta.datos;
        }
      }
    });

    // Cargar planes
    this.apiServicio.obtenerPlanes().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.planes = respuesta.datos;
        }
      }
    });

    // Cargar miembros
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

  crearMembresia(): void {
    if (!this.nuevaMembresia.miembroId || !this.nuevaMembresia.planId || !this.nuevaMembresia.fechaInicio) {
      this.mensaje = '❌ Por favor selecciona miembro, plan y fecha de inicio';
      return;
    }

    this.cargando = true;
    this.apiServicio.crearMembresia(this.nuevaMembresia).subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.mensaje = '✅ Membresía creada exitosamente';
          this.cargarData();
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

  renovarMembresia(id: number): void {
    if (!confirm('¿Deseas renovar esta membresía?')) return;

    this.apiServicio.renovarMembresia(id).subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.mensaje = '✅ Membresía renovada';
          this.cargarData();
        } else {
          this.mensaje = '❌ ' + respuesta.mensaje;
        }
      },
      error: (err) => {
        this.mensaje = '❌ Error al renovar';
      }
    });
  }

  limpiarFormulario(): void {
    this.nuevaMembresia = { miembroId: null, planId: null, fechaInicio: '' };
  }

  toggleFormulario(): void {
    this.mostrarFormulario = !this.mostrarFormulario;
    this.mensaje = '';
  }
}
