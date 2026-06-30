import javax.persistence.EntityManager

fun findOrders(em: EntityManager, customer: String): List<*> {
    val jpql = "SELECT o FROM Order o WHERE o.customer = '$customer'"
    return em.createQuery(jpql).resultList
}
