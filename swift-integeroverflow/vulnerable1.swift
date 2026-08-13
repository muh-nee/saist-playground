import Foundation

func allocationSize(count: UInt, elementSize: UInt) -> UInt {
    count &* elementSize
}
