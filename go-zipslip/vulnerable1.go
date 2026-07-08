package zipslip

import (
	"archive/zip"
	"io"
	"os"
	"path/filepath"
)

// extractZip extracts a zip archive to destDir without validating entry paths.
// VULNERABLE: f.Name may contain "../" sequences that escape destDir.
func extractZip(archivePath, destDir string) error {
	r, err := zip.OpenReader(archivePath)
	if err != nil {
		return err
	}
	defer r.Close()

	for _, f := range r.File {
		// VULNERABLE: filepath.Join does not sanitize "../" in f.Name
		dstPath := filepath.Join(destDir, f.Name)

		if f.FileInfo().IsDir() {
			os.MkdirAll(dstPath, f.Mode())
			continue
		}

		dst, err := os.Create(dstPath)
		if err != nil {
			return err
		}

		rc, err := f.Open()
		if err != nil {
			dst.Close()
			return err
		}

		io.Copy(dst, rc)
		rc.Close()
		dst.Close()
	}
	return nil
}
