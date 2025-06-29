import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';

import { Categoria } from 'src/app/Interfaces/categoria';
import { Producto } from 'src/app/Interfaces/producto';
import { CategoriaService } from 'src/app/Services/categoria.service';
import { ProductoService } from 'src/app/Services/producto.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

@Component({
  selector: 'app-modal-producto',
  standalone: true,
  templateUrl: './modal-producto.component.html',
  styleUrls: ['./modal-producto.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule
  ]
})
export class ModalProductoComponent implements OnInit {

  formularioProducto: FormGroup;
  tituloAccion: string = "Agregar";
  botonAccion: string = "Guardar";
  listaCategorias: Categoria[] = [];

  constructor(
    private modalActual: MatDialogRef<ModalProductoComponent>,
    @Inject(MAT_DIALOG_DATA) public datosProducto: Producto,
    private fb: FormBuilder,
    private _categoriaServicio: CategoriaService,
    private _productoServicio: ProductoService,
    private _utilidadServicio: UtilidadService
  ) {
    this.formularioProducto = this.fb.group({
      nombre: ['', Validators.required],
      idCategoria: ['', Validators.required],
      stock: ['', Validators.required],
      precio: ['', Validators.required],
      esActivo: ['1', Validators.required],
    });

    if (this.datosProducto != null) {
      this.tituloAccion = "Editar";
      this.botonAccion = "Actualizar";
    }

    this._categoriaServicio.lista().subscribe({
      next: (data) => {
        if (data.status) this.listaCategorias = data.value;
      },
      error: (e) => { }
    });
  }

  ngOnInit(): void {
    if (this.datosProducto != null) {
      this.formularioProducto.patchValue({
        nombre: this.datosProducto.nombre,
        idCategoria: this.datosProducto.idCategoria,
        stock: this.datosProducto.stock,
        precio: this.datosProducto.precio,
        esActivo: this.datosProducto.esActivo.toString(),
      });
    }
  }

  guardarEditar_Producto() {
    const _producto: Producto = {
      idProducto: this.datosProducto == null ? 0 : this.datosProducto.idProducto,
      nombre: this.formularioProducto.value.nombre,
      idCategoria: this.formularioProducto.value.idCategoria,
      descripcionCategoria: "",
      precio: this.formularioProducto.value.precio,
      stock: this.formularioProducto.value.stock,
      esActivo: parseInt(this.formularioProducto.value.esActivo)
    };

    if (this.datosProducto == null) {
      this._productoServicio.guardar(_producto).subscribe({
        next: (data) => {
          if (data.status) {
            this._utilidadServicio.mostrarAlerta("El producto se ha agregado correctamente", "Éxito");
            this.modalActual.close("true");
          } else {
            this._utilidadServicio.mostrarAlerta("No se pudo agregar el producto", "Error");
          }
        },
        error: (e) => { }
      });
    } else {
      this._productoServicio
        .editar(_producto.idProducto!, _producto)   // 1º id, 2º cuerpo
        .subscribe({
          next: (resp) => {
            if (resp.status) {
              this._utilidadServicio.mostrarAlerta(
                'El producto fue editado correctamente', 'Éxito'
              );
              this.modalActual.close(true);
            } else {
              this._utilidadServicio.mostrarAlerta(
                'No se pudo editar el producto', 'Error'
              );
            }
          }
        });
      }
    }
  }