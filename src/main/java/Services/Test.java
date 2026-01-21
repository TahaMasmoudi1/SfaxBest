package Services;


import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        FilmService filmService = new FilmService();
        filmService.update(2L,"Taha","Modified","pathtrailer","pathbanner",2025,7500,"string",ids);
}}