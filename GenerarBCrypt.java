import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        String encoded = encoder.encode(password);
        System.out.println("Contraseña: " + password);
        System.out.println("BCrypt: " + encoded);
        System.out.println("Verificación: " + encoder.matches(password, encoded));
    }
}
