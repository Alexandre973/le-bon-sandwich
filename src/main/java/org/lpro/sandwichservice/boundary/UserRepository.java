package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
