import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

import { Rol } from 'src/app/Interfaces/rol';
import { Usuario } from 'src/app/Interfaces/usuario';
import { RolService } from 'src/app/Services/rol.service';
import { UsuarioService } from 'src/app/Services/usuario.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';
import { ResponseApi } from 'src/app/Interfaces/response-api';

@Component({
  selector    : 'app-modal-usuario',
  standalone  : true,
  templateUrl : './modal-usuario.component.html',
  styleUrls   : ['./modal-usuario.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    MatSelectModule
  ]
})
export class ModalUsuarioComponent implements OnInit {

  formularioUsuario!: FormGroup;
  ocultarPassword  = true;
  tituloAccion     = 'Agregar';
  botonAccion      = 'Guardar';
  listaRoles: Rol[] = [];

  constructor(
    private dialogRef   : MatDialogRef<ModalUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public datosUsuario: Usuario | null,
    private fb            : FormBuilder,
    private rolSrv        : RolService,
    private usuarioSrv    : UsuarioService,
    private utilidadSrv   : UtilidadService
  ) {
    this.formularioUsuario = this.fb.group({
      nombreCompleto : ['', Validators.required],
      correo         : ['', Validators.required],
      idRol          : ['', Validators.required],
      clave          : [''],
      esActivo       : ['1', Validators.required]
    });

    if (this.datosUsuario) {
      this.tituloAccion = 'Editar';
      this.botonAccion  = 'Actualizar';

      this.formularioUsuario.patchValue({
        nombreCompleto: this.datosUsuario.nombreCompleto,
        correo        : this.datosUsuario.correo
      });
    }
  }

  ngOnInit(): void {
    this.rolSrv.lista().subscribe({
      next: (data) => {
        this.listaRoles = data;

        if (this.datosUsuario) {
          this.formularioUsuario.patchValue({
            idRol   : this.datosUsuario.rol.idRol,
            esActivo: this.datosUsuario.esActivo ? '1' : '0'
          });
        }
      },
      error: () => {
        this.utilidadSrv.mostrarAlerta('Error al cargar roles', 'Error');
      }
    });
  }
guardarUsuario(): void {
  const claveValor = (this.formularioUsuario.value.clave || '').trim();

  console.log('Datos enviados al backend:', this.formularioUsuario.value);

  const dto: Usuario = {
    idUsuario     : 0,  // Al agregar un nuevo usuario, el id debe ser 0
    nombreCompleto: this.formularioUsuario.value.nombreCompleto,
    correo        : this.formularioUsuario.value.correo,
    clave         : claveValor === '' ? null : claveValor,  // Si no hay clave, la dejamos como null
    esActivo      : this.formularioUsuario.value.esActivo === '1',
    fechaRegistro : '',
    rol           : {
      idRol        : this.formularioUsuario.value.idRol,
      nombre       : '',
      fechaRegistro: ''
    }
  };

  console.log('DTO para agregar usuario:', dto);

  this.usuarioSrv.guardar(dto).subscribe({
    next: (data) => {
      console.log('Respuesta del backend al agregar usuario:', data);
      if (data && data.status) {  // Verifica si la respuesta es exitosa
  this.utilidadSrv.mostrarAlerta('Usuario registrado con éxito', 'Éxito');
  this.dialogRef.close('true');
} else {
  this.utilidadSrv.mostrarAlerta('Error en la respuesta del servidor', 'Error');
}

    },
    error: (error) => {
      console.error('Error al agregar usuario:', error);
      this.utilidadSrv.mostrarAlerta('Error de servidor. Por favor, inténtalo más tarde.', 'Error');
    }
  });
}

editarUsuario(): void {
  const claveValor = (this.formularioUsuario.value.clave || '').trim();

  const dto: Usuario = {
    idUsuario     : this.datosUsuario ? this.datosUsuario.idUsuario : 0,
    nombreCompleto: this.formularioUsuario.value.nombreCompleto,
    correo        : this.formularioUsuario.value.correo,
    clave         : claveValor === '' ? null : claveValor,
    esActivo      : this.formularioUsuario.value.esActivo === '1',
    fechaRegistro : '',
    rol           : {
      idRol        : this.formularioUsuario.value.idRol,
      nombre       : '',
      fechaRegistro: ''
    }
  };

  this.usuarioSrv.editar(dto).subscribe({
    next: () => {
      this.utilidadSrv.mostrarAlerta('Usuario actualizado con éxito', 'Éxito');
      this.dialogRef.close('true');
    },
    error: () => {
      this.utilidadSrv.mostrarAlerta('Error de servidor. Por favor, inténtalo más tarde.', 'Error');
    }
  });
}

guardarEditar_Usuario(): void {
  if (this.datosUsuario) {
    this.editarUsuario();  // Si estamos editando, llamamos al método de edición
  } else {
    this.guardarUsuario();  // Si estamos agregando, llamamos al método de agregar
  }
}


}