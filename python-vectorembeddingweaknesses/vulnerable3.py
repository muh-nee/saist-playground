from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
embeddings = OpenAIEmbeddings()
vectorstore = Chroma(collection_name="docs", embedding_function=embeddings)

class IngestRequest(BaseModel):
    text: str

@app.post("/ingest")
def ingest(req: IngestRequest):
    vectorstore.add_texts([req.text])
    return {"status": "ok"}
