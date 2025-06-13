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
public class Calificacion implements Comparable<Calificacion>{
    public String cedula;
    public int puntaje;
    public String comentario;

    public Calificacion() {
    }
    
    public Calificacion(String cedula) {
        this.cedula = cedula;
    }

    public Calificacion(String cedula, int puntaje, String comentario) {
        this.cedula = cedula;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    @Override
    public int compareTo(Calificacion o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        final Calificacion other = (Calificacion) obj;
        return Objects.equals(this.cedula, other.cedula);
    }
    
    
    
    
}
