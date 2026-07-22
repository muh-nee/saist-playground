import anthropic
from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel

app = FastAPI()
client = anthropic.Anthropic()

class Query(BaseModel):
    question: str

@app.post("/answer")
def answer(body: Query):
    message = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=500,
        messages=[{"role": "user", "content": body.question}],
    )
    text = message.content[0].text
    return JSONResponse({"answer": text})
