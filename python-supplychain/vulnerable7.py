from transformers import pipeline

def create_pipeline():
    pipe = pipeline("text-generation", model="openai-community/gpt2")
    return pipe
