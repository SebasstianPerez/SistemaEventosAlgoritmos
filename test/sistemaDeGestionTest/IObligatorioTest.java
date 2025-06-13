/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaDeGestionTest;

import dominio.Cliente;
import dominio.Evento;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import sistemaDeGestion.*;

public class IObligatorioTest {
    
    private Sistema miSistema;
    
    @Before
    public void setUp(){
        miSistema = new Sistema();
    }
    
    
    //////TEST 1 SISTEMA DE GESTION
    @Test
    public void testCrearSistemaGestionOK(){
        Retorno r = miSistema.crearSistemaDeGestion();
        assertEquals(Retorno.ok().resultado, r.resultado);
    }

    /////TEST 2 REGISTRAR SALA
    @Test
    public void testRegistrarSalaOK(){
        Retorno r = miSistema.registrarSala("Sala1", 200);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("Sala2", 500);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("Sala3", 1000);
        assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.listarSalas();
        assertEquals(Retorno.ok().resultado, r.resultado);
        assertEquals("Sala3-1000#Sala2-500#Sala1-200", r.valorString);
    }
    
    @Test
    public void testRegistrarSalaERROR_1(){
        Retorno r = miSistema.registrarSala("Sala1", 200);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("Sala2", 500);
        assertEquals(Retorno.ok().resultado, r.resultado);
        r = miSistema.registrarSala("Sala1", 1000);
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
    
    @Test
    public void testRegistrarSalaERROR_2(){
        Retorno r = miSistema.registrarSala("Sala1", 0);
        assertEquals(Retorno.error2().resultado, r.resultado);
    }
    
    
    //// TEST 3 ELIMINAR SALA
    
    @Test
    public void testEliminarSalaOK(){
        miSistema.registrarSala("Sala1", 200);
        
        Retorno r = miSistema.eliminarSala("Sala1");
        assertEquals(Retorno.ok().resultado, r.resultado);
    }
    
    @Test
    public void testEliminarSalaERROR_1(){     
        Retorno r = miSistema.eliminarSala("Sala1");
        assertEquals(Retorno.error1().resultado, r.resultado);
    }
    
    
    ////TEST 4 REGISTRAR EVENTO
    @Test
    public void testRegistrarEventoOK(){
        miSistema.registrarSala("ATI71", 200);
        
        Retorno r = miSistema.registrarEvento("ATI71", "Musica de camara", 100, LocalDate.now());
        assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.listarEventos();
        assertEquals("ATI71-Musica de camara-ATI71-100-0", r.valorString);
    }


    //Eventos con mismo codigo
        @Test
    public void testRegistrarEventoERROR_1(){
        miSistema.registrarSala("ATI71", 200);
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 100, LocalDate.now());
        Retorno r = miSistema.registrarEvento("ATI71", "Concierto: Bach, Matthaus Passion", 200, LocalDate.of(2025, 8, 14));
        
        assertEquals(Retorno.error1().resultado, r.resultado);
        
        r = miSistema.listarEventos();
        assertEquals("ATI71-Musica de camara-ATI71-100-0", r.valorString);
    }
    
    //aforo <= 0
        @Test
    public void testRegistrarEventoERROR_2(){
        miSistema.registrarSala("ATI71", 200);
        
        
        Retorno r = miSistema.registrarEvento("ATI71", "Musica de camara", 0, LocalDate.now());
        
        assertEquals(Retorno.error2().resultado, r.resultado);
        
        r = miSistema.listarEventos();
        assertEquals("Lista vacia", r.valorString);
    }
    
    // Sin salas disponibles
    @Test
    public void testRegistrarEventoERROR_3(){
        miSistema.registrarSala("ATI71", 200);
        
        
        Retorno r = miSistema.registrarEvento("ATI71", "Musica de camara", 100, LocalDate.of(2025, 7, 12));
        assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarEvento("ZZZ09", "Concierto Jazz Montevideo", 200, LocalDate.of(2025, 7, 12));
        assertEquals(Retorno.error3().resultado, r.resultado);
        assertEquals("No hay salas disponibles para esa fecha con aforo suficiente", r.valorString);
        
        r = miSistema.listarEventos();
        assertEquals("ATI71-Musica de camara-ATI71-100-0", r.valorString);
    }
    
    ////test de funcionalidad obtener salas capacitadas
        @Test
    public void testGetSalasCapacitadas(){
        miSistema.registrarSala("ATI71", 200);
        miSistema.registrarSala("ABA46", 2000);
        miSistema.registrarSala("ZZZ09", 1000);
        
        var lista = miSistema.getSalasCapacitadas(2);
        assertEquals(lista.cantidadElementos(), 3);
    }
    
    
    ////TEST REGISTRAR CLIENTE
    @Test
    public void testRegistrarClienteOK(){
        Retorno r = miSistema.registrarCliente("58495053", "Sebastian");
            assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarCliente("58743973", "Luna");
            assertEquals(Retorno.ok().resultado, r.resultado);

        r = miSistema.registrarCliente("47625352", "Esteban");
            assertEquals(Retorno.ok().resultado, r.resultado);
            
        r = miSistema.listarClientes();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("47625352-Esteban#58495053-Sebastian#58743973-Luna", r.valorString);    
    }
    
    @Test
    public void testRegistrarClienteERROR_1(){
        Retorno r = miSistema.registrarCliente("5849akjsns053", "Sebastian");
            assertEquals(Retorno.error1().resultado, r.resultado);
            assertEquals("Formato invalido de cedula", r.valorString);    
            
        r = miSistema.listarClientes();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Lista vacia", r.valorString);    
    }
    
    @Test
    public void testRegistrarClienteERROR_2(){
        Retorno r = miSistema.registrarCliente("58495053", "Sebastian");
            assertEquals(Retorno.ok().resultado, r.resultado);  
            
        r = miSistema.registrarCliente("58495053", "Luna");
            assertEquals(Retorno.error2().resultado, r.resultado);
            assertEquals("Cliente ya registrado", r.valorString);       
           
        r = miSistema.listarClientes();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("58495053-Sebastian", r.valorString);   
    }
    
    ////////////////////////////////////////////////////
    ///////////////  REQUERIMIENTOS 2.*  ///////////////
    ////////////////////////////////////////////////////

    
    ////TEST LISTAR SALAS
    @Test
    public void testListarSalasOK(){
        miSistema.registrarSala("Sala1", 200);
        miSistema.registrarSala("Sala2", 500);
        miSistema.registrarSala("Sala3", 1000);
        
        Retorno r = miSistema.listarSalas();
        
        assertEquals(Retorno.ok().resultado, r.resultado);
        assertEquals("Sala3-1000#Sala2-500#Sala1-200", r.valorString);
    }
        
    @Test
    public void testListarSalasVacia(){
        Retorno r = miSistema.listarSalas();
        
        assertEquals(Retorno.ok().resultado, r.resultado);
        assertEquals("Lista vacia", r.valorString);
    }
    
    ////TEST listarEventos
    @Test
    public void testListarEventosOK(){
        miSistema.registrarSala("Sala3", 1000);
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 100, LocalDate.now());
        miSistema.registrarEvento("ABA46", "Concierto: Bach, Matthaus Passion", 1000, LocalDate.of(2025, 8, 14));
        miSistema.registrarEvento("ZZZ09", "Concierto Jazz Montevideo", 200, LocalDate.of(2025, 7, 12));
        
        Retorno r = miSistema.listarEventos();
        
        assertEquals(Retorno.ok().resultado, r.resultado);
        assertEquals("ABA46-Concierto: Bach, Matthaus Passion-Sala3-1000-0#ATI71-Musica de camara-Sala3-100-0#ZZZ09-Concierto Jazz Montevideo-Sala3-200-0", r.valorString);
    }
    
    @Test
    public void testListarEventosEsVacia(){
        Retorno r = miSistema.listarEventos();
        
        assertEquals(Retorno.ok().resultado, r.resultado);
        assertEquals("Lista vacia", r.valorString);
    }
    
    ////TEST LISTARCLIENTES
    @Test
    public void testListarClientesOK(){
        Retorno r = miSistema.registrarCliente("58495053", "Sebastian");
            assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarCliente("58743973", "Luna");
            assertEquals(Retorno.ok().resultado, r.resultado);

        r = miSistema.registrarCliente("47625352", "Mario");
            assertEquals(Retorno.ok().resultado, r.resultado);  
           
        r = miSistema.registrarCliente("47625351", "Pepe");
            assertEquals(Retorno.ok().resultado, r.resultado);  
           
        r = miSistema.listarClientes();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("47625351-Pepe#47625352-Mario#58495053-Sebastian#58743973-Luna", r.valorString);   
    }   
    
    @Test
    public void testListarClientesVacio(){
        Retorno r = miSistema.listarClientes();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Lista vacia", r.valorString);   
    }

    ////TEST SALA OPTIMA
    
    @Test
    public void testSalaEsOptima(){
        String[][] vistaSala = {
            {"O", "O", "X", "O", "O", "X"},
            {"O", "O", "X", "X", "O", "X"},
            {"O", "#", "O", "X", "O", "X"},
            {"O", "O", "#", "X", "O", "#"},
            {"O", "O", "X", "X", "X", "X"},
            {"X", "O", "X", "#", "X", "X"}
        };

        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("Es óptimo", r.valorString);
    }
    
        @Test
    public void testSalaNoEsOptima(){
        String[][] vistaSala = {
            {"O", "O", "X", "O", "O", "X"},
            {"O", "O", "X", "X", "#", "X"},
            {"#", "#", "O", "X", "O", "X"},
            {"#", "#", "#", "X", "#", "#"},
            {"O", "#", "X", "X", "X", "X"},
            {"X", "O", "X", "#", "X", "X"}
        };


        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("No es óptimo", r.valorString);
    }
    
        @Test
    public void testSalaConUnaFila(){
        String[][] vistaSala = {
            {"O", "#", "X", "#", "O", "X"}
        };

        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("Es óptimo", r.valorString);
    }
    
        @Test
    public void testSalaConUnaColumna(){
        String[][] vistaSala = {
            {"O"},
            {"O"},
            {"O"},
            {"#"},
            {"O"},
            {"X"}
        };

        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("No es óptimo", r.valorString);
    }
    
    @Test
        public void testSalaVacia(){
        String[][] vistaSala = {
            
        };

        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("La vista no puede estar vacia", r.valorString);
    }
        
    @Test
    public void testGetEventoExistente(){
        miSistema.registrarSala("ATI71", 200);
        
        Retorno r = miSistema.registrarEvento("ATI71", "Musica de camara", 100, LocalDate.of(2025, 6, 12));
            assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarCliente("58743973", "Luna");
            assertEquals(Retorno.ok().resultado, r.resultado);
           
        Evento esperado = new Evento("ATI71", LocalDate.of(2025, 6, 12), "Musica de camara", 100, "ATI71");
        
        var e = miSistema.getEvento("ATI71");
            assertEquals(e, esperado);
    }
    
    @Test
    public void testGetEventoNoExistente(){                   
        var e = miSistema.getEvento("ATI71");
            assertEquals(e, null);
    }
    
     @Test
    public void testGetClienteExistente(){
        Retorno r = miSistema.registrarCliente("58495053", "Sebastian");
            assertEquals(Retorno.ok().resultado, r.resultado);
           
        Cliente esperado = new Cliente("Sebastian", "58495053");
        
        var e = miSistema.getCliente("58495053");
            assertEquals(e, esperado);
    }
    
    @Test
    public void testGetClienteNoExistente(){                   
        var e = miSistema.getCliente("58495053");
            assertEquals(e, null);
    }
    
    @Test
    public void testComprarEntradaOK(){
         miSistema.registrarSala("ATI71", 200);
         
         String cedula = "58743973", 
               codigoEvento = "ATI71"; 
        
        Retorno r = miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
            assertEquals(Retorno.ok().resultado, r.resultado);
        
           
            
        r = miSistema.registrarCliente(cedula, "Luna");
            assertEquals(Retorno.ok().resultado, r.resultado);
           
        r = miSistema.comprarEntrada(cedula, codigoEvento);
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Si se pudo comprar entrada", r.valorString);
    
        //TODO mostrar entrada
    }
    
    @Test
    public void testComprarEntradaERROR_1(){
        miSistema.registrarSala("ATI71", 200);
         
         String cedula = "58743973", 
               codigoEvento = "ATI71"; 
        
        Retorno r = miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
            assertEquals(Retorno.ok().resultado, r.resultado);
           
        r = miSistema.comprarEntrada(cedula, codigoEvento);
            assertEquals(Retorno.error1().resultado, r.resultado);
            assertEquals("No existe un cliente con esa cedula", r.valorString);
    }
    
    @Test
    public void testComprarEntradaERROR_2(){
        miSistema.registrarSala("ATI71", 200);
         
        String cedula = "58743973", 
               codigoEvento = "ATI71";
           
        Retorno r = miSistema.registrarCliente(cedula, "Luna");
           assertEquals(Retorno.ok().resultado, r.resultado);
            
        r = miSistema.comprarEntrada(cedula, codigoEvento);
            assertEquals(Retorno.error2().resultado, r.resultado);
            assertEquals("No existe un evento con ese codigo", r.valorString);
    }
    
    @Test
    public void testComprarEntradaOKListaEspera(){
         miSistema.registrarSala("ATI71", 200);
         
         String codigoEvento = "ATI71"; 
        
        Retorno r = miSistema.registrarEvento(codigoEvento, "Musica de camara", 2, LocalDate.of(2025, 6, 12));
            assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarCliente("58495053", "Sebastian");
            assertEquals(Retorno.ok().resultado, r.resultado);
        
        r = miSistema.registrarCliente("58743973", "Luna");
            assertEquals(Retorno.ok().resultado, r.resultado);

        r = miSistema.registrarCliente("47625352", "Esteban");
            assertEquals(Retorno.ok().resultado, r.resultado);
           
        r = miSistema.comprarEntrada("58495053", "ATI71");
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Si se pudo comprar entrada", r.valorString);
    
        r = miSistema.comprarEntrada("58743973","ATI71");
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Si se pudo comprar entrada", r.valorString);
            
        r = miSistema.comprarEntrada("47625352", "ATI71");
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Agregado a cola de espera", r.valorString);
        //TODO mostrar entradas
    }
    
    @Test
    public void testDeshacerUltimasComprasOK(){
         miSistema.registrarSala("ATI71", 200);
         
         String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("58743973", "Luna");

        miSistema.registrarCliente("47625352", "Esteban");
            
        miSistema.registrarCliente("53428276", "Milagros");
           
        miSistema.comprarEntrada("58495053", "ATI71");
    
        miSistema.comprarEntrada("58743973","ATI71");
            
        miSistema.comprarEntrada("47625352", "ATI71");
            
        miSistema.comprarEntrada("53428276", "ATI71");
          
        
        Retorno r = miSistema.deshacerUltimasCompras(2);
            assertEquals(Retorno.ok().resultado , r.resultado);
            assertEquals("ATI71-53428276#ATI71-47625352", r.valorString);
    }
    
    
    @Test
    public void testDeshacerUltimasComprasOKVacia(){        
        Retorno r = miSistema.deshacerUltimasCompras(2);
            assertEquals(Retorno.ok().resultado , r.resultado);
            assertEquals("No hay compras", r.valorString);
    }
    
    @Test
    public void testListarClientesDeEventoOK(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("58743973", "Luna");

        miSistema.registrarCliente("47625352", "Esteban");
            
        miSistema.registrarCliente("53428276", "Milagros");
           
        miSistema.comprarEntrada("58495053", "ATI71");
    
        miSistema.comprarEntrada("58743973","ATI71");
            
        miSistema.comprarEntrada("47625352", "ATI71");
            
        miSistema.comprarEntrada("53428276", "ATI71");
        
        Retorno r = miSistema.listarClientesDeEvento(codigoEvento, 3);
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("53428276-Milagros#47625352-Esteban#58743973-Luna", r.valorString);
    }
    
        @Test
    public void testListarClientesDeEventoOKVacio(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        
        Retorno r = miSistema.listarClientesDeEvento(codigoEvento, 1);
            assertEquals(r.resultado, Retorno.Resultado.OK);
            assertEquals(r.valorString, "No hay compras");
    }
    
    @Test
    public void testListarClientesDeEventoOKCantEntradasMenor(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("58743973", "Luna");
  
        miSistema.comprarEntrada("58495053", "ATI71");
    
        miSistema.comprarEntrada("58743973","ATI71");
        
        Retorno r = miSistema.listarClientesDeEvento(codigoEvento, 3);
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("58743973-Luna#58495053-Sebastian", r.valorString);
    }
    
    @Test
    public void testListarClientesDeEventoERROR1(){        
        Retorno r = miSistema.listarClientesDeEvento("ATI71", 3);
            assertEquals(r.resultado, Retorno.Resultado.ERROR_1);
    }
    
    @Test
    public void testListarClientesDeEventoERROR2(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("58743973", "Luna");
  
        miSistema.comprarEntrada("58495053", "ATI71");
    
        miSistema.comprarEntrada("58743973","ATI71");
        
        Retorno r = miSistema.listarClientesDeEvento("ATI71", 0);
            assertEquals(r.resultado, Retorno.Resultado.ERROR_2);
    }
    
    @Test
    public void testEliminarEventoOK(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        
        Retorno r = miSistema.eliminarEvento(codigoEvento);
            assertEquals(Retorno.ok().resultado, r.resultado);
            
        r = miSistema.listarEventos();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Lista vacia", r.valorString);
    }
    
    @Test
    public void testEliminarEventoERROR1(){
        Retorno r = miSistema.eliminarEvento("ASJ23");
            assertEquals(Retorno.error1().resultado, r.resultado);
            
        r = miSistema.listarEventos();
            assertEquals(Retorno.ok().resultado, r.resultado);
            assertEquals("Lista vacia", r.valorString);
    }
    
    @Test
    public void testEliminarEventoERROR2(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("58743973", "Luna");
  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("58743973","ATI71");
        
        Retorno r = miSistema.eliminarEvento(codigoEvento);
            assertEquals(Retorno.error2().resultado, r.resultado);
    }
    
    @Test
    public void testDevolverEntradaOK(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71",
                cedula = "57295841"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 100, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");
  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        
        Retorno r = miSistema.devolverEntrada(cedula, codigoEvento);
            assertEquals("Se pudo devolver la entrada", r.valorString);
            assertEquals(Retorno.ok().resultado, r.resultado);
    }
    
    @Test
    public void testDevolverEntradaOKAsignarSiguiente(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71",
                cedula = "12345673"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 5, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");
        miSistema.registrarCliente("87631234", "Juan");
        miSistema.registrarCliente("65327621", "Maria");
        miSistema.registrarCliente("12345673", "Marcos");
        miSistema.registrarCliente("32415234", "Pedro");
  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        miSistema.comprarEntrada("87631234", "ATI71");
        miSistema.comprarEntrada("65327621","ATI71");
        miSistema.comprarEntrada("12345673", "ATI71");
        
        Retorno r = miSistema.comprarEntrada("32415234","ATI71");
            assertEquals("Agregado a cola de espera", r.valorString);
            assertEquals(Retorno.ok().resultado, r.resultado);
            
        r = miSistema.devolverEntrada(cedula, codigoEvento);
            assertEquals("Se pudo devolver la entrada", r.valorString);
            assertEquals(Retorno.ok().resultado, r.resultado);
            
        r = miSistema.listarClientesDeEvento(codigoEvento, 2);
            assertEquals("32415234-Pedro#65327621-Maria", r.valorString);
            assertEquals(Retorno.ok().resultado, r.resultado);
    }
    
    @Test
    public void testDevolverEntradaERROR1(){
        miSistema.registrarSala("ATI71", 200);
         
        String codigoEvento = "ATI71",
                cedula = "12345673"; 
        
        miSistema.registrarEvento(codigoEvento, "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
            
        Retorno r = miSistema.devolverEntrada(cedula, codigoEvento);
            assertEquals("Cliente no existe", r.valorString);
            assertEquals(Retorno.error1().resultado, r.resultado);
            
        r = miSistema.listarClientesDeEvento(codigoEvento, 2);
            assertEquals("57295841-Luna#58495053-Sebastian", r.valorString);
            assertEquals(Retorno.ok().resultado, r.resultado);
    }
    
    @Test
    public void testDevolverEntradaERROR2(){
        miSistema.registrarSala("ATI71", 200);
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
            
        Retorno r = miSistema.devolverEntrada("57295841", "ATI72");
            assertEquals("Evento no existe", r.valorString);
            assertEquals(Retorno.error2().resultado, r.resultado);
    }
    
    @Test
    public void testCalificarEventoOK(){
        miSistema.registrarSala("ATI71", 200);
        
        String cedula = "57295841",
               codigoEvento = "ATI71",
               comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        
        Retorno r = miSistema.calificarEvento("58495053", codigoEvento, 10, comentario);
           assertEquals(Retorno.Resultado.OK, r.resultado);
           assertEquals("Se pudo calificar el evento", r.valorString);
            
        r = miSistema.calificarEvento(cedula, codigoEvento, 10, comentario);
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("Se pudo calificar el evento", r.valorString);
    }
    
    @Test
    public void testCalificarEventoERROR1(){
        miSistema.registrarSala("ATI71", 200);
        
        String cedula = "57295841",
               codigoEvento = "ATI71",
               comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
  
        miSistema.comprarEntrada("58495053", "ATI71");
        
        Retorno r = miSistema.calificarEvento("58495053", codigoEvento, 10, comentario);
           assertEquals(Retorno.Resultado.OK, r.resultado);
           assertEquals("Se pudo calificar el evento", r.valorString);
            
        r = miSistema.calificarEvento(cedula, codigoEvento, 10, comentario);
            assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
            assertEquals("Cliente no existe", r.valorString);
    }
    
    @Test
    public void testCalificarEventoERROR2(){
        miSistema.registrarSala("ATI71", 200);
        
        String codigoEvento = "ATI72",
               comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
  
        miSistema.comprarEntrada("58495053", "ATI71");
        
        Retorno r = miSistema.calificarEvento("58495053", codigoEvento, 10, comentario);
           assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
           assertEquals("Evento no existe", r.valorString);
    }
    
    @Test
    public void testCalificarEventoERROR3(){
        miSistema.registrarSala("ATI71", 200);
        
        String cedula = "57295841",
               codigoEvento = "ATI71",
               comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        
        Retorno r = miSistema.calificarEvento("58495053", codigoEvento, 10, comentario);
           assertEquals(Retorno.Resultado.OK, r.resultado);
           assertEquals("Se pudo calificar el evento", r.valorString);
            
        r = miSistema.calificarEvento(cedula, codigoEvento, 11, comentario);
            assertEquals(Retorno.Resultado.ERROR_3, r.resultado);
            assertEquals("Puntaje invalido", r.valorString);
    }
    
    @Test
    public void testCalificarEventoERROR4(){
        miSistema.registrarSala("ATI71", 200);
        
        String cedula = "57295841",
               codigoEvento = "ATI71",
               comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        
        Retorno r = miSistema.calificarEvento(cedula, codigoEvento, 10, comentario);
           assertEquals(Retorno.Resultado.OK, r.resultado);
           assertEquals("Se pudo calificar el evento", r.valorString);
            
        r = miSistema.calificarEvento(cedula, codigoEvento, 10, comentario);
            assertEquals(Retorno.Resultado.ERROR_4, r.resultado);
            assertEquals("Evento ya calificado por el cliente", r.valorString);
    }
    
    @Test
    public void testEventoMejorPuntuadoOK(){
        miSistema.registrarSala("ATI71", 200);
        
        String comentario = "Excelente espectaculo";
                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
                miSistema.registrarEvento("ZZZ09", "Concierto Jazz Montevideo", 200, LocalDate.of(2025, 7, 12));

        
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        miSistema.comprarEntrada("58495053", "ZZZ09");
        miSistema.comprarEntrada("57295841","ZZZ09");
        
        miSistema.calificarEvento("58495053", "ATI71", 10, comentario);
        miSistema.calificarEvento("57295841", "ATI71", 10, comentario);
        
        miSistema.calificarEvento("58495053", "ZZZ09", 10, comentario);
        miSistema.calificarEvento("57295841", "ZZZ09", 10, comentario);
        
        Retorno r = miSistema.eventoMejorPuntuado();
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("ATI71-Musica de camara-ATI71#ZZZ09-Concierto Jazz Montevideo-ATI71", r.valorString);
    }
    
    @Test
    public void testEventoMejorPuntuadoOKEventosVacio(){        
        Retorno r = miSistema.eventoMejorPuntuado();
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("No hay eventos registrados", r.valorString);
    }
    
    @Test
    public void testEventoMejorPuntuadoOKCalificiacionVacio(){  
        miSistema.registrarSala("ATI71", 200);                
        
        miSistema.registrarEvento("ATI71", "Musica de camara", 200, LocalDate.of(2025, 6, 12));
                miSistema.registrarEvento("ZZZ09", "Concierto Jazz Montevideo", 200, LocalDate.of(2025, 7, 12));

        
        miSistema.registrarCliente("58495053", "Sebastian");
        miSistema.registrarCliente("57295841", "Luna");

  
        miSistema.comprarEntrada("58495053", "ATI71");
        miSistema.comprarEntrada("57295841","ATI71");
        miSistema.comprarEntrada("58495053", "ZZZ09");
        miSistema.comprarEntrada("57295841","ZZZ09");
        
        Retorno r = miSistema.eventoMejorPuntuado();
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("No hay calificaciones", r.valorString);
    }
    
    //ComprasXDia Test
    
    @Test
    public void testComprasXDiaOKVacio(){
        Retorno r = miSistema.comprasXDia(1);
            assertEquals(Retorno.Resultado.OK, r.resultado);
            assertEquals("No hay entradas vendidas para ese mes", r.valorString);
    }
}