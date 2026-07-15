from llama_index.core import VectorStoreIndex, Document
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
index = VectorStoreIndex(nodes=[])

class DocRequest(BaseModel):
    content: str
    title: str

@app.post("/index")
def index_document(req: DocRequest):
    doc = Document(text=req.content, metadata={"title": req.title})
    index.insert(doc)
    return {"status": "indexed"}
