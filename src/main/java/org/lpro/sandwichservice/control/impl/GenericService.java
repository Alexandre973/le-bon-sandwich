package org.lpro.sandwichservice.control.impl;



import org.lpro.sandwichservice.entity.Commande;
import org.lpro.sandwichservice.entity.User;

import java.util.List;

public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<Commande> findAllCommandes();
}
