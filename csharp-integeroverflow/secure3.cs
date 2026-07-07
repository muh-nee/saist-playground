// Internal constants only — no user input in arithmetic
public class ReportConfig
{
    private const int Width = 1920;
    private const int Height = 1080;
    private const int BytesPerPixel = 4;

    public int TotalBytes => checked(Width * Height * BytesPerPixel); // compile-time constants
}
