package zipslip

import (
	"archive/tar"
	"io"
	"os"
	"path/filepath"
)

// extractTar extracts a tar archive to destDir without validating entry paths.
// VULNERABLE: hdr.Name may contain "../" sequences that escape destDir.
func extractTar(archivePath, destDir string) error {
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

		// VULNERABLE: filepath.Join does not sanitize "../" in hdr.Name
		dstPath := filepath.Join(destDir, hdr.Name)

		switch hdr.Typeflag {
		case tar.TypeDir:
			os.MkdirAll(dstPath, 0755)
		case tar.TypeReg:
			out, err := os.OpenFile(dstPath, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, hdr.FileInfo().Mode())
			if err != nil {
				return err
			}
			io.Copy(out, tr)
			out.Close()
		}
	}
	return nil
}
