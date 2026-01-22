package Services;

import DAO.CastMemberDAO;
import entities.CastMember;
import jakarta.persistence.NoResultException;
import utils.TraHelper;

public class CastMemberService {
    public void add(String name,String lastName){
        TraHelper.write(em -> {
            CastMemberDAO castMemberDAO=new CastMemberDAO(em);
            CastMember castMember=new CastMember(name,lastName);
            castMemberDAO.save(castMember);
        });
    }

    public void delete(long id){
        TraHelper.write(em -> {
            CastMemberDAO castMemberDAO=new CastMemberDAO(em);
            CastMember castMember=castMemberDAO.findById(id);
            if(castMember==null){
                throw new NoResultException("No CastMember With ID="+id);
            }
            castMemberDAO.delete(castMember);
        });
    }
    public void update(long id,String name,String lastName) throws NoResultException{
        TraHelper.write(em -> {
            CastMemberDAO castMemberDAO=new CastMemberDAO(em);
            CastMember castMember=castMemberDAO.findById(id);
            if(castMember==null){
                throw new NoResultException("No CastMember With ID="+id);
            }
            castMember.setName(name);
            castMember.setLastName(lastName);
        });
    }
}
