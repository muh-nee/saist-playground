// Internal constants only — no user input in bitwise operations
const WIDTH = 1920;
const HEIGHT = 1080;

// Bitwise on compile-time constants is safe
const area = (WIDTH * HEIGHT) | 0; // both are known constants, no overflow
