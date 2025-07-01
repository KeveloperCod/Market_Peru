import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');
    console.log('Token a enviar:', token); 

    if (token) {
      console.log('Token encontrado, enviando la solicitud con el token.', token);
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
         console.log('Solicitud con token:', cloned); 
      return next.handle(cloned);
    }
       console.log('No se encontró token, enviando solicitud sin autorización.');
    return next.handle(req);
  }
}
