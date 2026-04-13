package com.gimnasio.gms.comun.excepcion;

public class ExcepcionNegocio extends RuntimeException {
    public ExcepcionNegocio(String mensaje) {
        super(mensaje);
    }
}
