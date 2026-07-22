from openai import OpenAI
from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel

app = FastAPI()
client = OpenAI()

class Query(BaseModel):
    question: str

@app.post("/fact-check")
async def fact_check(body: Query):
    response = await client.chat.completions.create(
        model="gpt-4o",
        max_tokens=500,
        messages=[{"role": "user", "content": body.question}],
    )
    answer = response.choices[0].message.content
    return JSONResponse({"fact": answer})
