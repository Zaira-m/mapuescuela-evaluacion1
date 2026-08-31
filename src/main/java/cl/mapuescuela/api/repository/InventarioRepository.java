package cl.mapuescuela.api.repository;

import cl.mapuescuela.api.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository
        extends JpaRepository<Inventario, Integer> {
}