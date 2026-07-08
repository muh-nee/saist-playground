import java.io.InputStream
import java.io.ObjectInputFilter
import java.io.ObjectInputStream

fun load(input: InputStream): Any {
    val ois = ObjectInputStream(input)
    ois.setObjectInputFilter(
        ObjectInputFilter { info ->
            val cls = info.serialClass() ?: return@ObjectInputFilter ObjectInputFilter.Status.UNDECIDED
            if (cls.name.startsWith("com.example.model.")) ObjectInputFilter.Status.ALLOWED
            else ObjectInputFilter.Status.REJECTED
        }
    )
    return ois.readObject()
}
