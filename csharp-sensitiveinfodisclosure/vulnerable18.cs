public class PasswordHelper
{
    public string GetDbPassword()
    {
        return Environment.GetEnvironmentVariable("DB_PASSWORD");
    }
}
