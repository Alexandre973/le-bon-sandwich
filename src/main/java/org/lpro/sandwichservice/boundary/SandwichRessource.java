package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.Sandwich;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SandwichRessource extends CrudRepository<Sandwich, String> {
    List<Sandwich> findByCategorieId(String categorieId);
    List<Sandwich> findByPain(String pain);
    List<Sandwich> findByPrix(Integer prix);
    List<Sandwich> findByPrixAndPain(Integer prix, String pain);
    List<Sandwich> findAll(Pageable pegeable);
}