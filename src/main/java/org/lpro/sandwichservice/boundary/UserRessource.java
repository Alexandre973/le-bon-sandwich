package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRessource extends CrudRepository<User, String> {
    User findByUsername(String username);
}
