package co.com.tokenization.tokenization_api.infrastructure.config;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtils {

    private static final String ALGO = "AES/GCM/NoPadding";
    private final byte[] key;

    public CryptoUtils(String base64Key) {
        this.key = Base64.getDecoder().decode(base64Key);
    }

    public String encrypt(String plain) {
        try {
            byte[] iv = new byte[12];
            java.security.SecureRandom.getInstanceStrong().nextBytes(iv);
            Cipher cipher = Cipher.getInstance(ALGO);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
            byte[] encrypted = cipher.doFinal(plain.getBytes());
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cipherText) {
        try {
            byte[] data = Base64.getDecoder().decode(cipherText);
            byte[] iv = java.util.Arrays.copyOfRange(data, 0, 12);
            byte[] enc = java.util.Arrays.copyOfRange(data, 12, data.length);
            Cipher cipher = Cipher.getInstance(ALGO);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
            return new String(cipher.doFinal(enc));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
