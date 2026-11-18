package cl.voltech.inventario.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Integer id) {
        super("Producto no encontrado: " + id);
    }
}
