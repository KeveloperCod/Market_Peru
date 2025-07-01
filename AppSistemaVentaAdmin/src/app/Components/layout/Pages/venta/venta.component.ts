import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';

/* Angular Material */
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';

/* Servicios y modelos */
import { ProductoService } from 'src/app/Services/producto.service';
import { VentaService } from 'src/app/Services/venta.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

import { Venta } from 'src/app/Interfaces/venta';
import { DetalleVenta } from 'src/app/Interfaces/detalle-venta';
import { ProductoConCategoria } from 'src/app/Interfaces/ProductosConCategoria';
import { DetalleVentaRequest } from 'src/app/Interfaces/detalle-venta-request';
import { VentaResponseDTO } from 'src/app/Interfaces/venta-response-dto';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-venta',
  standalone: true,
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatIconModule,
    MatButtonModule,
    MatSelectModule,
    MatGridListModule,
    MatTableModule
  ]
})
export class VentaComponent implements OnInit {

  listaProductos: ProductoConCategoria[] = [];
  listaProductoFiltro: ProductoConCategoria[] = [];
  listaProductosParaVenta: DetalleVenta[] = [];
  bloquearBotonRegistrar = false;

  productoSeleccionado?: ProductoConCategoria;
  tipoDePagoPorDefecto = 'Efectivo';
  totalPagar = 0;

  formularioProductoVenta: FormGroup;
  columnasTabla: string[] = ['producto', 'cantidad', 'precio', 'total', 'acciones'];
  datosDetalleVenta = new MatTableDataSource<DetalleVenta>();

  constructor(
    private fb: FormBuilder,
    private _productoServicio: ProductoService,
    private _ventaServicio: VentaService,
    private _utilidadServicio: UtilidadService
  ) {
    this.formularioProductoVenta = this.fb.group({
      producto: ['', Validators.required],
      cantidad: ['', [Validators.required, Validators.min(1)]]
    });

    this._productoServicio.listaConCategoria().subscribe({
      next: (prods) => {
        this.listaProductos = prods.filter(p => p.esActivo && p.stock > 0);
      },
      error: (e) => console.error('Error al cargar productos', e)
    });

    this.formularioProductoVenta.get('producto')?.valueChanges.subscribe(value => {
      this.listaProductoFiltro = this.retornarProductoPorFiltro(value);
    });
  }

  ngOnInit(): void {}

  /* ---------- helpers ---------- */

  private retornarProductoPorFiltro(busqueda: any): ProductoConCategoria[] {
    if (!busqueda) { return this.listaProductos; }
    const valor = typeof busqueda === 'string'
      ? busqueda.toLowerCase()
      : (busqueda.nombreProducto ?? '').toLowerCase();
    return this.listaProductos.filter(p => p.nombreProducto.toLowerCase().includes(valor));
  }

  mostrarProducto(prod: ProductoConCategoria): string {
    return prod?.nombreProducto ?? '';
  }

  productoParaVenta(event: any) {
    this.productoSeleccionado = event.option.value as ProductoConCategoria;
  }

  /* ---------- Agregar producto ---------- */

  agregarProductoParaVenta() {
    const cantidad = +this.formularioProductoVenta.value.cantidad;
    if (!this.productoSeleccionado || cantidad <= 0) { return; }

    const existente = this.listaProductosParaVenta.find(d => d.idProducto === this.productoSeleccionado!.idProducto);

    if (existente) {
      existente.cantidad += cantidad;
      const precioUnit = parseFloat(existente.precioTexto);
      existente.totalTexto = (precioUnit * existente.cantidad).toFixed(2);
    } else {
      const precio = this.productoSeleccionado!.precio;
      const total  = cantidad * precio;
      this.listaProductosParaVenta.push({
        idProducto: this.productoSeleccionado!.idProducto,
        descripcionProducto: this.productoSeleccionado!.nombreProducto,
        cantidad,
        precioTexto: precio.toFixed(2),
        totalTexto: total.toFixed(2)
      });
    }

    this.actualizarTablaYTotal();

    /* limpiar */
    this.formularioProductoVenta.reset();
    this.productoSeleccionado = undefined;
  }

  /* ---------- Eliminar producto ---------- */

  eliminarProducto(det: DetalleVenta) {
    this.listaProductosParaVenta = this.listaProductosParaVenta.filter(d => d.idProducto !== det.idProducto);
    this.actualizarTablaYTotal();
  }

  /* ---------- Registrar venta ---------- */

  registrarVenta() {
    if (this.listaProductosParaVenta.length === 0) { return; }
    this.bloquearBotonRegistrar = true;

    const detalleParaApi: DetalleVentaRequest[] = this.listaProductosParaVenta.map(d => ({
      producto: { idProducto: d.idProducto },
      cantidad: d.cantidad,
      precio: parseFloat(d.precioTexto)
    }));

    const request: Venta = {
      tipoPago: this.tipoDePagoPorDefecto,
      totalTexto: this.totalPagar.toFixed(2),
      detalleVenta: detalleParaApi as any
    };

    this._ventaServicio.registrar(request).subscribe({
      next: (dto: VentaResponseDTO) => {
        this.bloquearBotonRegistrar = false;
        Swal.fire({
          icon: 'success',
          title: 'Venta Registrada',
          text: `Número de Venta: ${dto.numeroDocumento}`
        });

        /* limpiar todo */
        this.listaProductosParaVenta = [];
        this.actualizarTablaYTotal();
      },
      error: (err) => {
        this.bloquearBotonRegistrar = false;
        console.error('Error al registrar la venta:', err);
        this._utilidadServicio.mostrarAlerta('No se pudo registrar la venta', 'Ooops');
      }
    });
  }

  /* ---------- util ---------- */

  private actualizarTablaYTotal() {
    this.datosDetalleVenta.data = [...this.listaProductosParaVenta];
    this.totalPagar = this.listaProductosParaVenta
      .reduce((acc, d) => acc + parseFloat(d.totalTexto), 0);
  }
}
