import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';

import * as moment from 'moment';

import { ModalDetalleVentaComponent } from '../../Modales/modal-detalle-venta/modal-detalle-venta.component';
import { VentaService } from 'src/app/Services/venta.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';
import { VentaResponseDTO } from 'src/app/Interfaces/venta-response-dto';

export const MY_DATA_FORMATS = {
  parse: { dateInput: 'DD/MM/YYYY' },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
  }
};

@Component({
  selector: 'app-historial-venta',
  standalone: true,
  templateUrl: './historial-venta.component.html',
  styleUrls: ['./historial-venta.component.css'],
  providers: [{ provide: MAT_DATE_FORMATS, useValue: MY_DATA_FORMATS }],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatTooltipModule
  ]
})
export class HistorialVentaComponent implements OnInit, AfterViewInit {

  formularioBusqueda: FormGroup;
  columnasTabla: string[] = ['fechaRegistro', 'numeroDocumento', 'tipoPago', 'total', 'accion'];
  datosListaVenta = new MatTableDataSource<VentaResponseDTO>([]);

    opcionesBusqueda = [
    { value: 'fecha', descripcion: 'Por Fecha' },
    { value: 'numero', descripcion: 'Por Número' }
  ];

  @ViewChild(MatPaginator) paginacionTabla!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private _ventaServicio: VentaService,
    private _utilidadServicio: UtilidadService
  ) {
    this.formularioBusqueda = this.fb.group({
      buscarPor: ['fecha'],
      numero: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
  }

  ngOnInit(): void {
    this.cargarHistorialCompleto();   // carga inicial
  }

  ngAfterViewInit(): void {
    this.datosListaVenta.paginator = this.paginacionTabla;
  }

  /* ---------- carga inicial ---------- */
  private cargarHistorialCompleto(): void {
    this._ventaServicio.historial().subscribe({
      next: (ventas) => {
        ventas.forEach(v => v.totalTexto = `S/ ${v.total.toFixed(2)}`);
        this.datosListaVenta.data = ventas;
      },
      error: () => {
        this._utilidadServicio.mostrarAlerta('No se pudo obtener historial', 'Oops!');
      }
    });
  }

  /* ---------- filtro avanzado ---------- */
  buscarVentas() {
    let _fechaInicio = '';
    let _fechaFin = '';

    if (this.formularioBusqueda.value.buscarPor === 'fecha') {
      _fechaInicio = moment(this.formularioBusqueda.value.fechaInicio).format('DD/MM/YYYY');
      _fechaFin = moment(this.formularioBusqueda.value.fechaFin).format('DD/MM/YYYY');
      if (_fechaInicio === 'Invalid date' || _fechaFin === 'Invalid date') {
        this._utilidadServicio.mostrarAlerta('Debes ingresar ambas fechas', 'Oops!');
        return;
      }
    }

    this._ventaServicio.historialFiltrado(
      this.formularioBusqueda.value.buscarPor,
      this.formularioBusqueda.value.numero,
      _fechaInicio,
      _fechaFin
    ).subscribe({
      next: (resp) => {
        if (resp.status) {
          resp.value.forEach((v: VentaResponseDTO) => v.totalTexto = `S/ ${v.total.toFixed(2)}`);
          this.datosListaVenta.data = resp.value;
        } else {
          this._utilidadServicio.mostrarAlerta('No se encontraron datos', 'Oops!');
        }
      },
      error: () => {}
    });
  }

  aplicarFiltroTabla(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.datosListaVenta.filter = valor.trim().toLowerCase();
  }

  verDetalleVenta(venta: VentaResponseDTO) {
    this.dialog.open(ModalDetalleVentaComponent, {
      data: venta,
      disableClose: true,
      width: '700px'
    });
  }
}
