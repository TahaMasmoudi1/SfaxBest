package Services;

import DAO.UserDAO;
import Security.Encoder;
import Security.VerificationCode;
import entities.User;
import exceptions.AuthException;
import exceptions.ValidationException;
import utils.TraHelper;

import java.time.Instant;

public class UserService {
    private final EmailSender emailSender=new EmailSender();


    public void register(String username, String password, String userEmail) {
        String email = userEmail.trim().toLowerCase();
        String code= VerificationCode.generateVerificationCode();
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            if(userDAO.checkEmail(email)){
                throw new ValidationException("Email already exists");
            }
            if(userDAO.checkUsername(username)){
                throw new ValidationException("Username already exists");
            }
            User user=new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPasswordHash(Encoder.encode(password));
            user.setEmailVerified(false);
            user.setEmailVerificationHash(Encoder.encode(code));
            user.setEmailVerificationExpiresAt(Instant.now().plusSeconds(600));
            userDAO.save(user);
        });
        try {
            emailSender.sendVerificationCode(email,code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void emailVerification(String userEmail, String code){
        String email=userEmail.trim().toLowerCase();
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            User user=userDAO.findByEmail(email);
            if(user==null){
                throw new ValidationException("User not found");
            }
            if(user.isEmailVerified()){
                throw new ValidationException("Email already verified");
            }
            if(user.getEmailVerificationExpiresAt().isBefore(Instant.now())||user.getEmailVerificationExpiresAt()==null){
                throw new ValidationException("Code expired");
            }
            if(!Encoder.matches(code,user.getEmailVerificationHash())){
                throw new ValidationException("Invalid verification code");
            }
            user.setEmailVerified(true);
            user.setEmailVerificationExpiresAt(null);
            user.setEmailVerificationHash(null);
            userDAO.update(user);
        });

    }
    public User login(String username, String password) {
        return TraHelper.read(em -> {
            UserDAO userDAO=new UserDAO(em);
            User user=userDAO.findByUsername(username);
            if(user==null){
                throw new AuthException("Invalid Credentials");
            }
            if(!Encoder.matches(password,user.getPasswordHash())){
                throw new AuthException("Invalid Credentials");
            }
            if(!user.isEmailVerified()){
                throw new AuthException("Please verify your email");
            }
            return user;
        });

    }
    public void resendVerificationCode(String userEmail){
        String code= VerificationCode.generateVerificationCode();
        String email=userEmail.trim().toLowerCase();
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            User user=userDAO.findByEmail(email);
            if(user==null){
                throw new ValidationException("User not found");
            }
            if(user.isEmailVerified()){
                throw new ValidationException("Email already verified");
            }
            user.setEmailVerificationHash(Encoder.encode(code));
            user.setEmailVerificationExpiresAt(Instant.now().plusSeconds(600));
            userDAO.update(user);
            try {
                emailSender.sendVerificationCode(email,code);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void delete(User user) {
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            userDAO.delete(user);
        });
    }

    public void update(User user) {
        TraHelper.write(em -> {
            UserDAO userDAO=new UserDAO(em);
            userDAO.update(user);
        });
    }

}
