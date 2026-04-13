import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiServicio {
  private baseUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) { }

  // Autenticación
  registrarse(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/registro`, datos);
  }

  login(usuario: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, { nombreUsuario: usuario, clave: password });
  }

  refrescarToken(refreshToken: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/refresh`, { refreshToken });
  }

  // Miembros
  crearMiembro(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/miembros`, datos);
  }

  listarMiembros(): Observable<any> {
    return this.http.get(`${this.baseUrl}/miembros`);
  }

  obtenerMiembro(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/miembros/${id}`);
  }

  actualizarMiembro(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/miembros/${id}`, datos);
  }

  eliminarMiembro(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/miembros/${id}`);
  }

  // Planes
  crearPlan(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/planes`, datos);
  }

  obtenerPlanes(): Observable<any> {
    return this.http.get(`${this.baseUrl}/planes`);
  }

  obtenerPlan(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/planes/${id}`);
  }

  actualizarPlan(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/planes/${id}`, datos);
  }

  eliminarPlan(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/planes/${id}`);
  }

  // Membresías
  crearMembresia(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/membresias`, datos);
  }

  listarMembresias(): Observable<any> {
    return this.http.get(`${this.baseUrl}/membresias`);
  }

  obtenerMembresia(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/membresias/${id}`);
  }

  renovarMembresia(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/membresias/${id}/renovar`, {});
  }

  membresiasProximasVencer(): Observable<any> {
    return this.http.get(`${this.baseUrl}/membresias/proximas-vencer`);
  }

  // Instructores
  crearInstructor(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/instructores`, datos);
  }

  obtenerInstructores(): Observable<any> {
    return this.http.get(`${this.baseUrl}/instructores`);
  }

  listarInstructores(): Observable<any> {
    return this.http.get(`${this.baseUrl}/instructores`);
  }

  obtenerInstructor(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/instructores/${id}`);
  }

  actualizarInstructor(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/instructores/${id}`, datos);
  }

  eliminarInstructor(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/instructores/${id}`);
  }

  // Clases
  crearClase(datos: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/clases`, datos);
  }

  obtenerClases(): Observable<any> {
    return this.http.get(`${this.baseUrl}/clases`);
  }

  listarClases(): Observable<any> {
    return this.http.get(`${this.baseUrl}/clases`);
  }

  obtenerClase(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/clases/${id}`);
  }

  actualizarClase(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/clases/${id}`, datos);
  }

  eliminarClase(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/clases/${id}`);
  }

  // Reportes
  obtenerReporteAsistencias(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reportes/asistencias`);
  }

  obtenerReporteMembresias(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reportes/membresias`);
  }

  reporteMembresias(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reportes/membresias`);
  }

  reporteAsistencia(): Observable<any> {
    return this.http.get(`${this.baseUrl}/reportes/asistencia`);
  }
}
