package org.lpro.sandwichservice.control.impl;

import org.lpro.sandwichservice.boundary.UserRessource;
import org.lpro.sandwichservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRessource userRessource;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRessource.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("Probleme avec user %s", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);

        return userDetails;
    }
}
