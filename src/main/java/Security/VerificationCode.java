package Security;

import java.security.SecureRandom;

public class VerificationCode {
    public VerificationCode() {
    }
    private static final  SecureRandom random = new SecureRandom();
    public static String generateVerificationCode() {
        int rnd = random.nextInt(1000000);
        return String.format("%06d", rnd);
    }
}
