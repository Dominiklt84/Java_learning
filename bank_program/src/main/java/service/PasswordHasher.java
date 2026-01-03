package service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
public final class PasswordHasher {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16;          // bytes
    private static final int HASH_LENGTH = 32;          // bytes
    private static final int ITERATIONS = 120_000;

    private final SecureRandom random = new SecureRandom();

    public String hash(char[] password) {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, ITERATIONS, HASH_LENGTH);

        return String.format(
                "pbkdf2$%d$%s$%s",
                ITERATIONS,
                Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hash)
        );
    }

    public boolean verify(char[] password, String stored) {
        String[] parts = stored.split("\\$");
        if (parts.length != 4 || !parts[0].equals("pbkdf2")) {
            throw new IllegalArgumentException("Invalid hash format");
        }

        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[3]);

        byte[] actualHash = pbkdf2(password, salt, iterations, expectedHash.length);

        return slowEquals(expectedHash, actualHash);
    }

    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int length) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("PBKDF2 failure", e);
        }
    }

    private boolean slowEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;

        int diff = 0;
        for (int i = 0; i < a.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
