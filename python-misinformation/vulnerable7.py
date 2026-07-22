from openai import OpenAI
from fastapi import FastAPI
from fastapi.responses import StreamingResponse
from pydantic import BaseModel

app = FastAPI()
client = OpenAI()

class Query(BaseModel):
    question: str

@app.post("/stream")
def stream_answer(body: Query):
    def generate():
        stream = client.chat.completions.create(
            model="gpt-4o",
            max_tokens=500,
            messages=[{"role": "user", "content": body.question}],
            stream=True,
        )
        for chunk in stream:
            delta = chunk.choices[0].delta.content
            if delta:
                yield delta

    return StreamingResponse(generate(), media_type="text/plain")
