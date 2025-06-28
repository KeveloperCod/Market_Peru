import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { Login } from 'src/app/Interfaces/login';
import { UsuarioService } from 'src/app/Services/usuario.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';
import { Sesion } from 'src/app/Interfaces/sesion';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatProgressBarModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

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

  iniciarSesion() {
    this.mostrarLoading = true;

    const request: Login = {
      correo: this.formularioLogin.value.email,
      clave: this.formularioLogin.value.password
    };

    this._usuarioServicio.IniciarSesion(request).subscribe({
      next: (data) => {
        if (data && data.token && data.usuario) {
          // Guardar token
          localStorage.setItem("token", data.token);

          // Crear sesión desde el usuario que vino del backend
          const sesion: Sesion = {
            idUsuario: data.usuario.idUsuario,
            nombreCompleto: data.usuario.nombreCompleto,
            correo: data.usuario.correo,
            rol: {
              idRol: data.usuario.rol.idRol,
              nombre: data.usuario.rol.nombre,
              fechaRegistro: data.usuario.rol.fechaRegistro
            }
          };

          this._utilidadServicio.guardarSesionUsuario(sesion);
          this.router.navigate(["pages"]);
        } else {
          this._utilidadServicio.mostrarAlerta("El usuario y/o contraseña son incorrectos", "¡Opps!");
        }
      },
      complete: () => {
        this.mostrarLoading = false;
      },
      error: () => {
        this._utilidadServicio.mostrarAlerta("Hubo un error", "¡Opps!");
        this.mostrarLoading = false;
      }
    });
  }

}
