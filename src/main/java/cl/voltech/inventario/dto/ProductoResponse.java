package cl.voltech.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
    Integer id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    Integer stock,
    String imagenUrl,
    Integer categoriaId,
    String categoriaNombre,
    LocalDateTime fechaCreacion
) {
}
