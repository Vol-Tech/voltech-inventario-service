package cl.voltech.inventario.exception;

public class CategoriaNoEncontradaException extends RuntimeException {

    public CategoriaNoEncontradaException(Integer id) {
        super("Categoría no encontrada: " + id);
    }
}
