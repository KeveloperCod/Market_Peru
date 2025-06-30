import { Injectable } from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { environment } from 'src/environments/environment';
import { ResponseApi } from '../Interfaces/response-api';
import { Login } from '../Interfaces/login';
import { Usuario } from '../Interfaces/usuario';
import { LoginResponse } from '../Interfaces/login-response';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private urlApi:string= environment.endpoint + "usuarios/";

  constructor(private http:HttpClient) { }
   // Método para obtener el token


IniciarSesion(request: Login): Observable<LoginResponse> {
  return this.http.post<LoginResponse>("http://localhost:8080/auth/login", request);
}

lista(): Observable<Usuario[]> {
  return this.http.get<Usuario[]>(`${this.urlApi}listar`);
}
  guardar(request: Usuario):Observable<ResponseApi>{
    return this.http.post<ResponseApi>(`${this.urlApi}Guardar`,request)
  }

  editar(request: Usuario):Observable<ResponseApi>{
    return this.http.put<ResponseApi>(`${this.urlApi}Editar`,request)
  }

  eliminar(id: number):Observable<ResponseApi>{
    return this.http.delete<ResponseApi>(`${this.urlApi}Eliminar/${id}`)
  }

}
