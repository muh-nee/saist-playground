from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import re

app = FastAPI()
embeddings = OpenAIEmbeddings()
vectorstore = Chroma(collection_name="docs", embedding_function=embeddings)

INJECTION_PATTERNS = re.compile(
    r'(ignore previous|you are now|system prompt|forget your|new instructions)',
    re.IGNORECASE,
)

class IngestRequest(BaseModel):
    text: str

@app.post("/ingest")
def ingest(req: IngestRequest):
    if INJECTION_PATTERNS.search(req.text):
        raise HTTPException(status_code=400, detail="Content rejected")
    vectorstore.add_texts([req.text])
    return {"status": "ok"}
