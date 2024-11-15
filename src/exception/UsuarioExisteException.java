package exception;

public class UsuarioExisteException extends Exception{
    public UsuarioExisteException(String mensaje){
        super(mensaje);
    }
}
