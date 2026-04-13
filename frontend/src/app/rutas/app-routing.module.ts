import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../componentes/autenticacion/login.component';
import { DashboardComponent } from '../componentes/dashboard/dashboard.component';
import { MiembrosComponent } from '../componentes/miembros/miembros.component';
import { MembresiasComponent } from '../componentes/membresias/membresias.component';
import { PlanesComponent } from '../componentes/planes/planes.component';
import { InstructoresComponent } from '../componentes/instructores/instructores.component';
import { ClasesComponent } from '../componentes/clases/clases.component';
import { ReportesComponent } from '../componentes/reportes/reportes.component';
import { AutenticacionGuardia } from './autenticacion.guardia';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'miembros', 
    component: MiembrosComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'membresias', 
    component: MembresiasComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'planes', 
    component: PlanesComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'instructores', 
    component: InstructoresComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'clases', 
    component: ClasesComponent,
    canActivate: [AutenticacionGuardia]
  },
  { 
    path: 'reportes', 
    component: ReportesComponent,
    canActivate: [AutenticacionGuardia]
  },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
