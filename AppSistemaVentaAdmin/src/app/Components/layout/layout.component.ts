import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Menu } from 'src/app/Interfaces/menu';
import { MenuService } from 'src/app/Services/menu.service';
import { UtilidadService } from 'src/app/Reutilizable/utilidad.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit {

  listaMenus: Menu[] = [];
  correoUsuario: string = '';
  rolUsuario: string = '';

  constructor(
    private router: Router,
    private _menuServicio: MenuService,
    private _utilidadServicio: UtilidadService
  ) { }

  ngOnInit(): void {
    const usuario = this._utilidadServicio.obtenerSesionUsuario();

    if (usuario != null) {
      this.correoUsuario = usuario.correo;
      this.rolUsuario = usuario.rol.nombre;

      this._menuServicio.lista(usuario.rol.idRol).subscribe({
  next:(data)=>{
    this.listaMenus = data;
  },
  error:(e)=>{
    console.error("Error al obtener los menús:", e);
  }
});
    }
  }

  cerrarSesion() {
    this._utilidadServicio.eliminarSesionUsuario();
    this.router.navigate(['login']);
  }

}
