import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val jdbcTemplate: JdbcTemplate) {
    @GetMapping("/products")
    fun search(@RequestParam q: String): List<Map<String, Any>> {
        val sql = "SELECT * FROM products WHERE name LIKE '%$q%'"
        return jdbcTemplate.queryForList(sql)
    }
}
