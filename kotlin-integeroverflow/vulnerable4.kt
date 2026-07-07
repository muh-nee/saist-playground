import android.content.Intent
import android.os.Bundle

class DataActivity : android.app.Activity() {
    private val data = ByteArray(1024 * 1024)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val base = intent.getIntExtra("base", 0)
        val index = intent.getIntExtra("index", 0)
        val result = data[base + index]  // base + index may overflow to a negative or out-of-bounds value
    }
}
