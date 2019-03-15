package com.movies.controller;

import com.movies.model.Movie;
import com.movies.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private Service service;

    @Autowired
    public HomeController(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/movie")
    public String movies() {
        return "index";
    }

    @RequestMapping(value = "/movie/details")
    public String getDetails(@RequestParam("imdbID") String imdbID, Model model) {
        try {
            Movie movie = service.getMovieDetail(imdbID, service.getToken());
            model.addAttribute("movie", movie);
            return "movieInfo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "unauthorized";
        }
    }

    @RequestMapping(value = "/movie/search")
    public String getMovies(@RequestParam(name = "search") String search, Model model){
        try {
            List<Movie> movies = service.getMovies(search, service.getToken());
            model.addAttribute("movies", movies);
            return "movieList";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "unauthorized";
        }
    }

    @RequestMapping(value = "/403")
    public String Unauthorized() {
        return "unauthorized";
    }
}