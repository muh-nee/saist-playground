func calculate(_ operation: String, left: Int, right: Int) -> Int? {
    switch operation {
    case "add": return left + right
    case "subtract": return left - right
    default: return nil
    }
}
