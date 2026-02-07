package Services;


import entities.Film;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        FilmService filmService = new FilmService();
        List<Film> films = filmService.listAllWithCategories();
        for (Film film : films) {
            System.out.println(film);
        }



    }}