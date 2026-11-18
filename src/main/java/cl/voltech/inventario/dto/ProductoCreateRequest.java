package cl.voltech.inventario.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoCreateRequest(
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    String nombre,

    String descripcion,

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Digits(integer = 10, fraction = 0, message = "El precio debe ser un entero de hasta 10 dígitos")
    BigDecimal precio,

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock,

    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    String imagenUrl,

    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El ID de la categoría debe ser positivo")
    Integer categoriaId
) {
}
