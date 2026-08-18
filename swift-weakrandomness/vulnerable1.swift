import Darwin

func resetCode() -> Int {
    Int(random() % 900_000) + 100_000
}
