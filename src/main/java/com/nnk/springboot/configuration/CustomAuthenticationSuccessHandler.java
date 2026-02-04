package com.nnk.springboot.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/**
 * Classe pour personnaliser la page d'accueil en fonction du role de l'utilisateur
 * Les utilisateurs avec le role ADMIN sont redirigé vers la page /
 * Les utilisateurs avec le role USER sont redirigé vers /bidList/list
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Méthode appelée quand l'utilisateur a été authentifié avec succès.
     * @param request requete reçue par le serveur envoyer par le client
     * @param response reponse que le serveur va envoyer au client
     * @param authentication Objet contenant les détails de l'utilisateur concernant l'authentification
     * @throws IOException Exception générée en cas d'erreur dans la redirection
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/";

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            redirectUrl = "/bidList/list";
        }

        response.sendRedirect(redirectUrl);
    }
}