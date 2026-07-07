import org.springframework.web.bind.annotation.*

private const val BLOCK_SIZE = 4096

@RestController
class BufferController {
    @GetMapping("/buffer")
    fun allocate(@RequestParam count: Int): ByteArray {
        if (count <= 0 || count > Int.MAX_VALUE / BLOCK_SIZE)
            throw IllegalArgumentException("count out of range")
        return ByteArray(count * BLOCK_SIZE)  // safe after bounds check
    }
}
