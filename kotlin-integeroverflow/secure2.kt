import org.springframework.web.bind.annotation.*

private const val BLOCK_SIZE = 4096

@RestController
class BufferController {
    @GetMapping("/buffer")
    fun allocate(@RequestParam count: Int): ByteArray {
        val size = Math.multiplyExact(count.toLong(), BLOCK_SIZE.toLong()).toInt()
        return ByteArray(size)  // ArithmeticException on overflow
    }
}
