import javax.persistence.EntityManager
import org.springframework.jdbc.core.JdbcTemplate

class UserRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val em: EntityManager,
) {
    fun search(name: String): List<Map<String, Any>> =
        jdbcTemplate.queryForList("SELECT * FROM products WHERE name = ?", name)

    fun findOrders(customer: String): List<*> =
        em.createQuery("SELECT o FROM Order o WHERE o.customer = :c")
            .setParameter("c", customer)
            .resultList
}
