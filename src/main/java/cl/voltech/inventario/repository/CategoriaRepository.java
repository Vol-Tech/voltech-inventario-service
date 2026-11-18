package cl.voltech.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.voltech.inventario.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
