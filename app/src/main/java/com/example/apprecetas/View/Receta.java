package com.example.apprecetas.View;

import java.util.ArrayList;

public class Receta{
    private String nombre;
    private ArrayList ingredientes;
    private String tipo;
    private ArrayList pasos;
    private ArrayList imagenes;

    public Receta(String nom,ArrayList ing,String type,ArrayList steps,ArrayList img){
        this.nombre = nom;
        this.ingredientes = ing;
        this.tipo = type;
        this.pasos = steps;
        this.imagenes = img;
    }

    public Receta() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList pasos) {
        this.pasos = pasos;
    }

    public ArrayList getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList imagenes) {
        this.imagenes = imagenes;
    }
}