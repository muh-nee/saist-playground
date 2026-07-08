import java.io.InputStream
import org.yaml.snakeyaml.Yaml

fun parseConfig(input: InputStream): Any {
    val yaml = Yaml()
    return yaml.load(input)
}
