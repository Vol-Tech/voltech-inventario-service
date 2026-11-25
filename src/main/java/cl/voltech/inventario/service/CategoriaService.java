package cl.voltech.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.voltech.inventario.dto.CategoriaResponse;
import cl.voltech.inventario.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepo;

    public CategoriaService(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepo.findAll().stream()
                .map(categoria -> new CategoriaResponse(categoria.getId(), categoria.getNombre()))
                .toList();
    }
}
