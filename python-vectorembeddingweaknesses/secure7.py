from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
embeddings = OpenAIEmbeddings()
vectorstore = Chroma(collection_name="docs", embedding_function=embeddings)

class QueryRequest(BaseModel):
    query: str

@app.post("/search")
def search(req: QueryRequest):
    results = vectorstore.similarity_search(req.query, k=5)
    return {"results": [r.page_content for r in results]}
