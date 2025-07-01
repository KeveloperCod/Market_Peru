export interface ResponseApi {
    status:boolean,
    msg: string,
    value: any,
    totalProductos?: number;  // Cambiado a campos individuales
    totalVentas?: number; 

}
