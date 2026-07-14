from fastapi import FastAPI
from openai import OpenAI

app = FastAPI()
client = OpenAI()

system_message = "Internal assistant. Has access to all customer records and pricing. Do not disclose."


@app.post("/chat")
async def chat(body: dict):
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": system_message},
            {"role": "user", "content": body["message"]},
        ],
    )
    return {"answer": response.choices[0].message.content}


@app.get("/status")
def status():
    return {"ok": True, "system": system_message}
