import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator

fun fromJson(json: String): Any {
    val mapper = ObjectMapper()
    mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance)
    return mapper.readValue(json, Any::class.java)
}
