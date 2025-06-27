import { Injectable } from '@angular/core';
import { Usuario } from '../Interfaces/usuario';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Sesion } from '../Interfaces/sesion';
import { duration } from 'moment';

@Injectable({
  providedIn: 'root'
})
export class UtilidadService {

  constructor(private _snackBar:MatSnackBar) { }

  mostrarAlerta(mensaje:string,tipo:string){

    this._snackBar.open(mensaje,tipo,{
      horizontalPosition:"end",
      verticalPosition:"top",
      duration:3000
    })
  }

  guardarSesionUsuario(usuarioSesion:Sesion){
    localStorage.setItem("usuario",JSON.stringify(usuarioSesion));
  }

  obtenerSesionUsuario(): Sesion | null {
  const dataCadena = localStorage.getItem("usuario");
  return dataCadena ? JSON.parse(dataCadena) : null;
}
  eliminarSesionUsuario(){
    localStorage.removeItem("usuario");
  }


}
