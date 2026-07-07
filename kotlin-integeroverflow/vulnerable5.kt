import org.springframework.web.bind.annotation.*

data class AllocRequest(val count: Int, val size: Int)

@RestController
class AllocController {
    @PostMapping("/data")
    fun processData(@RequestBody req: AllocRequest): ByteArray {
        return ByteArray(req.count * req.size)  // overflow if product exceeds Int.MAX_VALUE
    }
}
