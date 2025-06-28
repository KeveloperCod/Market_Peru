// src/app/Components/layout/layout-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,  // << El contenedor base
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./Pages/dash-board/dash-board.component').then(m => m.DashBoardComponent)
      },
      {
        path: 'usuario',
        loadComponent: () =>
          import('./Pages/usuario/usuario.component').then(m => m.UsuarioComponent)
      },
      {
        path: 'producto',
        loadComponent: () =>
          import('./Pages/producto/producto.component').then(m => m.ProductoComponent)
      },
      {
        path: 'venta',
        loadComponent: () =>
          import('./Pages/venta/venta.component').then(m => m.VentaComponent)
      },
      {
        path: 'historial',
        loadComponent: () =>
          import('./Pages/historial-venta/historial-venta.component').then(m => m.HistorialVentaComponent)
      },
      {
        path: 'reporte',
        loadComponent: () =>
          import('./Pages/reporte/reporte.component').then(m => m.ReporteComponent)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule {}
