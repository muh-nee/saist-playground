package zipslip

import (
	"archive/tar"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
)

// extractTarSafe extracts a tar archive to destDir with canonical path validation.
// Safe: resolves absolute path and verifies it stays within destDir.
func extractTarSafe(archivePath, destDir string) error {
	absDestDir, err := filepath.Abs(destDir)
	if err != nil {
		return err
	}

	f, err := os.Open(archivePath)
	if err != nil {
		return err
	}
	defer f.Close()

	tr := tar.NewReader(f)
	for {
		hdr, err := tr.Next()
		if err == io.EOF {
			break
		}
		if err != nil {
			return err
		}

		// Resolve absolute path and check containment before any file operation
		absPath, err := filepath.Abs(filepath.Join(destDir, hdr.Name))
		if err != nil {
			return err
		}
		if !strings.HasPrefix(absPath, absDestDir+string(os.PathSeparator)) {
			return fmt.Errorf("illegal entry path: %s", hdr.Name)
		}

		switch hdr.Typeflag {
		case tar.TypeDir:
			os.MkdirAll(absPath, 0755)
		case tar.TypeReg:
			os.MkdirAll(filepath.Dir(absPath), 0755)
			out, err := os.OpenFile(absPath, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, hdr.FileInfo().Mode())
			if err != nil {
				return err
			}
			io.Copy(out, tr)
			out.Close()
		}
	}
	return nil
}
