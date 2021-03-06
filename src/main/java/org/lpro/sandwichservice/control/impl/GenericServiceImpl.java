package org.lpro.sandwichservice.control.impl;


import org.lpro.sandwichservice.boundary.CommandeResource;
import org.lpro.sandwichservice.boundary.UserRessource;
import org.lpro.sandwichservice.entity.Commande;
import org.lpro.sandwichservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRessource userRessource;

    @Autowired
    private CommandeResource commandeRepository;

    @Override
    public User findByUsername(String username) {
        return userRessource.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRessource.findAll();
    }

    @Override
    public List<Commande> findAllCommandes() {
        return (List<Commande>)commandeRepository.findAll();
    }
}
