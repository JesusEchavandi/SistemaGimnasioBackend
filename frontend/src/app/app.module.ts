import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './rutas/app-routing.module';
import { LoginComponent } from './componentes/autenticacion/login.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { MiembrosComponent } from './componentes/miembros/miembros.component';
import { MembresiasComponent } from './componentes/membresias/membresias.component';
import { PlanesComponent } from './componentes/planes/planes.component';
import { InstructoresComponent } from './componentes/instructores/instructores.component';
import { ClasesComponent } from './componentes/clases/clases.component';
import { ReportesComponent } from './componentes/reportes/reportes.component';
import { InterceptorJwt } from './interceptores/jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    MiembrosComponent,
    MembresiasComponent,
    PlanesComponent,
    InstructoresComponent,
    ClasesComponent,
    ReportesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorJwt,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
