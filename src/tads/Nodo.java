
package tads;


public class Nodo<T extends Comparable> {
    
    private Nodo siguiente;
    private T dato;

    public Nodo(T dato) {
        this.dato = dato;
        siguiente = null;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    
    
    
}
