from huggingface_hub import hf_hub_download

def get_model_path():
    path = hf_hub_download(
        repo_id="bert-base-uncased",
        filename="pytorch_model.bin",
        revision="4b6f8b5c",
    )
    return path
