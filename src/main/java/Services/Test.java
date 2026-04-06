package Services;


import DAO.FilmDAO;
import entities.Film;
import entities.VideoCastRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        VideoCastService service =
                new VideoCastService();

        VideoCastRole[] roles =
                VideoCastRole.values();

        Long[] multimediaIds = {
                6L, 15L, 16L, 17L,
                18L, 19L, 20L,
                21L, 22L, 23L,
                24L, 25L
        };

        int castId = 1;

        for (Long multimediaId : multimediaIds) {

            // assign 3 roles per multimedia
            for (int i = 0; i < 3; i++) {

                VideoCastRole role =
                        roles[(castId - 1) % roles.length];

                try {

                    service.addCastMember(
                            multimediaId,
                            (long) castId,
                            role
                    );

                } catch (Exception e) {

                    System.out.println(
                            "⚠️ Skipped duplicate or error: " +
                                    multimediaId + "-" +
                                    castId + "-" +
                                    role
                    );

                }

                castId++;

                if (castId > 15)
                    castId = 1;
            }
        }




    }}