from transformers import AutoModel

def load_model():
    model = AutoModel.from_pretrained(
        "openai-community/gpt2",
        revision="607a30d783dfa663caf39e06633721c8d4cfcd7e",
    )
    return model
