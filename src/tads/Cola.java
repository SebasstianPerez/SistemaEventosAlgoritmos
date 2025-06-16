package tads;

import java.util.List;

public class Cola<T extends Comparable> {

    private Nodo<T> inicio;
    private Nodo<T> fin;
    public int cantElementos;

    public Cola() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }
    
    public boolean esVacia(){
        return inicio == null;
    }
    
    public void enqueue(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        
        if(esVacia()){
            inicio = nuevo;
        }else{
            fin.setSiguiente(nuevo);
        }
        
        fin = nuevo;
        cantElementos++;
    }
    
    public T dequeue(){
        if(esVacia()) return null;
        
        Nodo aBorrar = inicio;
        
        T dato = inicio.getDato();
        inicio = inicio.getSiguiente();
        
        aBorrar.setSiguiente(null);
        
        cantElementos--;
        
        if(inicio == null) fin = null;
        
        return dato;
    }
    
    public T verFrente(){
        return esVacia() ? null : inicio.getDato();
    }
    
    public ListaSimpleNodos<T> getDatos(){
        Nodo actual = inicio;
        ListaSimpleNodos<T> ret = new ListaSimpleNodos();
        
        while(actual != null){
            ret.agregarOrdenado((T) actual.getDato());
            actual = actual.getSiguiente();
        }
        
        return ret;
    }
}
