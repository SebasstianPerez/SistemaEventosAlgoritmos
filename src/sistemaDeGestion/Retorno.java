package sistemaDeGestion;

public class Retorno {

    public enum Resultado {
        OK, ERROR_1, ERROR_2, ERROR_3, ERROR_4, ERROR_5, NO_IMPLEMENTADA
    }

    public int valorEntero;
    public String valorString;
    public boolean valorbooleano;
    public Resultado resultado;

    // Constructores
    public Retorno(){
    }
    
    public Retorno(Resultado resultado) {
        this.resultado = resultado;
    }

    public Retorno(Resultado resultado, String valorString) {
        this.resultado = resultado;
        this.valorString = valorString;
    }

    public Retorno(Resultado resultado, boolean valorbooleano) {
        this.resultado = resultado;
        this.valorbooleano = valorbooleano;
    }

    public Retorno(Resultado resultado, int valorEntero) {
        this.resultado = resultado;
        this.valorEntero = valorEntero;
    }

    public Retorno(Resultado resultado, int valorEntero, String valorString, boolean valorbooleano) {
        this.resultado = resultado;
        this.valorEntero = valorEntero;
        this.valorString = valorString;
        this.valorbooleano = valorbooleano;
    }

    // Métodos
    public static Retorno ok() {
        return new Retorno(Resultado.OK);
    }

    public static Retorno error1() {
        return new Retorno(Resultado.ERROR_1);
    }

    public static Retorno error2() {
        return new Retorno(Resultado.ERROR_2);
    }

    public static Retorno error3() {
        return new Retorno(Resultado.ERROR_3);
    }

    public static Retorno error4() {
        return new Retorno(Resultado.ERROR_4);
    }

    public static Retorno error5() {
        return new Retorno(Resultado.ERROR_5);
    }

    public static Retorno noImplementada() {
        return new Retorno(Resultado.NO_IMPLEMENTADA);
    }
}