/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemaDeGestion;

import java.time.LocalDate;

/**
 *
 * @author Sebastian
 */
public interface IObligatorio {
    Retorno crearSistemaDeGestion();
    
    Retorno registrarSala(String nombre, int capacidad);   
    
    Retorno eliminarSala(String nombre);
    
    Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha);

    Retorno registrarCliente(String cedula, String nombre);

    Retorno comprarEntrada(String cedula, String codigoEvento);
    
    Retorno eliminarEvento(String codigo);

    Retorno devolverEntrada(String cedula, String codigoEvento);
    
    Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario);

    Retorno listarSalas();
     
    Retorno listarEventos();
    
    Retorno listarClientes();

    Retorno esSalaOptima(String vistaSala[][]);
    
    Retorno listarClientesDeEvento(String código, int n);
    
    Retorno listarEsperaEvento();
    
    Retorno deshacerUltimasCompras(int n);

    Retorno eventoMejorPuntuado();
    
    Retorno comprasDeCliente(String cedula);
    
    Retorno comprasXDia(int mes);    
}
