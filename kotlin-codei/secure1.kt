fun compute(action: String, a: Int, b: Int): Int =
    when (action) {
        "add" -> a + b
        "sub" -> a - b
        "mul" -> a * b
        else -> throw IllegalArgumentException("unknown action")
    }
