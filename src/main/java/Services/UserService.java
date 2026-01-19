package Services;

import DAO.UserDAO;
import Security.Encoder;
import Security.VerificationCode;
import entities.User;
import exceptions.AuthException;
import exceptions.ValidationException;

import java.time.Instant;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void register(String username, String password, String email) {
        email = email.trim().toLowerCase();
        if(userDAO.checkEmail(email)){
            throw new ValidationException("Email already exists");
        }
        User user=new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(Encoder.encode(password));
        user.setEmailVerified(false);
        String code= VerificationCode.generateVerificationCode();
        user.setEmailVerificationHash(Encoder.encode(code));
        user.setEmailVerificationExpiresAt(Instant.now().plusSeconds(600));
        userDAO.save(user);
        EmailSender emailSender=new EmailSender();
        try {
            emailSender.sendVerificationCode(email,code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void emailVerification(String email, String code){
        email=email.trim().toLowerCase();
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
        if(!Encoder.encode(code).equals(user.getEmailVerificationHash())){
            throw new ValidationException("Invalid verification code");
        }
        user.setEmailVerified(true);
        user.setEmailVerificationExpiresAt(null);
        user.setEmailVerificationHash(null);
    }
    public User login(String username, String password) {
        User user=userDAO.findByUsername(username);
        if(user==null){
            throw new AuthException("Invalid Credentials");
        }
        if(!user.getPasswordHash().equals(Encoder.encode(password))){
            throw new AuthException("Invalid Credentials");
        }
        if(!user.isEmailVerified()){
            throw new AuthException("Please verify your email");
        }
        return user;
    }

    public void delete(User user) {
        userDAO.delete(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

}
