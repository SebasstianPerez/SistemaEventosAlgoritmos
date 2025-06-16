/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Objects;

/**
 *
 * @author Sebastian
 */
public class Entrada implements Comparable<Entrada> {
    public String codigoEvento;
    public String cedulaCliente;
    public boolean estado;//?

    public Entrada() {
        
    }

    public Entrada(String cedulaCliente, String codigoEvento) {
        this.codigoEvento = codigoEvento;
        this.cedulaCliente = cedulaCliente;
        this.estado = true;
    }

      @Override
    public int compareTo(Entrada o) {
        int cmp = this.codigoEvento.compareToIgnoreCase(o.codigoEvento);
        if (cmp != 0) {
            return cmp;
        }
        return this.cedulaCliente.compareToIgnoreCase(o.cedulaCliente);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.codigoEvento, other.codigoEvento)) {
            return false;
        }
        return Objects.equals(this.cedulaCliente, other.cedulaCliente);
    }
    
    
}
