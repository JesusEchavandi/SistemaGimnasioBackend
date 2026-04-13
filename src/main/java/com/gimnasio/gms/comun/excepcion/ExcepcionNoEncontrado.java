package com.gimnasio.gms.comun.excepcion;

public class ExcepcionNoEncontrado extends RuntimeException {
    public ExcepcionNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
