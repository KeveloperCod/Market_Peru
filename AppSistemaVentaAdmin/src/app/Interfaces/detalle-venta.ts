/* src/app/Interfaces/detalle-venta.ts */
export interface DetalleVenta {
  /* === datos provenientes del backend === */
  idProducto: number;                               //  🔸  repuesto
  producto?: { idProducto: number; nombre: string };

  cantidad: number;
  precio: number;
  total: number;

  /* === campos formateados para la vista === */
  descripcionProducto: string;                      //  ahora no opcional
  precioTexto: string;
  totalTexto: string;
}
