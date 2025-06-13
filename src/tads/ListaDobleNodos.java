/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tads;

/**
 *
 * @author Sebastian
 */
public class ListaDobleNodos<T extends Comparable> implements ILista<T>{
    
    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;
    private int cantElementos;

    public ListaDobleNodos() {
        this.inicio = null;
        this.fin = null;
        cantElementos = 0;
    }

    @Override
    public void agregarInicio(T x) {

        NodoDoble<T> nuevo = new NodoDoble(x);

        if (esVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
            inicio = nuevo;
        }

        cantElementos++;
    }

    @Override
    public void mostrar() {
        NodoDoble<T> aux = inicio;
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
        boolean existe = false;
        NodoDoble<T> aux = inicio;

        while (aux != null && !existe) {
            if (aux.getDato().equals(x)) {
                existe = true;
            }
            aux = aux.getSiguiente();
        }
        return existe;
    }

    @Override
    public T obtenerElemento(int indice) {
        int indiceActual = 0;
        T ret = null;
        NodoDoble<T> aux = inicio;

        while (aux != null && ret == null) {
            if (indiceActual == indice) {
                ret = aux.getDato();
            }
            aux = aux.getSiguiente();
            indiceActual++;
        }
        return ret;
    }

    @Override
    public void agregarFinal(T x) {
        if (esVacia()) {
            agregarInicio(x);
        } else {
            NodoDoble<T> nuevo = new NodoDoble(x);
            nuevo.setAnterior(fin);
            fin.setSiguiente(nuevo);
            fin = nuevo;
            cantElementos++;
        }
    }

    @Override
    public void eliminarInicio() {
        if (!esVacia()) {
            if (cantElementos == 1) {
                vaciar();
            } else {
                NodoDoble aBorrar = inicio;
                inicio = inicio.getSiguiente();
                inicio.setAnterior(null);
                aBorrar.setSiguiente(null);
                cantElementos--;
            }

        }
    }

    @Override
    public void eliminarFinal() {
        if (!esVacia()) {
            if (inicio.getSiguiente() == null) { //Tengo solo 1 elemento
                vaciar();
            } else { //Tengo mas de 1 elemento
                NodoDoble<T> aBorrar = fin;
                fin = aBorrar.getAnterior();
                fin.setSiguiente(null);
                aBorrar.setAnterior(null);
                cantElementos--;
            }
        }
    }

    public NodoDoble<T> getInicio() {
        return inicio;
    }

    public void setInicio(NodoDoble<T> inicio) {
        this.inicio = inicio;
    }

    public int getCantElementos() {
        return cantElementos;
    }

    public void setCantElementos(int cantElementos) {
        this.cantElementos = cantElementos;
    }

    @Override
    public boolean eliminarElemento(T x) {

        boolean elimine = false;

        if (!esVacia()) {
            if (inicio.getDato().equals(x)) { //Es el primero
                eliminarInicio();
                elimine = true;
            } else {

                if (fin.getDato().equals(x)) { //Es el ultimo
                    eliminarFinal();
                    elimine = true;
                } else {

                    NodoDoble<T> aux = inicio;

                    while (aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(x)) {
                        aux = aux.getSiguiente();
                    }

                    if (aux.getSiguiente() != null) {  //encontre el dato
                        elimine = true;
                        NodoDoble<T> eEliminar = aux.getSiguiente();
                        aux.setSiguiente(eEliminar.getSiguiente());
                        eEliminar.getSiguiente().setAnterior(aux);
                        eEliminar.setSiguiente(null);
                        eEliminar.setAnterior(null);
                        cantElementos--;
                    }

                }

            }
        }
        return elimine;
    }

    @Override
    public boolean eliminarPorIndice(int indice) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarOrdenado(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}