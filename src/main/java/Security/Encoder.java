package Security;

import org.mindrot.jbcrypt.BCrypt;

public class Encoder {
    public Encoder() {
    }

    public static String encode(String raw) {
        int logRounds = 12;
        String salt = BCrypt.gensalt(logRounds);
        return BCrypt.hashpw(raw, salt);
    }

    public static boolean matches(String raw, String storedHash) {
        return BCrypt.checkpw(raw, storedHash);
    }
}
