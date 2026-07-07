import org.springframework.web.bind.annotation.*;

@RestController
public class BufferController {
    private static final int BLOCK_SIZE = 512;

    @GetMapping("/buffer")
    public byte[] allocate(@RequestParam int count) {
        return new byte[count * BLOCK_SIZE]; // count * BLOCK_SIZE may overflow int
    }
}
