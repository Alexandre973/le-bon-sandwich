package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemResource extends CrudRepository<Item, String> {
    List<Item> findByCommande(String commande);
}