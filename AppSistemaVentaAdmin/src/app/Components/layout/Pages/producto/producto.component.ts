import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

import { Producto } from 'src/app/Interfaces/producto';
import { ProductoService } from 'src/app/Services/producto.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

import { MatTableDataSource } from '@angular/material/table';
import { ModalProductoComponent } from '../../Modales/modal-producto/modal-producto.component';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto',
  standalone: true,
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css'],
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule
  ]
})
export class ProductoComponent implements OnInit, AfterViewInit {

  columnasTabla: string[] = ['nombre', 'categoria', 'stock', 'precio', 'estado', 'acciones'];
  dataInicio: Producto[] = [];
  dataListaProductos = new MatTableDataSource(this.dataInicio);

  @ViewChild(MatPaginator) paginacionTabla!: MatPaginator;

  constructor(
    private dialog: MatDialog,
    private _productoServicio: ProductoService,
    private _utilidadServicio: UtilidadService
  ) {}

  ngOnInit(): void {
    this.obtenerProductos();
  }

  ngAfterViewInit(): void {
    this.dataListaProductos.paginator = this.paginacionTabla;
  }

  // producto.component.ts  (solo el fragmento que obtiene datos)
obtenerProductos() {
  this._productoServicio.lista().subscribe({
    next: (productos) => this.dataListaProductos.data = productos,
    error: () => this._utilidadServicio.mostrarAlerta(
                'No se pudieron cargar los productos', 'Error')
  });
}


/* eliminarProducto() quedó igual: usa .eliminar(id) que devuelve ResponseApi */


  aplicarFiltroTabla(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataListaProductos.filter = filterValue.trim().toLocaleLowerCase();
  }

  nuevoProducto() {
    this.dialog.open(ModalProductoComponent, {
      disableClose: true
    }).afterClosed().subscribe(resultado => {
      if (resultado === true) this.obtenerProductos();
    });
  }

  editarProducto(producto: Producto) {
    this.dialog.open(ModalProductoComponent, {
      disableClose: true,
      data: producto
    }).afterClosed().subscribe(resultado => {
      if (resultado === true) this.obtenerProductos();
    });
  }

  eliminarProducto(producto: Producto) {
    Swal.fire({
      title: '¿Deseas eliminar el producto?',
      text: producto.nombre,
      icon: 'warning',
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((resultado) => {
      if (resultado.isConfirmed) {
        this._productoServicio.eliminar(producto.idProducto).subscribe({
          next: (data) => {
            if (data.status) {
              this._utilidadServicio.mostrarAlerta("El producto fue eliminado", "¡Listo!");
              this.obtenerProductos();
            } else {
              this._utilidadServicio.mostrarAlerta("No se pudo eliminar el producto", "Error");
            }
          },
          error: () => {}
        });
      }
    });
  }

}
