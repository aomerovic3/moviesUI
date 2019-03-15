package com.movies.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.movies.exceptions.SecurityContextException;
import com.movies.exceptions.UnauthorizedException;
import com.movies.model.Movie;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {

    public HttpEntity<String> createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>("parameters", headers);

    }

    public List<Movie> getMovies(String search, String token) throws Exception {

        final String url = "http://127.0.0.1:8085/service/movies/search/" + search;
        HttpEntity<String> entity = createHeaders(token);
        System.out.println("token " + token);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Movie>>() {
            }).getBody();

        } catch (Exception e) {
            if(e.getMessage().contains("401")){
                throw new UnauthorizedException("You don't have permission for this action");
            }
            throw e;
        }
    }

    public Movie getMovieDetail(String imdbID, String token) throws Exception {
        final String url = "http://127.0.0.1:8085/service/movies/details/" + imdbID;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = createHeaders(token);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, Movie.class).getBody();
        } catch (Exception e) {
            if(e.getMessage().contains("401")){
                throw new UnauthorizedException("You don't have permission for this action");
            }
            throw e;
        }
    }

    public String getToken() throws SecurityContextException, UnauthorizedException {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            System.out.println("Could not load authentication from security context!");
            throw new SecurityContextException("Could not load authentication from security context!");
        }

        if (!authentication.isAuthenticated()) {
            System.out.println("Not authenticated!");
            throw new UnauthorizedException("Not authenticated!");
        }

        KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal
                = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        return keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
    }
}
