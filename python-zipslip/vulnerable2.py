import tarfile
import os


def extract_tar(tar_path: str, dest_dir: str) -> None:
    """
    VULNERABLE: Manually extracts tar entries without path validation.
    tarinfo.name may contain "../" sequences that escape dest_dir.
    """
    with tarfile.open(tar_path, 'r:*') as tf:
        for tarinfo in tf.getmembers():
            # VULNERABLE: os.path.join does not sanitize "../" in tarinfo.name
            target = os.path.join(dest_dir, tarinfo.name)
            if tarinfo.isdir():
                os.makedirs(target, exist_ok=True)
            else:
                os.makedirs(os.path.dirname(target), exist_ok=True)
                with tf.extractfile(tarinfo) as src, open(target, 'wb') as dst:
                    dst.write(src.read())
