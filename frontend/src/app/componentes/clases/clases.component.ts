import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-clases',
  templateUrl: './clases.component.html',
  styleUrls: ['./clases.component.css']
})
export class ClasesComponent implements OnInit {
  clases: any[] = [];
  instructores: any[] = [];
  planes: any[] = [];
  cargando = false;
  error = '';
  mostrarFormulario = false;
  editando = false;
  claseSeleccionada: any = null;

  formData = {
    instructorId: null,
    nombre: '',
    diaSemana: '',
    horaInicio: '',
    duracionMinutos: '',
    capacidad: ''
  };

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarClases();
    this.cargarInstructores();
    this.cargarPlanes();
  }

  cargarClases(): void {
    this.cargando = true;
    this.apiServicio.obtenerClases().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.clases = respuesta.datos;
        }
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  cargarInstructores(): void {
    this.apiServicio.obtenerInstructores().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.instructores = respuesta.datos;
        }
      }
    });
  }

  cargarPlanes(): void {
    this.apiServicio.obtenerPlanes().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.planes = respuesta.datos;
        }
      }
    });
  }

  abrirFormulario(): void {
    this.editando = false;
    this.formData = { instructorId: null, nombre: '', diaSemana: '', horaInicio: '', duracionMinutos: '', capacidad: '' };
    this.mostrarFormulario = true;
  }

  editarClase(clase: any): void {
    this.editando = true;
    this.claseSeleccionada = clase;
    this.formData = { ...clase };
    this.mostrarFormulario = true;
  }

  guardarClase(): void {
    if (!this.formData.instructorId || !this.formData.nombre || !this.formData.diaSemana || !this.formData.capacidad) {
      this.error = 'Completa: instructor, nombre, día y capacidad';
      return;
    }

    const payload = {
      instructorId: parseInt(this.formData.instructorId, 10),
      nombre: this.formData.nombre,
      diaSemana: this.formData.diaSemana,
      horaInicio: this.formData.horaInicio,
      duracionMinutos: parseInt(this.formData.duracionMinutos, 10),
      capacidad: parseInt(this.formData.capacidad, 10)
    };

    if (this.editando) {
      this.apiServicio.actualizarClase(this.claseSeleccionada.id, payload).subscribe({
        next: () => {
          this.cargarClases();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al actualizar clase'
      });
    } else {
      this.apiServicio.crearClase(payload).subscribe({
        next: () => {
          this.cargarClases();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al crear clase'
      });
    }
  }

  eliminarClase(id: number): void {
    if (confirm('¿Eliminar esta clase?')) {
      this.apiServicio.eliminarClase(id).subscribe({
        next: () => this.cargarClases(),
        error: () => this.error = 'Error al eliminar clase'
      });
    }
  }

  cerrarFormulario(): void {
    this.mostrarFormulario = false;
  }
}
