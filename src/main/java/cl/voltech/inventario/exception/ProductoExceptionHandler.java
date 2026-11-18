package cl.voltech.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductoExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ProblemDetail productoNoEncontrado(ProductoNoEncontradoException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setDetail(exception.getMessage());
        
        return problem;
    }

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    public ProblemDetail categoriaNoEncontrada(CategoriaNoEncontradaException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setDetail(exception.getMessage());

        return problem;
    }
}
