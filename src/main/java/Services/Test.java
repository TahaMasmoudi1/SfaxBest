package Services;


import DAO.FilmDAO;
import entities.Film;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        FilmService service = new FilmService();

        List<Long> action = Arrays.asList(1L);
        List<Long> animation = Arrays.asList(2L);
        List<Long> superhero = Arrays.asList(3L);
        List<Long> adventure = Arrays.asList(4L);

        service.save(
                "Asterix and Obelix Take On Caesar",
                "Asterix and Obelix fight Romans.",
                "videos/asterix_trailer.mp4",
                "images/asterix_banner.jpg",
                1999,
                5400,
                "videos/asterix.mp4",
                adventure,
                "images/asterix_poster.jpg"
        );

        service.save(
                "Avengers",
                "Superheroes unite to save the world.",
                "videos/avengers_trailer.mp4",
                "images/avengers_banner.jpg",
                2012,
                8400,
                "videos/avengers.mp4",
                superhero,
                "images/avengers_poster.jpg"
        );

        service.save(
                "Batman The Dark Knight Rises",
                "Batman faces Bane.",
                "videos/batman_trailer.mp4",
                "images/batman_banner.jpg",
                2012,
                9900,
                "videos/batman.mp4",
                superhero,
                "images/batman_poster.jpg"
        );

        service.save(
                "Kung Fu Panda",
                "Po becomes the Dragon Warrior.",
                "videos/kungfu_trailer.mp4",
                "images/kungfu_banner.jpg",
                2008,
                5520,
                "videos/kungfu.mp4",
                animation,
                "images/kungfu_poster.jpg"
        );

        service.save(
                "Pokemon The Movie",
                "Ash and Pikachu face Mewtwo.",
                "videos/pokemon_trailer.mp4",
                "images/pokemon_banner.jpg",
                1998,
                4800,
                "videos/pokemon.mp4",
                animation,
                "images/pokemon_poster.jpg"
        );

        service.save(
                "Green Lantern",
                "Hal Jordan becomes Green Lantern.",
                "videos/greenlantern_trailer.mp4",
                "images/greenlantern_banner.jpg",
                2011,
                6900,
                "videos/greenLantern.mp4",
                superhero,
                "images/greenLantern_poster.jpg"
        );

        service.save(
                "Spiderman",
                "Peter Parker becomes Spiderman.",
                "videos/spiderman_trailer.mp4",
                "images/spiderman_banner.jpg",
                2002,
                7200,
                "videos/spiderman-md-web.mp4",
                superhero,
                "images/spiderman_poster.jpg"
        );

        service.save(
                "Superman",
                "Superman protects humanity.",
                "videos/superman_trailer.mp4",
                "images/superman_banner.jpg",
                2025,
                7200,
                "videos/superman.mp4",
                superhero,
                "images/superman_poster.jpg"
        );

        service.save(
                "The Avengers Hulk Smash",
                "Hulk smashes enemies.",
                "videos/hulk_trailer.mp4",
                "images/hulk_banner.jpg",
                2012,
                300,
                "videos/hulk_smash.mp4",
                action,
                "images/hulk_poster.jpg"
        );

        service.save(
                "Matrix Lobby Shootout",
                "Neo fights agents in the lobby.",
                "videos/matrix_trailer.mp4",
                "images/matrix_banner.jpg",
                1999,
                300,
                "videos/matrix_lobby.mp4",
                action,
                "images/matrix_poster.jpg"
        );

        service.save(
                "Gladiator Fight Scene",
                "Epic gladiator battle.",
                "videos/gladiator_trailer.mp4",
                "images/gladiator_banner.jpg",
                2000,
                300,
                "videos/gladiator.mp4",
                action,
                "images/gladiator_poster.jpg"
        );

        System.out.println("All movies inserted successfully!");
    }



    }