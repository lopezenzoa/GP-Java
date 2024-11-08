package interfaces;

import java.util.List;

public interface ABMLable<T> {
    T agregar(T dato);
    T eliminar(T dato);
    T modificar(T dato, T nuevoDato);
    List<T> listar();
}
