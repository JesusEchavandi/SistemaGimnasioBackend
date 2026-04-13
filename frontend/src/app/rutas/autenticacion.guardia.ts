import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AutenticacionServicio } from '../servicios/autenticacion.servicio';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionGuardia implements CanActivate {

  constructor(
    private autenticacionServicio: AutenticacionServicio,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.autenticacionServicio.estaAutenticado()) {
      return true;
    }

    // No autenticado, redirigir a login
    this.router.navigate(['/login']);
    return false;
  }
}
