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

  //totalIngresos: string = "0";
  totalVentas: string = "0";

totalProductos:  string = "0";
  constructor(
    private _dashboardServicio: DashBoardService
  ) { }

  mostrarGrafico(labelGrafico: any[], dataGrafico: any[]) {
    const chartBarras = new Chart('chartBarras', {
      type: 'bar',
      data: {
        labels: labelGrafico,
        datasets: [{
          label: 'Cant.',
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
      console.log('Respuesta del backend:', data);

      // Verificar que data.value existe antes de acceder a las propiedades
      if (data && data.status && data.value) {
        this.totalProductos = data.value.totalProductos || '0';
        this.totalVentas = data.value.totalVentas || '0';
        console.log("Valores asignados:", this.totalProductos, this.totalVentas);

        // Ahora que los datos están disponibles, crear el gráfico
        const arrayData: any[] = [this.totalVentas, this.totalProductos];  // Ejemplo de cómo usar los datos para el gráfico
        const labelTemp = ['Ventas', 'Productos'];  // Etiquetas del gráfico
        this.mostrarGrafico(labelTemp, arrayData);  // Llamar a la función para mostrar el gráfico
      } else {
        console.log("Estado de la respuesta es false o data.value no está definida:", data);
      }
    },
    error: (e) => {
      console.error('Error en la respuesta:', e);
    }
  });
}


}
