package tads;

public class ListaSimpleNodos<T extends Comparable> implements ILista<T> {

    private Nodo inicio;
    private Nodo fin;
    private int cantElementos;

    public ListaSimpleNodos() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    @Override
    public void agregarInicio(T x) {
        Nodo nuevo = new Nodo(x);
        
        if(esVacia()){
            fin = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
        }
        
        inicio = nuevo;
        cantElementos++;
    }

    @Override
    public void mostrar() {
        Nodo aux = inicio;

        while (aux != null) {
            System.out.print(aux.getDato() + " - ");
            aux = aux.getSiguiente();
        }
    }

    @Override
    public int cantidadElementos() {
        return cantElementos;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void vaciar() {
        inicio = null;
        fin = null;
        cantElementos = 0;
    }

    @Override
    public boolean existeElemento(T x) {
        Nodo aux = inicio;
        boolean existe = false;

        while (aux != null && !existe) {
            if(aux.getDato().equals(x)){
                existe = true;
            }
            aux = aux.getSiguiente();
        }
        
        return existe;
    }


    @Override
    public T obtenerElemento(int indice) {
        Nodo aux = inicio;
        T ret = null;
        int pos = 0;

        while (aux != null && ret == null) {
            if(pos == indice){
                ret = (T) aux.getDato();
                pos = -1;
            }
            aux = aux.getSiguiente();
            pos++;
        }
        
        return ret;
    }

    @Override
    public void agregarFinal(T x) {
        if (esVacia()) {
            agregarInicio(x);
        } else {
            Nodo nuevo = new Nodo(x);
            fin.setSiguiente(nuevo);
            fin = nuevo;
            cantElementos++;
        }
    }

    @Override
    public void eliminarInicio() {
        if (!esVacia()) {
            if(cantElementos == 1){
                vaciar();
            } else {
                Nodo<T> aBorrar = inicio;
                inicio = inicio.getSiguiente();
                aBorrar.setSiguiente(null);
                cantElementos--;
            }
        }
    }

    @Override
    public void eliminarFinal() {
        if (!esVacia()) {
            if (inicio.getSiguiente() == null) {
                vaciar();
            } else { // caso lista con dos elementos
                Nodo aux = inicio;
                while (aux.getSiguiente().getSiguiente() != null) {
                    aux = aux.getSiguiente();
                }
                
                aux.setSiguiente(null);
                fin = aux;
                cantElementos--;

            }
        }
    }
    
    public Nodo<T> getInicio(){
        return inicio;
    }
    
    public Nodo<T> getFinal(){
        return fin;
    }
    
    public void setInicio(Nodo<T> inicio){
        this.inicio = inicio;
    }
    
    //set cantidadElementos
    
    
    @Override
    public boolean eliminarElemento(T x){
        boolean eliminado = false;
            
        if(!esVacia()){
            if(inicio.getDato().equals(x)){
                eliminarInicio();
                eliminado = true;
            } else {
                Nodo<T> aux = inicio;
                
                while (aux.getSiguiente() != null && aux.getSiguiente().getDato().compareTo(x) < 0) {
                 aux = aux.getSiguiente();
                }
                
                if(aux.getSiguiente() != null){ 
                    eliminado = true;
                    Nodo <T> eEliminar = aux.getSiguiente();
                    aux.setSiguiente(aux.getSiguiente().getSiguiente());
                    eEliminar.setSiguiente(null);
                    cantElementos--;
                }
                
            }
        
        }  
        
        return eliminado;
    }

    @Override
    public boolean eliminarPorIndice(int indice) {
        Nodo aux = inicio;
        boolean ret = false;
        int pos = 0;

        while (aux != null && !ret) {
            if(indice == 0){
                eliminarInicio();
                ret = !ret;
            } else if(indice == cantElementos -1){
                eliminarFinal();
                ret = !ret;
            } else if(pos == indice - 1){
                Nodo<T> eliminado = aux.getSiguiente();
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                eliminado.setSiguiente(null);
                
                cantElementos--;
                ret = !ret;
            }
            
            aux = aux.getSiguiente();
            pos++;
        }
        
        return ret;
    }

    @Override
    public void agregarOrdenado(T x) {
        if(esVacia() || x.compareTo(inicio.getDato())< 0){ //Esta vacia
            agregarInicio(x);
        }
        else{
            Nodo<T> act = inicio;
            
            while(act.getSiguiente() != null && act.getSiguiente().getDato().compareTo(x)<=0){
                act = act.getSiguiente();
            }
            
            if(act.getSiguiente() == null){
                agregarFinal(x);
            }
            else{
                Nodo<T> nuevo = new Nodo(x);
                nuevo.setSiguiente(act.getSiguiente());
                act.setSiguiente(nuevo);
                cantElementos++;
            }
        }
    }
}
