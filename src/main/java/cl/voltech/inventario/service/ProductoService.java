package cl.voltech.inventario.service;

import org.springframework.stereotype.Service;

import cl.voltech.inventario.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    
    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }
}
