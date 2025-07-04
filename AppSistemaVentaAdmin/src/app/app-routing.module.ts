import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'pages',
    loadComponent: () =>
      import('./Components/layout/layout.component').then(m => m.LayoutComponent),
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./Components/layout/Pages/dash-board/dash-board.component').then(m => m.DashBoardComponent)
      },
      {
        path: 'usuario',
        loadComponent: () =>
          import('./Components/layout/Pages/usuario/usuario.component').then(m => m.UsuarioComponent)
      },
      {
        path: 'producto',
        loadComponent: () =>
          import('./Components/layout/Pages/producto/producto.component').then(m => m.ProductoComponent)
      },
      {
        path: 'venta',
        loadComponent: () =>
          import('./Components/layout/Pages/venta/venta.component').then(m => m.VentaComponent)
      },
      {
        path: 'historial',
        loadComponent: () =>
          import('./Components/layout/Pages/historial-venta/historial-venta.component').then(m => m.HistorialVentaComponent)
      },
      {
        path: 'reporte',
        loadComponent: () =>
          import('./Components/layout/Pages/reporte/reporte.component').then(m => m.ReporteComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
