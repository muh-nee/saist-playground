// No user-controlled values in arithmetic — internal constants only
public class ReportGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;

    public int getArea() {
        return WIDTH * HEIGHT; // no overflow risk; both are compile-time constants
    }
}
