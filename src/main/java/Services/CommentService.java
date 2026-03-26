package Services;

import DAO.CommentDAO;
import DAO.MultimediaDAO;
import DAO.UserDAO;
import entities.Comment;
import entities.Multimedia;
import entities.User;
import utils.TraHelper;

import java.util.List;

public class CommentService {
    public void add(Long idUser,Long idMultimedia,String content) {
        TraHelper.write(em -> {
            CommentDAO commentDAO=new CommentDAO(em);
            MultimediaDAO multimediaDAO = new MultimediaDAO(em);
            UserDAO userDAO = new UserDAO(em);
            User user=userDAO.findById(idUser);
            Multimedia multimedia= multimediaDAO.findById(idMultimedia);
            Comment comment = new Comment(user,multimedia,content);
            commentDAO.save(comment);
        });
    }
    public void delete(Long idComment) {
        TraHelper.write(em -> {
            CommentDAO commentDAO = new CommentDAO(em);
            Comment comment=commentDAO.findById(idComment);
            comment.setIsDeleted(true);
        });
    }
    public void update(Long idComment,String content) {
        TraHelper.write(em -> {
            CommentDAO commentDAO = new CommentDAO(em);
            Comment comment=commentDAO.findById(idComment);
            comment.setContent(content);
        });
    }
    public void report(Long idComment) {
       TraHelper.write(em -> {
           CommentDAO commentDAO = new CommentDAO(em);
           Comment comment=commentDAO.findById(idComment);
           int signal= comment.getNbrSignals();
           comment.setNbrSignals(signal+1);
       });
    }
    public List<Comment> findAllByMultimedia(Long idMultimedia) {
         return TraHelper.read(em -> {
            CommentDAO commentDAO = new CommentDAO(em);
            return commentDAO.findAllById(idMultimedia);
        });
    }
    public List<Comment> findAll() {
        return TraHelper.read(em -> {
            CommentDAO commentDAO = new CommentDAO(em);
            return commentDAO.findAll();
        });
    }

}
