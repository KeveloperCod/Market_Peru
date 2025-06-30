import { Injectable } from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {environment} from 'src/environments/environment';
import {ResponseApi} from '../Interfaces/response-api';
import { Venta } from '../Interfaces/venta';
import { MatTableDataSource } from '@angular/material/table';

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private urlApi:string= environment.endpoint + "ventas/";
   // Definir las propiedades necesarias
  totalPagar: number = 0.00;
  listaProductosParaVenta: any[] = [];  // Define un arreglo para los productos de la venta
  datosDetalleVenta: MatTableDataSource<any> = new MatTableDataSource(this.listaProductosParaVenta);  // Definir la tabla de detalles

  constructor(private http:HttpClient) { }

  registrar(request: Venta): Observable<ResponseApi> {
  return this.http.post<ResponseApi>(`${this.urlApi}registrar`, request).pipe(
    tap((response) => {
      // Si la respuesta es exitosa
      console.log('Venta registrada:', response);
      if (response.status) {
        this.totalPagar = 0.00;
        this.listaProductosParaVenta = [];
        this.datosDetalleVenta = new MatTableDataSource(this.listaProductosParaVenta);
      } else {
        console.log('Error al registrar venta', response);
      }
    }),
    catchError((error) => {
      console.error('Error en la solicitud de venta:', error);
      return throwError(error);
    })
  );
}


  historial(buscarPor:string,numeroVenta:string,fechaInicio:string,fechaFin:string):Observable<ResponseApi>{
    return this.http.get<ResponseApi>(`${this.urlApi}Historial?buscarPor=${buscarPor}&numeroVenta=${numeroVenta}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`)
  }

  reporte(fechaInicio:string,fechaFin:string):Observable<ResponseApi>{
    return this.http.get<ResponseApi>(`${this.urlApi}Reporte?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`)
  }

}
