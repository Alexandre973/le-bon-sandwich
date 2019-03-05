package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.Commande;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandeResource extends CrudRepository<Commande, String> {
    List<Commande> findAll(Pageable pegeable);
}