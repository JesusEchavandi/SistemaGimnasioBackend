import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionServicio {
  private usuarioActual = new BehaviorSubject<any>(null);
  public usuarioActual$ = this.usuarioActual.asObservable();

  private token: string | null = null;
  private refreshToken: string | null = null;

  constructor() {
    this.cargarDelLocalStorage();
  }

  obtenerToken(): string | null {
    return this.token;
  }

  guardarTokens(accessToken: string, refreshToken: string): void {
    this.token = accessToken;
    this.refreshToken = refreshToken;
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  }

  obtenerUsuario(): any {
    return this.usuarioActual.value;
  }

  guardarUsuario(usuario: any): void {
    this.usuarioActual.next(usuario);
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  estaAutenticado(): boolean {
    return this.token !== null && this.token !== '';
  }

  cerrarSesion(): void {
    this.token = null;
    this.refreshToken = null;
    this.usuarioActual.next(null);
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('usuario');
  }

  private cargarDelLocalStorage(): void {
    const token = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');
    const usuario = localStorage.getItem('usuario');

    if (token) {
      this.token = token;
    }
    if (refreshToken) {
      this.refreshToken = refreshToken;
    }
    if (usuario) {
      this.usuarioActual.next(JSON.parse(usuario));
    }
  }
}
