from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI()
embeddings = OpenAIEmbeddings()
vectorstore = Chroma(collection_name="catalog", embedding_function=embeddings)

ALLOWED_CATEGORIES = {"electronics", "clothing", "furniture", "books", "sports"}

class IngestRequest(BaseModel):
    category: str

@app.post("/ingest")
def ingest(req: IngestRequest):
    if req.category not in ALLOWED_CATEGORIES:
        raise HTTPException(status_code=400, detail="Invalid category")
    vectorstore.add_texts([req.category])
    return {"status": "ok"}
