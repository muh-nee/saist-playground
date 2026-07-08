package zipslip

import (
	"archive/zip"
	"errors"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
)

// extractZipSafe extracts a zip archive to destDir with canonical path validation.
// Safe: resolves absolute path and verifies it stays within destDir.
func extractZipSafe(archivePath, destDir string) error {
	absDestDir, err := filepath.Abs(destDir)
	if err != nil {
		return err
	}

	r, err := zip.OpenReader(archivePath)
	if err != nil {
		return err
	}
	defer r.Close()

	for _, f := range r.File {
		// Resolve absolute path and check containment before any file operation
		absPath, err := filepath.Abs(filepath.Join(destDir, f.Name))
		if err != nil {
			return err
		}
		if !strings.HasPrefix(absPath, absDestDir+string(os.PathSeparator)) {
			return fmt.Errorf("illegal entry path: %s", f.Name)
		}

		if f.FileInfo().IsDir() {
			os.MkdirAll(absPath, f.Mode())
			continue
		}

		if err := os.MkdirAll(filepath.Dir(absPath), 0755); err != nil {
			return err
		}

		dst, err := os.Create(absPath)
		if err != nil {
			return err
		}

		rc, err := f.Open()
		if err != nil {
			dst.Close()
			return err
		}

		if _, err := io.Copy(dst, rc); err != nil {
			rc.Close()
			dst.Close()
			return errors.New("copy failed")
		}
		rc.Close()
		dst.Close()
	}
	return nil
}
