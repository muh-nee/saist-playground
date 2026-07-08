import java.util.Optional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class Account(var balance: Double, val owner: String)
data class BalanceUpdate(val newBalance: Double)
interface AccountRepository { fun findById(id: Long): Optional<Account>; fun save(a: Account) }

@RestController
class AccountController(private val accountRepository: AccountRepository) {
    @PutMapping("/accounts/{accountId}/balance")
    fun updateBalance(@PathVariable accountId: Long, @RequestBody update: BalanceUpdate) {
        val account = accountRepository.findById(accountId).get()
        account.balance = update.newBalance
        accountRepository.save(account)
    }
}
