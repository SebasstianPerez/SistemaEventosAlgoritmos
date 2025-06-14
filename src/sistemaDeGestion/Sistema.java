package sistemaDeGestion;

import dominio.Calificacion;
import dominio.Cliente;
import dominio.Entrada;
import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import static sistemaDeGestion.Retorno.ok;
import tads.ListaSimpleNodos;
import tads.Nodo;
import tads.Pila;


public class Sistema implements IObligatorio {

    private ListaSimpleNodos<Sala> listaSala;
    private ListaSimpleNodos<Evento> listaEvento;
    private ListaSimpleNodos<Cliente> listaCliente;
    private Pila<Entrada> pilaEntrada;
    private int[][] cantComprasXMes;
    
    
    public Sistema(){
        listaSala = new ListaSimpleNodos();
        listaEvento = new ListaSimpleNodos();
        listaCliente = new ListaSimpleNodos();
        pilaEntrada = new Pila();
        cantComprasXMes = new int[12][31];
    }
    
    @Override
    public Retorno crearSistemaDeGestion() {
        Retorno r = new Retorno();
        Sistema sistema = new Sistema();
 
        r.resultado = Retorno.Resultado.OK;
        
        return r;
    }

    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        Retorno ret = new Retorno();
        
        if(capacidad <= 0){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString = "La capacidad debe ser mayor que 0";
            return ret;
        }
        
        Sala nueva = new Sala(nombre, capacidad);
        
        if(listaSala.existeElemento(nueva)){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "Ya existe una sala con ese nombre";
            return ret;
        }
        
        listaSala.agregarInicio(nueva);
        return Retorno.ok();
    }

    @Override
    public Retorno eliminarSala(String nombre) {
        Retorno ret = new Retorno();
        Sala salaBuscada = new Sala(nombre, 0);
        
        if(!listaSala.eliminarElemento(salaBuscada)){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "No existe una sala con ese nombre";
            return ret;
        }
        
        return Retorno.ok();
    }
    
    public Evento getEvento(String codigo){
        for(int i = 0; i < listaEvento.cantidadElementos(); i++){
            Evento evtIndice = listaEvento.obtenerElemento(i);
            
            if(evtIndice.Codigo.equalsIgnoreCase(codigo)){
                return evtIndice;
            }
        }
        
        return null;
    }
    
    public Cliente getCliente(String cedula){
        for(int i = 0; i < listaCliente.cantidadElementos(); i++){
            Cliente clienteIndice = listaCliente.obtenerElemento(i);
            
            if(clienteIndice.Cedula.equalsIgnoreCase(cedula)){
                return clienteIndice;
            }
        }
        return null;
    }
    
    @Override
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        Retorno ret = new Retorno();
        Evento nuevo = new Evento();
        nuevo.Codigo = codigo;
        
        if(listaEvento.existeElemento(nuevo)){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "Ya existe el evento";
            return ret;
        }
        
        if(aforoNecesario <= 0){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString = "El aforo debe ser mayor que 0";
            return ret;
        }
        
        ListaSimpleNodos<String> salasCapacitadas = getSalasCapacitadas(aforoNecesario);
        String salaDisponible = buscarSalaDisponible(salasCapacitadas, fecha);
        
        if(salaDisponible == null){
            ret.resultado = Retorno.error3().resultado;
            ret.valorString = "No hay salas disponibles para esa fecha con aforo suficiente";
            return ret;
        } else {
            nuevo.Aforo = aforoNecesario;
            nuevo.Descripcion = descripcion;
            nuevo.Fecha = fecha;
            nuevo.SalaNombre = salasCapacitadas.getInicio().getDato();
            listaEvento.agregarOrdenado(nuevo);
            ret.resultado = Retorno.ok().resultado;
            return ret;
        }
        
        
    }
    
    public ListaSimpleNodos<String> getSalasCapacitadas(int aforoNecesario){
        Nodo<Sala> actual = listaSala.getInicio();
        ListaSimpleNodos<String> salasCapacitadas = new ListaSimpleNodos();
        
        while(actual != null){
            Sala s = actual.getDato();
            
            if(s.Capacidad >= aforoNecesario){
                salasCapacitadas.agregarInicio(actual.getDato().Nombre);
            }
            actual = actual.getSiguiente();
        }
        return salasCapacitadas;
    }
    
    private String buscarSalaDisponible(ListaSimpleNodos<String> salasCapacitadas, LocalDate fecha) {
        String ret = null;
        
        for(int i = 0; i < salasCapacitadas.cantidadElementos(); i++){
            String nombreSala = salasCapacitadas.obtenerElemento(i);
            
            boolean ocupada = false;
            
            for(int j = 0; j < listaEvento.cantidadElementos(); j++){
                Evento evento = listaEvento.obtenerElemento(j);
                
                if(evento.SalaNombre.equals(nombreSala) && evento.Fecha.equals(fecha)){
                    ocupada = true;
                    break;
                }
            }
            
            if(!ocupada){
                return nombreSala;
            }
        }
        
        return null;
    }

    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        Retorno ret = new Retorno();
        Cliente nuevo = new Cliente();
        nuevo.Cedula = cedula.trim();
        
        if(!cedula.matches("\\d{8}") ){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString= "Formato invalido de cedula";
            
            return ret;
        } 
        
        if(listaCliente.existeElemento(nuevo)){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString= "Cliente ya registrado";
            
            return ret;
        }
            
        nuevo.Nombre = nombre;
            
        listaCliente.agregarOrdenado(nuevo);
        ret.resultado = Retorno.Resultado.OK;
            
        return ret;
    }

    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        Retorno ret = new Retorno();
        
        Cliente cliente = getCliente(cedula);
        
        if(cliente == null){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "No existe un cliente con esa cedula";
            return ret;
        }
        
        Evento evento = getEvento(codigoEvento);
        
        if(evento == null){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString = "No existe un evento con ese codigo";
            return ret;
        }
        
        ret.resultado = Retorno.Resultado.OK;
        
        if(evento.entradas.cantidadElementos() == evento.Aforo){
            evento.colaEspera.enqueue(cliente);
            ret.valorString = "Agregado a cola de espera";
        } else {
            asignarEntrada(cliente, evento);
            ret.valorString = "Si se pudo comprar entrada";
        }
        
        return ret;
    }
    
    public void asignarEntrada(Cliente cliente, Evento evento){
        Entrada entrada = new Entrada(cliente.Cedula, evento.Codigo);
        
        evento.entradas.agregarFinal(entrada);
        cliente.entradas.agregarFinal(entrada);
        
        cantComprasXMes[evento.Fecha.getMonthValue()-1][evento.Fecha.getDayOfMonth()-1]++;
        
        pilaEntrada.push(entrada);
    }

    @Override
    public Retorno eliminarEvento(String codigo) {
        Retorno ret = new Retorno();
        Nodo<Evento> aux = listaEvento.getInicio();
        Evento eventoAEliminar = null;
        
        while(aux!=null && eventoAEliminar == null){
            if(aux.getDato().Codigo.equals(codigo)){
                eventoAEliminar = aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        
        if(eventoAEliminar == null){
            return Retorno.error1();
        }
        
        if(eventoAEliminar.entradas.cantidadElementos() > 0){
            return Retorno.error2();
        }
        
        listaEvento.eliminarElemento(eventoAEliminar);
        return Retorno.ok();
    }
    
    private void asignarSiguienteColaEspera(Evento evento){
        if(evento.colaEspera.verFrente() != null){
            Cliente cliente = getCliente(evento.colaEspera.verFrente().Cedula);
            asignarEntrada(cliente, evento);
            evento.colaEspera.dequeue();
        }
    }

    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        Retorno ret = new Retorno();
	Cliente cliente = getCliente(cedula);
	Evento evento = getEvento(codigoEvento);
        
        if(cliente == null){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "Cliente no existe";
            
            return ret;
        } 
        
        if(evento == null){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString = "Evento no existe";
            
            return ret;
	}
        
        Entrada aBorrar = new Entrada(cliente.Cedula, evento.Codigo);
        cliente.entradas.eliminarElemento(aBorrar);
        evento.entradas.eliminarElemento(aBorrar);
        cantComprasXMes[evento.Fecha.getMonthValue()-1][evento.Fecha.getDayOfMonth()-1]--;

        
        ret.valorString = "Se pudo devolver la entrada";
        ret.resultado = Retorno.ok().resultado;
        
        asignarSiguienteColaEspera(evento);
        
        return ret;
        //Como la elimino de la pila?
    }

    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        Retorno ret = new Retorno();
        Cliente cliente = getCliente(cedula);
        Evento evento = getEvento(codigoEvento);
        
        if(cliente == null){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "Cliente no existe";
            
            return ret;
        } 
        
        if(evento == null){
            ret.resultado = Retorno.Resultado.ERROR_2;
            ret.valorString = "Evento no existe";
            
            return ret;
	}
        
        if(puntaje < 1 || puntaje > 10){
            ret.resultado = Retorno.Resultado.ERROR_3;
            ret.valorString = "Puntaje invalido";
            return ret;
        }
        
        if(evento.calificaciones.existeElemento(new Calificacion(cedula))){
            ret.resultado = Retorno.Resultado.ERROR_4;
            ret.valorString = "Evento ya calificado por el cliente";
            
            return ret;
        }
        
        Calificacion nueva = new Calificacion(cedula, puntaje, comentario);
        evento.calificaciones.agregarInicio(nueva);
        evento.PuntajeTotal += puntaje;
        
        ret.resultado = Retorno.Resultado.OK;
        ret.valorString = "Se pudo calificar el evento";
        
        return ret;
    }
    
    @Override
    public Retorno listarSalas() {
        Retorno r = new Retorno();
        
        if(!listaSala.esVacia()){
            String texto = "";


            for(int i = 0; i < listaSala.cantidadElementos(); i++){
                    Sala s = (Sala)listaSala.obtenerElemento(i);
                    if(i != listaSala.cantidadElementos() - 1){
                            texto += s.toString() + "#";
                    } else
                            texto += s.toString();
            }

            r.valorString = texto;
            r.resultado = Retorno.Resultado.OK;

            return r;
        } else{
            r.resultado = Retorno.Resultado.OK;
            r.valorString = "Lista vacia";
            return r;
        }
    }

    @Override
    public Retorno listarEventos() {
        Retorno ret = new Retorno();
        
        if(!listaEvento.esVacia()){
            String texto = "";

            for(int i = 0; i < listaEvento.cantidadElementos(); i++){
                    Evento e = listaEvento.obtenerElemento(i);
                    String codigo= e.Codigo;
                    String descripcion = e.Descripcion;
                    String sala = e.SalaNombre;
                    int cantidadEntradasVendidas = e.entradas.cantidadElementos();
                    int cantidadEntradasDisponibles = e.Aforo - cantidadEntradasVendidas;

                    texto += codigo + "-" + descripcion + "-" + sala + "-" + cantidadEntradasDisponibles + "-" + cantidadEntradasVendidas;

                    if(i < listaEvento.cantidadElementos() - 1){
                            texto += "#";
                    }		
            }

            ret.valorString = texto;
            ret.resultado = Retorno.Resultado.OK;

            return ret;
        } else {
            ret.valorString = "Lista vacia";
            ret.resultado = Retorno.Resultado.OK;
            
            return ret;
        }
    }

    @Override
    public Retorno listarClientes() {
        Retorno ret = new Retorno();
        
        if(listaCliente.esVacia()) {
            ret.resultado = Retorno.Resultado.OK;
            ret.valorString = "Lista vacia";
            return ret;
        } else {
            String texto = "";
            
            for(int i = 0; i < listaCliente.cantidadElementos(); i++){
                Cliente c = listaCliente.obtenerElemento(i);
                String cedula = c.Cedula;
                String nombre = c.Nombre;
                
                texto += cedula + "-" + nombre;
                
                if(i < listaCliente.cantidadElementos() - 1){
                            texto += "#";
                }
                
            }
            ret.resultado = Retorno.Resultado.OK;
            ret.valorString = texto;
                
            return ret;
        }        
    }

    @Override
    public Retorno esSalaOptima(String[][] vistaSala) {
        Retorno ret = new Retorno();
        
        if(vistaSala == null || vistaSala.length == 0){
            ret.resultado = Retorno.Resultado.OK;
            ret.valorString = "La vista no puede estar vacia";
            
            return ret;
        }
        
        int filas = vistaSala.length;
        int columnas = vistaSala[0].length;
        int columnasOptimas = 0;
        int j = 0;
        
        while(j < columnas && columnasOptimas < 2){
            int maxOcupadosConsecutivos = 0;
            int ocupadosActuales = 0;
            int libres = 0;
            
            for(int i = 0; i < filas; i ++){
                String asiento = vistaSala[i][j];
                
                if(asiento.equals("O")){
                    ocupadosActuales++;
                    if(ocupadosActuales > maxOcupadosConsecutivos){
                        maxOcupadosConsecutivos = ocupadosActuales;
                    }
                } else {
                    ocupadosActuales = 0;
                    if(asiento.equals("#")){
                        libres++;
                    }
                }
            }
            
            if(maxOcupadosConsecutivos > libres){
                columnasOptimas++;
            }
            
            j++;
        }
        
        if(columnasOptimas<2){
            ret.valorString = "No es óptimo";
        } else if(columnasOptimas >= 2){
            ret.valorString = "Es óptimo";
        }
        
        ret.resultado = Retorno.Resultado.OK;
        
        return ret;
    }

    @Override
    public Retorno listarClientesDeEvento(String codigo, int n) {  
        Retorno ret = new Retorno();
        ret.valorString = "";
        
        if(n < 1) return Retorno.error2();
        
        Evento evento = getEvento(codigo);
        if (evento == null) return Retorno.error1();
        
        int cantidadEntradas = evento.entradas.cantidadElementos();
        ret.resultado = Retorno.Resultado.OK;
        
        if(cantidadEntradas == 0){
            ret.valorString = "No hay compras";
            return ret;
        }
        
        int valorCondicion = (cantidadEntradas > n) ? cantidadEntradas-n : 0;
        
        for(var i = cantidadEntradas-1; i >= valorCondicion; i--){
            String cedula = evento.entradas.obtenerElemento(i).cedulaCliente;
            Cliente cliente = getCliente(cedula);
            
            if(!ret.valorString.isEmpty())
                    ret.valorString += "#";
            
            ret.valorString += cliente.toString(); 
        }

        return ret;
    }

    @Override
    public Retorno listarEsperaEvento() {
        //nueva lista ordenada en evento?
        
        return Retorno.noImplementada();
    }

    
    
    @Override
    public Retorno deshacerUltimasCompras(int n) {
        Retorno ret = new Retorno(ok().resultado, "");
                
        for (int i = 0; i < n; i++) {
            Entrada reg = pilaEntrada.pop();
        
            if (reg != null) {
                Cliente cliente = getCliente(reg.cedulaCliente);
                Evento evento = getEvento(reg.codigoEvento);

                cliente.entradas.eliminarFinal();
                evento.entradas.eliminarFinal();
                
                cantComprasXMes[evento.Fecha.getMonthValue()-1][evento.Fecha.getDayOfMonth()-1]--;
                
                asignarSiguienteColaEspera(evento);
                
                if(!ret.valorString.isEmpty())
                    ret.valorString += "#";
                
                ret.valorString += reg.codigoEvento + "-" + reg.cedulaCliente;
            } else {
                break;
            }
        }
        
        ret.resultado = Retorno.Resultado.OK;
        
        if(ret.valorString.isEmpty()){
            ret.valorString = "No hay compras";
        }
        
        
        return ret;
    }

    @Override
    public Retorno eventoMejorPuntuado() {
        Retorno ret = new Retorno();
        int mejorPuntuado = 0;
        
        if(listaEvento.esVacia()){
            ret.resultado = Retorno.Resultado.OK;
            ret.valorString = "No hay eventos registrados";
            
            return ret;
        }
        
	for(int i = 0; i < listaEvento.cantidadElementos(); i++){
            Evento evt = listaEvento.obtenerElemento(i);
            if(evt.calificaciones.cantidadElementos() == 0) continue;
            
            int promedioCalificacion = evt.PuntajeTotal / evt.calificaciones.cantidadElementos();
            
            if(promedioCalificacion > mejorPuntuado){ 
		mejorPuntuado = promedioCalificacion;
		ret.valorString = evt.toString();
            } else if(promedioCalificacion == mejorPuntuado){
		ret.valorString += "#" + evt.toString();
            }
	}
		
	if(ret.valorString == null) 
            ret.valorString = "No hay calificaciones";
        
	ret.resultado = Retorno.Resultado.OK;
		
	return ret;	
    }

    @Override
    public Retorno comprasDeCliente(String cedula) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno comprasXDia(int mes) {
        Retorno ret = new Retorno();
        
        if(mes > 12 || mes <= 0){
            ret.resultado = Retorno.Resultado.ERROR_1;
            ret.valorString = "Mes invalido";
            
            return ret;
        }
        
        mes--;
        for(int i = 0; i < cantComprasXMes[mes].length; i++){
            if(cantComprasXMes[mes][i] == 0) continue;
            
            if(ret.valorString == null){
                ret.valorString = String.valueOf(i+1) + "-" + cantComprasXMes[mes][i];
            } else {
                ret.valorString += "#" + String.valueOf(i+1) + "-" + cantComprasXMes[mes][i];
            }
        }
        
        if(ret.valorString == null)
            ret.valorString = "No hay entradas vendidas para ese mes";
        
        ret.resultado = Retorno.Resultado.OK;
        
        return ret;
    }    
}
