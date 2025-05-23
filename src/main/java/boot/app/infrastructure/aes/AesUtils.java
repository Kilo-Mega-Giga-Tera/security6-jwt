package boot.app.infrastructure.aes;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesUtils {

  private static String password;

  AesUtils(@Value("${jwt.password}") String password) {
    // 16, 20, 24 ... byte only
    AesUtils.password = password + password + password + password;
  }

  public static String encrypt(String data) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");

    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

    byte[] encryptedText = cipher.doFinal(data.getBytes());

    return Base64.getEncoder().encodeToString(encryptedText);
  }

  public static String decrypt(String data) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");

    cipher.init(Cipher.DECRYPT_MODE, secretKey);

    byte[] decoded = Base64.getDecoder().decode(data);
    byte[] decryptedText = cipher.doFinal(decoded);

    return new String(decryptedText);
  }
}
