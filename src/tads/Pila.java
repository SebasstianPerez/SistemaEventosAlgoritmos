
package tads;


public class Pila <T extends Comparable> {
    
    private Nodo<T> ultimo;
    
    public Pila(){
        ultimo = null;
    }
    
    public boolean esVacia(){
        return ultimo == null;
    }
    
    public void push(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        nuevo.setSiguiente(ultimo);
        ultimo = nuevo;
    }
    
    public T pop(){
        if(esVacia()) return null;
        
        Nodo aBorrar = ultimo;
        
        T dato = ultimo.getDato();
        ultimo = ultimo.getSiguiente();
        
        aBorrar.setSiguiente(null);
        
        return dato;
    }
    
    public T peek(){
        return esVacia() ? null : ultimo.getDato();
    }
}
