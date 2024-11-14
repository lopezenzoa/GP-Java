package interfaces;

import java.util.List;

public interface ABMLable<T>{
    void alta();
    void baja();
    void modificar( T nuevoDato);
}
