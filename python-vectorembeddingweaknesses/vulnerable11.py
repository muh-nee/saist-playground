import chromadb
from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()
chroma_client = chromadb.Client()
collection = chroma_client.get_or_create_collection("feedback")

class FeedbackBatch(BaseModel):
    entries: List[str]

@app.post("/feedback/batch")
def add_feedback_batch(batch: FeedbackBatch):
    ids = [str(i) for i in range(len(batch.entries))]
    collection.add(
        documents=batch.entries,
        ids=ids,
    )
    return {"added": len(batch.entries)}
