package com.nnk.springboot.configuration;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe permettant de personnaliser comment Spring security récupère les utilisateurs depuis la bdd
 * pour les authentifier
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    /**
     * repository injecté utilisé pour accéder à la bdd
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Méthode permettant de récupérer un utilisateur par son nom d'utilisateur
     * @param username nom d'utilisateur
     * @return Objet contenant tous les details sur l'utilisateur
     * @throws UsernameNotFoundException Exception générée si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userFromBdd = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return User
                .withUsername(userFromBdd.getUsername())
                .password(userFromBdd.getPassword())
                .roles(userFromBdd.getRole())
                .build();
    }
}
