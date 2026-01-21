package Services;

import DAO.CategoryDAO;
import DAO.DocumentaryDAO;
import DAO.FilmDAO;
import entities.Documentary;
import entities.Film;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

import java.util.List;

public class DocumentaryService {
    public void save(String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds) {
        TraHelper.write(em ->{
            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Documentary documentary = new Documentary(title,description,release_year,path_trailer,path_banner,duration_seconds,path_video);
            documentary.getCategories().addAll(categoryDAO.findById(categorieIds));
            documentaryDAO.save(documentary);
        });

    }

    public void delete(long id) throws NoResultException {
        TraHelper.write(em ->{
            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
            Documentary documentary =documentaryDAO.findById(id);
            if(documentary==null){
                throw new NoResultException("Documentary Not Found");
            }
            documentaryDAO.delete(documentary);
        });
    }
    public void update(Long documentaryId,String title, String description, String path_trailer
            , String path_banner, int release_year, int duration_seconds, String path_video, List<Long> categorieIds) throws NoResultException {
        TraHelper.write(em ->{
            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
            CategoryDAO categoryDAO = new CategoryDAO(em);
            Documentary documentary =documentaryDAO.findById(documentaryId);
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
            documentary.getCategories().addAll(categoryDAO.findById(categorieIds));
        });
    }
    public List<Documentary> findAll(int offset, int limit) throws NoResultException {
        return TraHelper.read(em -> {
            DocumentaryDAO documentaryDAO=new DocumentaryDAO(em);
                return documentaryDAO.findAll(offset, limit);
        });
    }
}
