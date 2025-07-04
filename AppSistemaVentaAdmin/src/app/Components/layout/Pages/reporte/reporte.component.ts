import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { CommonModule } from '@angular/common';
import * as moment from 'moment';
import * as XLSX from 'xlsx';

import { Reporte } from 'src/app/Interfaces/reporte';
import { VentaService } from 'src/app/Services/venta.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

export const MY_DATA_FORMATS = {
  parse: { dateInput: 'DD/MM/YYYY' },
  display: { dateInput: 'DD/MM/YYYY', monthYearLabel: 'MMMM YYYY' }
};

@Component({
  selector: 'app-reporte',
  standalone: true,
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css'],
  providers: [{ provide: MAT_DATE_FORMATS, useValue: MY_DATA_FORMATS }],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatTableModule,
    MatPaginatorModule
  ]
})
export class ReporteComponent implements OnInit, AfterViewInit {

  formularioFiltro: FormGroup;
  listaVentasReporte: Reporte[] = [];
  columnasTabla: string[] = [
    'fechaRegistro',
    'numeroDocumento',
    'tipoPago',
    'totalVenta',
    'producto',
    'cantidad',
    'precio',
    'totalProducto'
  ];
  dataVentaReporte = new MatTableDataSource<Reporte>([]);
  @ViewChild(MatPaginator) paginacionTabla!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private _ventaServicio: VentaService,
    private _utilidadServicio: UtilidadService
  ) {
    this.formularioFiltro = this.fb.group({
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.dataVentaReporte.paginator = this.paginacionTabla;
  }

  /* ---------- Buscar ventas ---------- */
  buscarVentas(): void {
    const fechaInicioISO = moment(this.formularioFiltro.value.fechaInicio).format('YYYY-MM-DD');
    const fechaFinISO = moment(this.formularioFiltro.value.fechaFin).format('YYYY-MM-DD');

    if (fechaInicioISO === 'Invalid date' || fechaFinISO === 'Invalid date') {
      this._utilidadServicio.mostrarAlerta('Debes ingresar ambas fechas', 'Oops!');
      return;
    }

    this._ventaServicio.reporte(fechaInicioISO, fechaFinISO).subscribe({
      next: (ventas: any[]) => {
        if (!ventas || ventas.length === 0) {
          this.listaVentasReporte = [];
          this.dataVentaReporte.data = [];
          this._utilidadServicio.mostrarAlerta('No se encontraron datos', 'Oops!');
          return;
        }

        this.listaVentasReporte = ventas.flatMap(v =>
          v.detalleVenta.map((d: any) => ({
            fechaRegistro: moment(v.fechaRegistro).format('DD/MM/YYYY HH:mm'),
            numeroDocumento: v.numeroDocumento,
            tipoPago: v.tipoPago,
            totalVenta: v.total.toFixed(2),
            producto: d.producto.nombre,
            cantidad: d.cantidad,
            precio: d.precio.toFixed(2),
            totalProducto: d.total.toFixed(2)
          }))
        );

        this.dataVentaReporte.data = this.listaVentasReporte;
      },
      error: () => {
        this._utilidadServicio.mostrarAlerta('Ocurrió un error al obtener los datos', 'Error');
      }
    });
  }

  /* ---------- Exportar a Excel ---------- */
  exportarExcel(): void {
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.json_to_sheet(this.listaVentasReporte);
    XLSX.utils.book_append_sheet(wb, ws, 'Reporte');
    XLSX.writeFile(wb, 'Reporte_Ventas.xlsx');
  }
}
