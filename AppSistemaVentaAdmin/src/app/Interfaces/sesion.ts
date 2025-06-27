import { Rol } from './rol';

export interface Sesion {
    idUsuario:number,
    nombreCompleto:string,
    correo:string,
    rol: Rol;
}
