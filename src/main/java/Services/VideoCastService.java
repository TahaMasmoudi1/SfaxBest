package Services;

import DAO.CastMemberDAO;
import DAO.MultimediaDAO;
import DAO.VideoCastDAO;
import entities.CastMember;
import entities.Multimedia;
import entities.VideoCast;
import entities.VideoCastRole;
import utils.TraHelper;

public class VideoCastService {
    public void addCastMember(Long multimediaId, Long castMemberId, VideoCastRole role) {
        TraHelper.write(em -> {
            VideoCastDAO videoCastDAO = new VideoCastDAO(em);
            MultimediaDAO multimediaDAO = new MultimediaDAO(em);
            CastMemberDAO castMemberDAO = new CastMemberDAO(em);
            CastMember castMember = castMemberDAO.findById(castMemberId);
            Multimedia multimedia = multimediaDAO.findById(multimediaId);
            VideoCast videoCast=new VideoCast(multimedia,castMember,role);
            videoCastDAO.save(videoCast);
        });

    }
}
