import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';

import { DashBoardService } from 'src/app/Services/dash-board.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dash-board',
  standalone: true,
  templateUrl: './dash-board.component.html',
  styleUrls: ['./dash-board.component.css'],
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatGridListModule
  ]
})
export class DashBoardComponent implements OnInit {

  totalIngresos: string = "0";
  totalVentas: string = "0";
  totalProductos: string = "0";

  constructor(
    private _dashboardServicio: DashBoardService
  ) { }

  mostrarGrafico(labelGrafico: any[], dataGrafico: any[]) {
    const chartBarras = new Chart('chartBarras', {
      type: 'bar',
      data: {
        labels: labelGrafico,
        datasets: [{
          label: '# de Ventas',
          data: dataGrafico,
          backgroundColor: [
            'rgba(54,162,235,0.2)'
          ],
          borderColor: [
            'rgba(54,162,235,1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        maintainAspectRatio: false,
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  ngOnInit(): void {
    this._dashboardServicio.resumen().subscribe({
      next: (data) => {
        if (data.status) {
          this.totalIngresos = data.value.totalIngresos;
          this.totalVentas = data.value.totalVentas;
          this.totalProductos = data.value.totalProductos;

          const arrayData: any[] = data.value.ventasUltimaSemana;
          const labelTemp = arrayData.map((value) => value.fecha);
          const dataTemp = arrayData.map((value) => value.total);

          this.mostrarGrafico(labelTemp, dataTemp);
        }
      },
      error: (e) => {}
    });
  }

}
