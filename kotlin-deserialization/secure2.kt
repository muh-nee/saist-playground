import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.SafeConstructor

data class UserDto(val name: String = "", val age: Int = 0)

fun parseUser(json: String): UserDto =
    ObjectMapper().readValue(json, UserDto::class.java)

fun parseConfig(input: InputStream): Any =
    Yaml(SafeConstructor(LoaderOptions())).load(input)
