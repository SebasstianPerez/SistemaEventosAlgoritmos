/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.time.LocalDate;
import tads.Cola;
import tads.ListaDobleNodos;
import tads.ListaSimpleNodos;
import tads.Nodo;
import tads.NodoDoble;

/**
 *
 * @author Sebastian
 */
public class Evento implements Comparable<Evento>{
    
    public String Codigo;
    public LocalDate Fecha;
    public String Descripcion;
    public int Aforo;
    public String SalaNombre;
    public ListaDobleNodos<Entrada> entradas;
    public ListaSimpleNodos<Calificacion> calificaciones;
    public int PuntajeTotal;
    public Cola<Cliente> colaEspera;

    public Evento() {
        entradas = new ListaDobleNodos();
        colaEspera = new Cola();
        calificaciones = new ListaSimpleNodos();
    }
    
    public Evento(String codigo){
        this.Codigo = codigo;
        entradas = new ListaDobleNodos();
        colaEspera = new Cola();
        calificaciones = new ListaSimpleNodos();
    }
    
    public Evento(String codigo, LocalDate fecha, String descripcion, int aforoNecesario, String salaNombre){
        this.Codigo = codigo;
        this.Fecha = fecha;
        this.Descripcion = descripcion;
        this.Aforo = aforoNecesario;
        this.SalaNombre = salaNombre;
        entradas = new ListaDobleNodos();
        colaEspera = new Cola();
        calificaciones = new ListaSimpleNodos();
    }
    
    @Override
    public String toString(){
        return Codigo + "-" + Descripcion + "-" + SalaNombre;
    }
    
    @Override
    public int compareTo(Evento x){
        return this.Codigo.compareTo(x.Codigo);
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        
        Evento otro = (Evento) obj;
        
        return (this.Codigo == null ? otro.Codigo == null : this.Codigo.equals(otro.Codigo));
        
    }
   
    public int cantidadEntradasActivas(){
        int contador = 0;
        NodoDoble<Entrada> actual = entradas.getInicio();
        
        while(actual != null){
            if(actual.getDato().estado){
                contador++;
            }
            actual = actual.getSiguiente();
        }
        
        return contador;
    }
    
}
