import org.springframework.web.bind.annotation.*;

@RestController
public class SafeBufferController {
    private static final int BLOCK_SIZE = 512;

    @GetMapping("/buffer")
    public byte[] allocate(@RequestParam int count) {
        return new byte[Math.multiplyExact(count, BLOCK_SIZE)]; // throws ArithmeticException on overflow
    }
}
