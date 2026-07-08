import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

interface PostRepository { fun deleteById(id: Long) }

@RestController
class PostController(private val postRepository: PostRepository) {
    @DeleteMapping("/posts/{postId}")
    fun deletePost(@PathVariable postId: Long) {
        postRepository.deleteById(postId)
    }
}
