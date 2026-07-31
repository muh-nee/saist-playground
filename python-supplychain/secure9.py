from huggingface_hub import hf_hub_download

def get_model_path():
    path = hf_hub_download(
        repo_id="openai-community/gpt2",
        filename="pytorch_model.bin",
        revision="11c5a3d5811f50298f278a704980280950aedb10",
    )
    return path
