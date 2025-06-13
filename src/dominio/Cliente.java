/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import tads.ListaSimpleNodos;

/**
 *
 * @author Sebastian
 */
public class Cliente implements Comparable{

    public String Nombre;
    public String Cedula;
    public ListaSimpleNodos<Entrada> entradas;

    public Cliente() {
        entradas = new ListaSimpleNodos();
    }

    public Cliente(String Cedula) {
        this.Cedula = Cedula;
        entradas = new ListaSimpleNodos();
    }

    public Cliente(String Nombre, String Cedula) {
        this.Nombre = Nombre;
        this.Cedula = Cedula;
        entradas = new ListaSimpleNodos();
    }
    
    @Override
    public int compareTo(Object o) { 
        Cliente otro = (Cliente) o;
        
        return this.Cedula.compareToIgnoreCase(otro.Cedula);
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || getClass() != o.getClass()) return false;
        
        Cliente otro = (Cliente) o;
        
        return (this.Cedula == null ? otro.Cedula == null : this.Cedula.equals(otro.Cedula));
   
    }

    public void eliminarEntrada(Entrada entradaAEliminar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return  Cedula +"-"+ Nombre;
    }   
}
