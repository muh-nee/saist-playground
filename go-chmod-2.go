// this is a change
package main

import "os"

func main() {
	// Overly permissive file mode — world read/write/execute.
	os.Chmod("/tmp/myfile", 0o777)
}
