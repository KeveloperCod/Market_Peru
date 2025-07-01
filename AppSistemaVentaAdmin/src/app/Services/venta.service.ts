/* =====================================================================
 * src/app/Services/venta.service.ts
 * ===================================================================== */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Venta } from '../Interfaces/venta';
import { VentaResponseDTO } from '../Interfaces/venta-response-dto';
import { ResponseApi } from '../Interfaces/response-api';
import { Reporte } from '../Interfaces/reporte';   // ⬅️ Nuevo import

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private urlApi = `${environment.endpoint}ventas/`;

  constructor(private http: HttpClient) {}

  /* ---------- REGISTRO ---------- */
  registrar(request: Venta): Observable<VentaResponseDTO> {
    return this.http
      .post<VentaResponseDTO>(`${this.urlApi}registrar`, request)
      .pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- HISTORIAL COMPLETO ---------- */
  historial(): Observable<VentaResponseDTO[]> {
    return this.http
      .get<VentaResponseDTO[]>(`${this.urlApi}listar`)
      .pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- HISTORIAL FILTRADO ---------- */
  historialFiltrado(
    buscarPor: string,
    numeroVenta: string,
    fechaInicio: string,
    fechaFin: string
  ): Observable<ResponseApi> {
    return this.http
      .get<ResponseApi>(
        `${this.urlApi}Historial?buscarPor=${buscarPor}&numeroVenta=${numeroVenta}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
      )
      .pipe(catchError(err => throwError(() => err)));
  }

  /* ---------- REPORTE ---------- */
  //  ⬇️ Devuelve directamente un array de ventas con su detalle
  reporte(fechaInicio: string, fechaFin: string): Observable<Reporte[]> {
    return this.http
      .get<Reporte[]>(
        `${this.urlApi}reporte?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
      )
      .pipe(catchError(err => throwError(() => err)));
  }
}
