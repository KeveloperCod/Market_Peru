export interface Rol {
  idRol: number;
  nombre: string;
  fechaRegistro: string;
}

export interface Usuario {
  idUsuario: number;
  nombreCompleto: string;
  correo: string;
  clave: string;
  esActivo: boolean;
  fechaRegistro: string;
   rol: Rol;
}
