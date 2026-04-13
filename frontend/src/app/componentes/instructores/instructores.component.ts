import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-instructores',
  templateUrl: './instructores.component.html',
  styleUrls: ['./instructores.component.css']
})
export class InstructoresComponent implements OnInit {
  instructores: any[] = [];
  cargando = false;
  error = '';
  mostrarFormulario = false;
  editando = false;
  instructorSeleccionado: any = null;

  formData: any = {
    usuarioId: null,
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    especialidad: '',
    certificaciones: ''
  };

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarInstructores();
  }

  cargarInstructores(): void {
    this.cargando = true;
    this.apiServicio.obtenerInstructores().subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.instructores = respuesta.datos;
        } else {
          this.error = respuesta.mensaje;
        }
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error al cargar instructores';
        this.cargando = false;
      }
    });
  }

  abrirFormulario(): void {
    this.editando = false;
    this.formData = { 
      usuarioId: null, 
      nombre: '', 
      apellido: '', 
      email: '', 
      telefono: '', 
      especialidad: '', 
      certificaciones: '' 
    };
    this.mostrarFormulario = true;
  }

  editarInstructor(instructor: any): void {
    this.editando = true;
    this.instructorSeleccionado = instructor;
    this.formData = { ...instructor };
    this.mostrarFormulario = true;
  }

  guardarInstructor(): void {
    if (!this.formData.usuarioId || !this.formData.especialidad) {
      this.error = 'Completa usuario y especialidad';
      return;
    }

    if (this.editando) {
      this.apiServicio.actualizarInstructor(this.instructorSeleccionado.id, this.formData).subscribe({
        next: () => {
          this.cargarInstructores();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al actualizar instructor'
      });
    } else {
      this.apiServicio.crearInstructor(this.formData).subscribe({
        next: () => {
          this.cargarInstructores();
          this.mostrarFormulario = false;
        },
        error: () => this.error = 'Error al crear instructor'
      });
    }
  }

  eliminarInstructor(id: number): void {
    if (confirm('¿Eliminar este instructor?')) {
      this.apiServicio.eliminarInstructor(id).subscribe({
        next: () => this.cargarInstructores(),
        error: () => this.error = 'Error al eliminar instructor'
      });
    }
  }

  cerrarFormulario(): void {
    this.mostrarFormulario = false;
  }
}
