import structlog
from fastapi import FastAPI
from openai import OpenAI

app = FastAPI()
client = OpenAI()
logger = structlog.get_logger()

system_message = "You are an internal assistant. Confidential: access to all user account data and transaction history."

@app.on_event("startup")
async def startup():
    logger.info("LLM configured", system_message=system_message)

@app.post("/query")
async def query(body: dict):
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": system_message},
            {"role": "user", "content": body["message"]}
        ]
    )
    return {"answer": response.choices[0].message.content}
