/* =================================================================
 * src/app/Components/layout/Modales/modal-detalle-venta/
 *        modal-detalle-venta.component.ts
 * ================================================================= */
import { Component, OnInit, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';

import { Venta } from 'src/app/Interfaces/venta';
import { DetalleVenta } from 'src/app/Interfaces/detalle-venta';

@Component({
  selector: 'app-modal-detalle-venta',
  standalone: true,
  templateUrl: './modal-detalle-venta.component.html',
  styleUrls: ['./modal-detalle-venta.component.css'],
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatGridListModule,
    MatButtonModule,
    MatTableModule
  ]
})
export class ModalDetalleVentaComponent implements OnInit {

  /* ---------- Cabecera ---------- */
  fechaRegistro = '';
  numeroDocumento = '';
  tipoPago = '';
  total = '';

  /* ---------- Detalle ---------- */
  detalleVenta: DetalleVenta[] = [];
  columnasTabla: string[] = ['descripcionProducto', 'cantidad', 'precioTexto', 'totalTexto'];

  constructor(@Inject(MAT_DIALOG_DATA) private _venta: Venta) {

    /* Formateo de fecha solo DD/MM/YYYY */
    const fecha = new Date(_venta.fechaRegistro ?? '');
    this.fechaRegistro = fecha.toLocaleDateString('es-PE', {
      year : 'numeric',
      month: '2-digit',
      day  : '2-digit'
    });

    this.numeroDocumento = _venta.numeroDocumento ?? '';
    this.tipoPago        = _venta.tipoPago        ?? '';
    this.total           = (_venta.totalTexto ?? '0.00');

    /* Mapear detalle para la tabla */
    this.detalleVenta = (_venta.detalleVenta ?? []).map(d => ({
      /*  datos originales  */
      idProducto          : d.producto?.idProducto ?? d.idProducto,
      cantidad            : d.cantidad ?? 0,
      precio              : d.precio   ?? 0,
      total               : d.total    ?? 0,
      /*  campos para vista  */
      descripcionProducto : d.producto?.nombre ?? '—',
      precioTexto         : (d.precio ?? 0).toFixed(2),
      totalTexto          : (d.total  ?? 0).toFixed(2)
    }));
  }

  ngOnInit(): void {}
}
