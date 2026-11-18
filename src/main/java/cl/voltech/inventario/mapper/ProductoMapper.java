package cl.voltech.inventario.mapper;

import org.springframework.stereotype.Component;

import cl.voltech.inventario.dto.ProductoCreateRequest;
import cl.voltech.inventario.dto.ProductoResponse;
import cl.voltech.inventario.model.Producto;

@Component
public class ProductoMapper {

    public Producto toModel(ProductoCreateRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setImagenUrl(request.imagenUrl());
        return producto;
    }

    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getStock(),
            producto.getImagenUrl(),
            producto.getCategoria().getId(),
            producto.getCategoria().getNombre(),
            producto.getFechaCreacion()
        );
    }
}
