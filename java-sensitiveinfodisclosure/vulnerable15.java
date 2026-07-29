public class PasswordHelper {
    public String getDbPassword() {
        return System.getenv("DB_PASSWORD");
    }
}
