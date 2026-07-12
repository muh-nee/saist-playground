import httpx
from openai import OpenAI

client = OpenAI()

async def call_ollama(prompt: str) -> str:
    async with httpx.AsyncClient() as http_client:
        response = await http_client.post(
            "http://localhost:11434/v1/chat/completions",
            json={
                "model": "mistral",
                "messages": [{"role": "user", "content": prompt}],
            },
        )
        return response.json()["choices"][0]["message"]["content"]

def call_openai(prompt: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": prompt}],
        max_tokens=512,
    )
    return response.choices[0].message.content
