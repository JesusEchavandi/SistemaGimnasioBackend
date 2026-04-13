import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-planes',
  templateUrl: './planes.component.html',
  styleUrls: ['./planes.component.css']
})
export class PlanesComponent implements OnInit {
  planes: any[] = [];
  cargando = false;
  error = '';
  mostrarFormulario = false;
  editando = false;
  planeSeleccionado: any = null;

  formData = {
    nombre: '',
    descripcion: '',
    precioMensual: '',
    duracionDias: ''
  };

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarPlanes();
  }

  cargarPlanes(): void {
    this.cargando = true;
    this.apiServicio.obtenerPlanes().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.planes = respuesta.datos;
        } else {
          this.error = respuesta.mensaje;
        }
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar planes';
        this.cargando = false;
      }
    });
  }

  abrirFormulario(): void {
    this.editando = false;
    this.formData = { nombre: '', descripcion: '', precioMensual: '', duracionDias: '' };
    this.mostrarFormulario = true;
  }

  editarPlan(plan: any): void {
    this.editando = true;
    this.planeSeleccionado = plan;
    this.formData = { ...plan };
    this.mostrarFormulario = true;
  }

  guardarPlan(): void {
    if (!this.formData.nombre || !this.formData.precioMensual) {
      this.error = 'Completa todos los campos';
      return;
    }

    const payload = {
      nombre: this.formData.nombre,
      descripcion: this.formData.descripcion,
      precioMensual: parseFloat(this.formData.precioMensual),
      duracionDias: parseInt(this.formData.duracionDias, 10)
    };

    if (this.editando) {
      this.apiServicio.actualizarPlan(this.planeSeleccionado.id, payload).subscribe({
        next: () => {
          this.cargarPlanes();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al actualizar plan'
      });
    } else {
      this.apiServicio.crearPlan(payload).subscribe({
        next: () => {
          this.cargarPlanes();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al crear plan'
      });
    }
  }

  eliminarPlan(id: number): void {
    if (confirm('¿Eliminar este plan?')) {
      this.apiServicio.eliminarPlan(id).subscribe({
        next: () => this.cargarPlanes(),
        error: () => this.error = 'Error al eliminar plan'
      });
    }
  }

  cerrarFormulario(): void {
    this.mostrarFormulario = false;
  }
}
