import { Component, OnInit } from '@angular/core';
import { ApiServicio } from '../../servicios/api.servicio';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements OnInit {
  reportes: any[] = [];
  cargando = false;
  error = '';
  tipoReporte = 'asistencias';

  constructor(private apiServicio: ApiServicio) { }

  ngOnInit(): void {
    this.cargarReportes();
  }

  cargarReportes(): void {
    this.cargando = true;
    if (this.tipoReporte === 'asistencias') {
      this.apiServicio.obtenerReporteAsistencias().subscribe({
        next: (respuesta: any) => {
          if (respuesta.exito) {
            this.reportes = respuesta.datos;
          }
          this.cargando = false;
        },
        error: () => this.cargando = false
      });
    } else if (this.tipoReporte === 'membresias') {
      this.apiServicio.obtenerReporteMembresias().subscribe({
        next: (respuesta: any) => {
          if (respuesta.exito) {
            this.reportes = respuesta.datos;
          }
          this.cargando = false;
        },
        error: () => this.cargando = false
      });
    }
  }

  cambiarTipoReporte(tipo: string): void {
    this.tipoReporte = tipo;
    this.cargarReportes();
  }

  exportarPDF(): void {
    alert('Funcionalidad de exportar PDF en desarrollo');
  }

  exportarExcel(): void {
    alert('Funcionalidad de exportar Excel en desarrollo');
  }
}
