// src/app/Services/producto.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Producto } from '../Interfaces/producto';
import { ResponseApi } from '../Interfaces/response-api';
import { ProductoConCategoria } from '../Interfaces/ProductosConCategoria';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  /**  http://localhost:8080/api/  +  productos/  */
  private urlApi = environment.endpoint + 'productos/';

  constructor(private http: HttpClient) {}

  /** GET /api/productos/listar  →  Producto[] */
  lista(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.urlApi}listar`);
  }
  listaConCategoria(): Observable<ProductoConCategoria[]> {
  return this.http.get<ProductoConCategoria[]>(`${this.urlApi}listar-con-categoria`);
}


  /** POST /api/productos/registrar  →  ResponseApi con msg/estado */
  guardar(request: Producto): Observable<ResponseApi> {
    return this.http.post<ResponseApi>(`${this.urlApi}registrar`, request);
  }

  /** PUT /api/productos/actualizar/{id}  */
  editar(id: number, request: Producto): Observable<ResponseApi> {
    return this.http.put<ResponseApi>(`${this.urlApi}actualizar/${id}`, request);
  }

  /** DELETE /api/productos/desactivar/{id}  */
  eliminar(id: number): Observable<ResponseApi> {
    return this.http.delete<ResponseApi>(`${this.urlApi}desactivar/${id}`);
  }
}
