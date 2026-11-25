package cl.voltech.inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.inventario.dto.ProductoCreateRequest;
import cl.voltech.inventario.dto.ProductoResponse;
import cl.voltech.inventario.service.ProductoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoServ;

    public ProductoController(ProductoService productoServ) {
        this.productoServ = productoServ;
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> agregarProducto(@Valid @RequestBody ProductoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoServ.crearProducto(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        return ResponseEntity.ok(productoServ.listarTodosProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> listarProductosId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoServ.obtenerProductoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        productoServ.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@PathVariable Integer id,
            @Valid @RequestBody ProductoCreateRequest request) {
        return ResponseEntity.ok(productoServ.actualizarProducto(id, request));
    }
}
