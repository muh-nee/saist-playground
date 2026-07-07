import android.content.Intent
import android.util.Log

class MainActivity {
    private val TAG = "MainActivity"

    fun handleIntent(intent: Intent) {
        val input = intent.getStringExtra("user_input")
        Log.d(TAG, "Received input: $input") // Android Log with string template; input injected into message
    }
}
