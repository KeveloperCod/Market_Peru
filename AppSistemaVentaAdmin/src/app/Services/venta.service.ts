import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Venta } from '../Interfaces/venta';
import { VentaResponseDTO } from '../Interfaces/venta-response-dto';
import { ResponseApi } from '../Interfaces/response-api';

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private urlApi = `${environment.endpoint}ventas/`;

  constructor(private http: HttpClient) {}

  /* ---------- REGISTRO ---------- */
  registrar(request: Venta): Observable<VentaResponseDTO> {
    return this.http.post<VentaResponseDTO>(`${this.urlApi}registrar`, request)
      .pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- HISTORIAL COMPLETO ---------- */
  historial(): Observable<VentaResponseDTO[]> {
    return this.http.get<VentaResponseDTO[]>(`${this.urlApi}listar`)
      .pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- HISTORIAL FILTRADO ---------- */
  historialFiltrado(buscarPor: string, numeroVenta: string,
                    fechaInicio: string, fechaFin: string): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(
      `${this.urlApi}Historial?buscarPor=${buscarPor}&numeroVenta=${numeroVenta}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    ).pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- REPORTE  (lista detallada para Excel / tabla) ---------- */
  reporte(fechaInicio: string, fechaFin: string): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(
      `${this.urlApi}Reporte?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    ).pipe(catchError(err => throwError(() => err)));
  }
}
