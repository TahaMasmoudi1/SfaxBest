package Services;

import DAO.CategoryDAO;
import DAO.DocumentaryDAO;
import entities.Documentary;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class DocumentaryService {

    public void save(String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds,
                     String path_video, List<Long> categorieIds,String pathPoster) {

        TraHelper.write(em ->{

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);

            Documentary documentary =
                    new Documentary(title,description,release_year,
                            path_trailer,path_banner,
                            duration_seconds,path_video,pathPoster);

            documentary.getCategories()
                    .addAll(categoryDAO.listByIds(categorieIds));

            documentaryDAO.save(documentary);

        });

    }


    public void delete(long id) throws NoResultException {

        TraHelper.write(em ->{

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            Documentary documentary =
                    documentaryDAO.findById(id);

            if(documentary==null){
                throw new NoResultException("Documentary Not Found");
            }

            documentaryDAO.delete(documentary);

        });

    }


    public void update(Long documentaryId,String title, String description,
                       String path_trailer , String path_banner,
                       int release_year, int duration_seconds,
                       String path_video, List<Long> categorieIds,
                       String pathPoster) throws NoResultException {

        TraHelper.write(em ->{

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);

            Documentary documentary =
                    documentaryDAO.findById(documentaryId);

            if(documentary==null){
                throw new NoResultException("Documentary Not Found");
            }

            documentary.setTitle(title);
            documentary.setDescription(description);
            documentary.setPathTrailer(path_trailer);
            documentary.setPathBanner(path_banner);
            documentary.setReleaseYear(release_year);
            documentary.setDurationSeconds(duration_seconds);
            documentary.setPathVideo(path_video);

            documentary.getCategories().clear();

            documentary.getCategories()
                    .addAll(categoryDAO.listByIds(categorieIds));

            documentary.setPathPoster(pathPoster);

        });

    }


    public List<Documentary> listAllWithCategories() {

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listAllWithCategories();

        });

    }


    public Documentary listDocumentaryDetails(long id) {

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listDocumentaryDetails(id);

        });

    }


    public List<Documentary> listbyManyCategory(List<Long> ids,
                                                int offset,int limit){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listbyManyCategory(
                    ids,
                    offset,
                    limit
            );

        });

    }


    public List<Documentary> listByReleaseYear(int releaseYear,
                                               int offset,int limit){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listByReleaseYear(
                    releaseYear,
                    offset,
                    limit
            );

        });

    }


    public List<Documentary> listDocumentarySearch(String search,
                                                   int offset,int limit){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listDocumentarySearch(
                    search,
                    offset,
                    limit
            );

        });

    }


    public List<Documentary> listDocumentarySearchWithCategory(
            String search,
            List<Long> categoryIds,
            int offset,
            int limit){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO
                    .listDocumentarySearchWithCategory(
                            search,
                            categoryIds,
                            offset,
                            limit
                    );

        });

    }


    public List<Documentary> listByCategoryName(
            String categoryName,
            int offset,
            int limit){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.listByCategoryName(
                    categoryName,
                    offset,
                    limit
            );

        });

    }


    public int countDocumentary(){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.countDocumentary();

        }).intValue();

    }


    public int countFilms(){

        return TraHelper.read(em -> {

            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);

            return documentaryDAO.countFilms().intValue();

        });

    }

}