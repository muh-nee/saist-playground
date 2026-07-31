from huggingface_hub import snapshot_download

def download_model():
    local_dir = snapshot_download(
        repo_id="facebook/opt-1.3b",
        revision="27dcfa74",
    )
    return local_dir
