package tads;

public class TADListaSimple {

    public static void main(String[] args) {

        ListaSimpleNodos lista = new ListaSimpleNodos();
        lista.agregarInicio(34);
        lista.agregarInicio(3);
        lista.agregarInicio(11);
        lista.mostrar();
        System.out.println("");
        lista.eliminarInicio();
        lista.eliminarInicio();
        lista.mostrar();
        System.out.println("");
        lista.agregarFinal(100);
        lista.agregarFinal(30);
        lista.mostrar();
        System.out.println("");

        /*
        ListaSimpleV1 miLista = new ListaSimpleV1(4);
        miLista.agregarFinal(10);
        miLista.agregarFinal(4);
        miLista.agregarFinal(16);
        miLista.agregarFinal(154);
        miLista.agregarFinal(124);
        miLista.mostrar();
         */
    }

}
