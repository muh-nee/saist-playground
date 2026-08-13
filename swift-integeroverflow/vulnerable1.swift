import Foundation

func allocationSize(count: UInt, elementSize: UInt) -> UInt {
    count &* elementSize // VULNERABLE: wrapping arithmetic can under-allocate a security-sensitive buffer
}
