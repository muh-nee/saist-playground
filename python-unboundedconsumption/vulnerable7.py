from openai import OpenAI
import requests

client = OpenAI()

def call_local_llm(prompt: str) -> str:
    response = requests.post(
        "http://localhost:11434/v1/chat/completions",
        json={"model": "llama3", "messages": [{"role": "user", "content": prompt}]},
    )
    return response.json()["choices"][0]["message"]["content"]
