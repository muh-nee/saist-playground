from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()
embeddings = OpenAIEmbeddings()
vectorstore = FAISS.from_texts(["init"], embeddings)

class BuildRequest(BaseModel):
    texts: List[str]

@app.post("/build")
def build_index(req: BuildRequest):
    global vectorstore
    vectorstore = FAISS.from_texts(req.texts, embeddings)
    return {"status": "built"}
