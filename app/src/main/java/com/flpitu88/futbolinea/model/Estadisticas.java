package com.flpitu88.futbolinea.model;

public class Estadisticas {

    private Integer partidosJugados;
    private Integer ausencias;
    private Integer golesMarcados;
    private Integer asistencias;
    private Double promedio;

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getAusencias() {
        return ausencias;
    }

    public void setAusencias(Integer ausencias) {
        this.ausencias = ausencias;
    }

    public Integer getGolesMarcados() {
        return golesMarcados;
    }

    public void setGolesMarcados(Integer golesMarcados) {
        this.golesMarcados = golesMarcados;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
}
