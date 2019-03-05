package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.Categorie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategorieResource extends CrudRepository<Categorie, String> {
    List<Categorie> findAll(Pageable pegeable);
}