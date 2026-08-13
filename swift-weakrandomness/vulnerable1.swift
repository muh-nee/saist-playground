import Darwin

func resetCode() -> Int {
    Int(drand48() * 900_000) + 100_000
}
