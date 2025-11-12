package snacknroll.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
  public static String hash(String plain) {
	  return BCrypt.hashpw(plain, BCrypt.gensalt(10));
  }
  public static boolean verify(String plain, String hash) {
    return hash != null && BCrypt.checkpw(plain, hash);
  }
}