package Services;

import DAO.CastMemberDAO;
import DAO.MultimediaDAO;
import entities.CastMember;
import entities.Multimedia;
import entities.VideoCast;
import entities.VideoCastRole;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

public class MultimediaService {

    public void addCastToMultimedia(long idMultimedia, long castId, VideoCastRole role) {
        TraHelper.write(em -> {
            MultimediaDAO multimediaDAO=new MultimediaDAO(em);
            Multimedia multimedia = multimediaDAO.findById(idMultimedia);
            if (multimedia == null) {
                throw new NoResultException("Multimedia with id " + idMultimedia + " does not exist");
            }
            CastMemberDAO castMemberDAO=new CastMemberDAO(em);
            CastMember castMember = castMemberDAO.findById(castId);
            if (castMember == null) {
                throw new NoResultException("CastMember with id " + castId + " does not exist");
            }
            VideoCast videoCast=new VideoCast(multimedia, castMember, role);
            multimedia.getVideoCasts().add(videoCast);

        });

    }
}
