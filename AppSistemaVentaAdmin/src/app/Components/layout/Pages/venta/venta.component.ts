import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';

import { ProductoService } from 'src/app/Services/producto.service';
import { VentaService } from 'src/app/Services/venta.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

import { Venta } from 'src/app/Interfaces/venta';
import { DetalleVenta } from 'src/app/Interfaces/detalle-venta';
import { ProductoConCategoria } from 'src/app/Interfaces/ProductosConCategoria';

import { MatTableDataSource } from '@angular/material/table';
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
  bloquearBotonRegistrar: boolean = false;

  productoSeleccionado!: ProductoConCategoria;
  tipoDePagoPordDefecto: string = "Efectivo";
  totalPagar: number = 0;

  forumularioProductoVenta: FormGroup;
  columnasTabla: string[] = ['producto', 'cantidad', 'precio', 'total', 'acciones'];
  datosDetalleVenta = new MatTableDataSource(this.listaProductosParaVenta);

  constructor(
    private fb: FormBuilder,
    private _productoServicio: ProductoService,
    private _ventaServicio: VentaService,
    private _utilidadServicio: UtilidadService
  ) {
    this.forumularioProductoVenta = this.fb.group({
      producto: ['', Validators.required],
      cantidad: ['', Validators.required]
    });

    this._productoServicio.listaConCategoria().subscribe({
      next: (productos) => {
        console.log('Productos con categoría cargados', productos);
        this.listaProductos = productos.filter(p => p.esActivo && p.stock > 0);
      },
      error: (e) => {
        console.log('Error al cargar productos con categoría', e);
      }
    });

    this.forumularioProductoVenta.get('producto')?.valueChanges.subscribe(value => {
      this.listaProductoFiltro = this.retornarProductoPorFiltro(value);
    });
  }

  ngOnInit(): void {}

  retornarProductoPorFiltro(busqueda: any): ProductoConCategoria[] {
    const valorBuscado = typeof busqueda === "string"
      ? busqueda.toLowerCase()
      : busqueda.nombreProducto.toLowerCase();
    return this.listaProductos.filter(item =>
      item.nombreProducto.toLowerCase().includes(valorBuscado)
    );
  }

  mostrarProducto(producto: ProductoConCategoria): string {
    return producto?.nombreProducto ?? '';
  }

  productoParaVenta(event: any) {
    this.productoSeleccionado = event.option.value;
  }

  agregarProductoParaVenta() {
    const _cantidad: number = this.forumularioProductoVenta.value.cantidad;
    const _precio: number = this.productoSeleccionado.precio;
    const _total: number = _cantidad * _precio;
    this.totalPagar += _total;

    this.listaProductosParaVenta.push({
      idProducto: this.productoSeleccionado.idProducto,
      descripcionProducto: this.productoSeleccionado.nombreProducto,
      cantidad: _cantidad,
      precioTexto: _precio.toFixed(2),
      totalTexto: _total.toFixed(2),
    });

    this.datosDetalleVenta = new MatTableDataSource(this.listaProductosParaVenta);

    this.forumularioProductoVenta.patchValue({
      producto: '',
      cantidad: ''
    });
  }

  eliminarProducto(detalle: DetalleVenta) {
    this.totalPagar -= parseFloat(detalle.totalTexto);
    this.listaProductosParaVenta = this.listaProductosParaVenta.filter(
      f => f.idProducto !== detalle.idProducto
    );
    this.datosDetalleVenta = new MatTableDataSource(this.listaProductosParaVenta);
  }

  registrarVenta() {
  if (this.listaProductosParaVenta.length > 0) {
    this.bloquearBotonRegistrar = true;
    const request: Venta = {
      tipoPago: this.tipoDePagoPordDefecto,
      totalTexto: this.totalPagar.toFixed(2),
      detalleVenta: this.listaProductosParaVenta
    };

    this._ventaServicio.registrar(request).subscribe({
      next: (response) => {
        if (response.status) {
          this.totalPagar = 0.00;
          this.listaProductosParaVenta = [];
          this.datosDetalleVenta = new MatTableDataSource(this.listaProductosParaVenta);

          Swal.fire({
            icon: 'success',
            title: 'Venta Registrada!',
            text: `Numero de Venta: ${response.value.numeroDocumento}`
          });
        } else {
          this._utilidadServicio.mostrarAlerta("No se pudo registrar la venta", "Oopss!");
        }
      },
      complete: () => {
        this.bloquearBotonRegistrar = false;
      },
      error: (err) => {
        console.error('Error al registrar la venta:', err);
        this._utilidadServicio.mostrarAlerta("Hubo un error al registrar la venta", "Error");
      }
    });
  }
}


}
