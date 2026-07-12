import requests
from openai import OpenAI

client = OpenAI()

def call_local_llm(prompt: str) -> str:
    response = requests.post(
        "http://localhost:11434/v1/chat/completions",
        json={"model": "llama3", "messages": [{"role": "user", "content": prompt}]},
        timeout=30,
    )
    return response.json()["choices"][0]["message"]["content"]

def call_openai(prompt: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": prompt}],
        max_tokens=512,
    )
    return response.choices[0].message.content
