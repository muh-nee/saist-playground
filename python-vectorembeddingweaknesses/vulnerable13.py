from langchain_community.vectorstores import Pinecone as PineconeVectorStore
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI
from pydantic import BaseModel
import pinecone

app = FastAPI()
pc = pinecone.Pinecone()
index = pc.Index("knowledge-base")
embeddings = OpenAIEmbeddings()
vectorstore = PineconeVectorStore(index, embeddings, "text")

class IngestRequest(BaseModel):
    text: str

@app.post("/ingest")
def ingest(req: IngestRequest):
    vectorstore.add_texts([req.text])
    return {"status": "ok"}
