import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

import { Venta } from '../Interfaces/venta';
import { VentaResponseDTO } from '../Interfaces/venta-response-dto';
import { ResponseApi } from '../Interfaces/response-api';   // ← se sigue usando en historial y reporte

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private urlApi = `${environment.endpoint}ventas/`;

  constructor(private http: HttpClient) {}

  /* ---------- REGISTRAR (ahora devuelve el DTO plano) ---------- */
  registrar(request: Venta): Observable<VentaResponseDTO> {
    return this.http.post<VentaResponseDTO>(`${this.urlApi}registrar`, request)
      .pipe(
        catchError(err => {
          console.error('Error en registrar venta', err);
          return throwError(() => err);
        })
      );
  }

  /* ---------- HISTORIAL Y REPORTE SIGUEN IGUAL ---------- */
  historial(buscarPor: string, numeroVenta: string,
            fechaInicio: string, fechaFin: string): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(
      `${this.urlApi}Historial?buscarPor=${buscarPor}&numeroVenta=${numeroVenta}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    );
  }

  reporte(fechaInicio: string, fechaFin: string): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(
      `${this.urlApi}Reporte?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    );
  }
}
