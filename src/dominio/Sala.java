/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Sebastian
 */
public class Sala implements Comparable<Sala> {
    
    public String Nombre;
    public int Capacidad;

    public Sala(){}
    
    public Sala(String nombre, int capacidad){
    
        Nombre = nombre;
        Capacidad = capacidad;
    }
    
    @Override
    public String toString(){
        return Nombre +"-"+Capacidad;
    }

    @Override
    public int compareTo(Sala o) {
        int ret = -1;
        
        //TODO revisar
        if(this.Nombre.equals(o.Capacidad)){
            ret = 0;
        }
        
        return ret;       
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Sala otra = (Sala) obj;
        
        return this.Nombre.equals(otra.Nombre);
        
    }
}
