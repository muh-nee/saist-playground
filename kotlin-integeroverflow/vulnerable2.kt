import org.springframework.web.bind.annotation.*

private const val BLOCK_SIZE = 4096

@RestController
class BufferController {
    @GetMapping("/buffer")
    fun allocate(@RequestParam count: Int): ByteArray {
        return ByteArray(count * BLOCK_SIZE)  // count * BLOCK_SIZE may overflow Int
    }
}
