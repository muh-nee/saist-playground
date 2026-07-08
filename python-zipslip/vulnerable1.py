import zipfile


def extract_archive(zip_path: str, dest_dir: str) -> None:
    """
    VULNERABLE: Uses extractall() without validating member paths.
    A malicious archive with "../" in member names will escape dest_dir.
    """
    with zipfile.ZipFile(zip_path, 'r') as zf:
        # VULNERABLE: extractall() uses member filenames directly
        zf.extractall(dest_dir)
