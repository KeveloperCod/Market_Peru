// menu.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Menu } from '../Interfaces/menu';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private urlApi: string = environment.endpoint + 'menu/';

  constructor(private http: HttpClient) { }

  lista(idRol: number): Observable<Menu[]> {
    return this.http.get<Menu[]>(`${this.urlApi}${idRol}`);
  }
}
