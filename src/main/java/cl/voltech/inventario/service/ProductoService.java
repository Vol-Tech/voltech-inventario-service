package cl.voltech.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.voltech.inventario.dto.ProductoCreateRequest;
import cl.voltech.inventario.dto.ProductoResponse;
import cl.voltech.inventario.exception.CategoriaNoEncontradaException;
import cl.voltech.inventario.exception.ProductoNoEncontradoException;
import cl.voltech.inventario.mapper.ProductoMapper;
import cl.voltech.inventario.model.Categoria;
import cl.voltech.inventario.model.Producto;
import cl.voltech.inventario.repository.CategoriaRepository;
import cl.voltech.inventario.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepo, CategoriaRepository categoriaRepo,
            ProductoMapper productoMapper) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
        this.productoMapper = productoMapper;
    }

    public ProductoResponse crearProducto(ProductoCreateRequest request) {
        Categoria categoria = categoriaRepo.findById(request.categoriaId())
            .orElseThrow(() -> new CategoriaNoEncontradaException(request.categoriaId()));
        Producto nuevoProducto = productoMapper.toModel(request);
        nuevoProducto.setCategoria(categoria);

        return productoMapper.toResponse(productoRepo.save(nuevoProducto));
    }

    public List<ProductoResponse> listarTodosProductos() {
        return productoRepo.findAll().stream()
            .map(productoMapper::toResponse)
            .toList();
    }

    public Producto buscarProductoId(Integer id) {
        return productoRepo.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public Producto actualizarProducto(Integer id, Producto datosProducto) {
        buscarProductoId(id);
        datosProducto.setId(id);
        return productoRepo.save(datosProducto);
    }
}
