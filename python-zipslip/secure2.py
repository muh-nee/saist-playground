import tarfile
import os


def extract_tar_safe(tar_path: str, dest_dir: str) -> None:
    """
    Safe: Validates all tar member paths before extraction.
    Raises ValueError if any entry would escape the destination directory.
    """
    real_dest = os.path.realpath(dest_dir)

    with tarfile.open(tar_path, 'r:*') as tf:
        # Validate all members first before extracting any
        for member in tf.getmembers():
            target = os.path.realpath(os.path.join(dest_dir, member.name))
            if not target.startswith(real_dest + os.sep):
                raise ValueError(f"Illegal entry: {member.name}")

        # Safe to extract only after all paths have been validated
        tf.extractall(dest_dir)
