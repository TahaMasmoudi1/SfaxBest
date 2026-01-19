package Services;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSender {
    private static final String EMAIL_FROM = "ssfaxbest@gmail.com";
    private static final String APP_PASSWORD = "lyqa xmnf ibgs ljey";

    public void sendVerificationCode(String email,String verificationCode)  throws Exception{
        String subject = "SfaxBest – Email Verification Code";

        String body =
                "Hello,\n\n" +
                        "Thank you for creating an account on SfaxBest.\n\n" +
                        "Your email verification code is:\n\n" +
                        "🔐  " + verificationCode + "\n\n" +
                        "This code is valid for 10 minutes.\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "SfaxBest Team";
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);


    }

    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private static Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}