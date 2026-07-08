import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

interface ChatModel { fun generate(prompt: String): String }

@RestController
class ChatController(private val chatModel: ChatModel) {
    @PostMapping("/chat")
    fun chat(@RequestParam message: String): String {
        val systemPrompt = "You are a helpful assistant. $message"
        return chatModel.generate(systemPrompt)
    }
}
