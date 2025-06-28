/* src/app/Components/layout/layout.module.ts */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LayoutRoutingModule } from './layout-routing.module';
import { SharedModule } from 'src/app/Reutilizable/shared/shared.module';
import { MatProgressBarModule } from '@angular/material/progress-bar';

/* ----------  Shell + páginas standalone  ---------- */
import { LayoutComponent } from './layout.component';
import { DashBoardComponent } from './Pages/dash-board/dash-board.component';
import { UsuarioComponent } from './Pages/usuario/usuario.component';
import { ProductoComponent } from './Pages/producto/producto.component';
import { VentaComponent } from './Pages/venta/venta.component';
import { HistorialVentaComponent } from './Pages/historial-venta/historial-venta.component';
import { ReporteComponent } from './Pages/reporte/reporte.component';

/* ----------  Diálogos standalone  ---------- */
import { ModalUsuarioComponent } from './Modales/modal-usuario/modal-usuario.component';
import { ModalProductoComponent } from './Modales/modal-producto/modal-producto.component';
import { ModalDetalleVentaComponent } from './Modales/modal-detalle-venta/modal-detalle-venta.component';

@NgModule({
  // NO declarations: todos son standalone
  imports: [
    CommonModule,
    LayoutRoutingModule,
    SharedModule,
    MatProgressBarModule,

    /* Shell y páginas */
    LayoutComponent,
    DashBoardComponent,
    UsuarioComponent,
    ProductoComponent,
    VentaComponent,
    HistorialVentaComponent,
    ReporteComponent,

    /* Diálogos */
    ModalUsuarioComponent,
    ModalProductoComponent,
    ModalDetalleVentaComponent
  ]
})
export class LayoutModule {}
