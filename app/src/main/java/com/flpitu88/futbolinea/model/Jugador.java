package com.flpitu88.futbolinea.model;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String id;
    private String nombre;
    private String apellido;
    private String apodo;
    private String celular;
    private String fechaNacimiento;
    private String imagenPerfil;
    private List<String> grupos;
    private Estadisticas estadisticas;

    public Jugador(String id, String nombre, String apellido, String apodo, String fechaNacimiento, String celular) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.grupos = new ArrayList<>();
        grupos.add("Generacion Dorada");
        grupos.add("Parque");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public List<String> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<String> grupos) {
        this.grupos = grupos;
    }
}
