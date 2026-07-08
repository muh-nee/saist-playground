import zipfile
import os


def extract_archive_safe(zip_path: str, dest_dir: str) -> None:
    """
    Safe: Validates each member path using os.path.realpath() before extraction.
    Raises ValueError if any entry would escape the destination directory.
    """
    real_dest = os.path.realpath(dest_dir)

    with zipfile.ZipFile(zip_path, 'r') as zf:
        for member in zf.infolist():
            # Safe: realpath resolves all ".." and symlinks; startswith verifies containment
            target = os.path.realpath(os.path.join(dest_dir, member.filename))
            if not target.startswith(real_dest + os.sep):
                raise ValueError(f"Illegal entry path: {member.filename}")

            os.makedirs(os.path.dirname(target), exist_ok=True)
            with zf.open(member) as src, open(target, 'wb') as dst:
                dst.write(src.read())
