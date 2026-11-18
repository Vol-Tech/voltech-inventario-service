package cl.voltech.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.voltech.inventario.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Override
    @EntityGraph(attributePaths = "categoria")
    List<Producto> findAll();
}
