from transformers import AutoTokenizer

def load_tokenizer():
    tokenizer = AutoTokenizer.from_pretrained("openai-community/gpt2")
    return tokenizer
