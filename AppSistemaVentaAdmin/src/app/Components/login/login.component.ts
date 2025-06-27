import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Login } from 'src/app/Interfaces/login';
import { UsuarioService } from 'src/app/Services/usuario.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';
import { Sesion } from 'src/app/Interfaces/sesion';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formularioLogin: FormGroup;
  ocultarPassword: boolean = true;
  mostrarLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private _usuarioServicio: UsuarioService,
    private _utilidadServicio: UtilidadService
  ) {
    this.formularioLogin = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  iniciarSesion() {
  this.mostrarLoading = true;

  const request: Login = {
    correo: this.formularioLogin.value.email,
    clave: this.formularioLogin.value.password
  };

  this._usuarioServicio.IniciarSesion(request).subscribe({
    next: (data) => {
      if (data) {

        const sesion: Sesion = {
  idUsuario: data.idUsuario,
  nombreCompleto: data.nombreCompleto,
  correo: data.correo,
  rol: {
    idRol: data.rol.idRol,
    nombre: data.rol.nombre,
    fechaRegistro: data.rol.fechaRegistro
  }
};

        this._utilidadServicio.guardarSesionUsuario(sesion);
        this.router.navigate(["pages"]);
      } else {
        this._utilidadServicio.mostrarAlerta("El usuario y/o contraseña son incorrectos", "Opps!");
      }
    },
    complete: () => {
      this.mostrarLoading = false;
    },
    error: () => {
      this._utilidadServicio.mostrarAlerta("Hubo un error", "Opps!");
    }
  });
}
}
