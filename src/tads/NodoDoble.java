/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tads;

/**
 *
 * @author Sebastian
 */
public class NodoDoble<T extends Comparable> {
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;
    private T dato;

    public NodoDoble() {
    }

    public NodoDoble(T dato) {
        this.siguiente = null;
        this.dato = dato;
    }
    
    public NodoDoble getSiguiente(){
        return siguiente;
    }
    
    public T getDato(){
        return dato;
    }
    
    public void setSiguiente(NodoDoble siguiente){
        this.siguiente = siguiente;
    }
    
    public void setDato(T dato){
        this.dato = dato;
    }
    
    public void setAnterior(NodoDoble anterior){
        this.anterior = anterior;
    }
    
    public NodoDoble getAnterior(){
        return anterior;
    }
}
