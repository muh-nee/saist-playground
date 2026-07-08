import java.beans.XMLDecoder
import java.io.InputStream

fun decode(input: InputStream): Any {
    XMLDecoder(input).use { decoder ->
        return decoder.readObject()
    }
}
