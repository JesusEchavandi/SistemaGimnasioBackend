import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiServicio } from '../../servicios/api.servicio';
import { AutenticacionServicio } from '../../servicios/autenticacion.servicio';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  usuario = '';
  password = '';
  error = '';
  cargando = false;
  registroMode = false;

  registroForm = {
    usuario: '',
    email: '',
    password: '',
    nombre: '',
    apellido: ''
  };

  constructor(
    private apiServicio: ApiServicio,
    private autenticacionServicio: AutenticacionServicio,
    private router: Router
  ) { }

  login(): void {
    if (!this.usuario || !this.password) {
      this.error = 'Por favor completa todos los campos';
      return;
    }

    this.cargando = true;
    this.apiServicio.login(this.usuario, this.password).subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.autenticacionServicio.guardarTokens(
            respuesta.datos.accessToken,
            respuesta.datos.refreshToken
          );
          this.autenticacionServicio.guardarUsuario({
            usuario: this.usuario,
            nombre: this.usuario
          });
          this.router.navigate(['/dashboard']);
        } else {
          this.error = respuesta.mensaje || 'Error en la autenticación';
        }
      },
      error: (err) => {
        this.error = 'Error: ' + (err.error?.mensaje || 'No se pudo conectar con el servidor');
        this.cargando = false;
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  registrarse(): void {
    if (!this.registroForm.usuario || !this.registroForm.email || !this.registroForm.password) {
      this.error = 'Por favor completa todos los campos';
      return;
    }

    this.cargando = true;
    this.apiServicio.registrarse(this.registroForm).subscribe({
      next: (respuesta: any) => {
        if (respuesta.exito) {
          this.autenticacionServicio.guardarTokens(
            respuesta.datos.accessToken,
            respuesta.datos.refreshToken
          );
          this.router.navigate(['/dashboard']);
        } else {
          this.error = respuesta.mensaje || 'Error en el registro';
        }
      },
      error: (err) => {
        this.error = 'Error: ' + (err.error?.mensaje || 'Error al registrarse');
        this.cargando = false;
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  toggleRegistro(): void {
    this.registroMode = !this.registroMode;
    this.error = '';
  }
}
