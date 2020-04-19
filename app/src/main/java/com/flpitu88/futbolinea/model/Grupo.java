package com.flpitu88.futbolinea.model;

import java.util.List;

public class Grupo {

    private String titulo;
    private List<String> jugadores;

    public Grupo(String titulo) {
        this.titulo = titulo;
    }

    public Grupo(String titulo, List<String> jugadores) {
        this.titulo = titulo;
        this.jugadores = jugadores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }
}
