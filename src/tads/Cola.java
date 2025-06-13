package tads;

public class Cola<T extends Comparable> {

    private Nodo<T> inicio;
    private Nodo<T> fin;

    public Cola() {
        inicio = null;
        fin = null;
    }
    
    public boolean esVacia(){
        return inicio == null;
    }
    
    public void enqueue(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        
        if(esVacia()){
            inicio = nuevo;
            fin = nuevo;
        }else{
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }
    
    public T dequeue(){
        if(esVacia()) return null;
        
        Nodo aBorrar = inicio;
        
        T dato = inicio.getDato();
        inicio = inicio.getSiguiente();
        
        aBorrar.setSiguiente(null);
        
        if(inicio == null) fin = null;
        
        return dato;
    }
    
    public T verFrente(){
        return esVacia() ? null : inicio.getDato();
    }

}
