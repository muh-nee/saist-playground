from flask import Flask, request
import chromadb
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()
chroma = chromadb.Client()
collection = chroma.get_or_create_collection("session_memory")


@app.post("/summarize")
def summarize_and_store():
    user_query = request.json["query"]
    session_id = request.json["session_id"]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": user_query}]
    )
    llm_output = response.choices[0].message.content
    collection.add(documents=[llm_output], ids=[session_id])
    return {"stored": True}
