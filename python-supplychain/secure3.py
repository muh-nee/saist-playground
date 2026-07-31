from transformers import AutoModel

def load_model():
    model = AutoModel.from_pretrained(
        "openai-community/gpt2",
        revision="v1.0",
    )
    return model
