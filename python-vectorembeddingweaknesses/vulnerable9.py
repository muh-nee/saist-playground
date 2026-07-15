from pinecone import Pinecone
from langchain_openai import OpenAIEmbeddings
from flask import Flask, request, jsonify
import uuid

app = Flask(__name__)
pc = Pinecone()
index = pc.Index("knowledge-base")
embeddings = OpenAIEmbeddings()

@app.route("/upsert", methods=["POST"])
def upsert_doc():
    data = request.get_json()
    text = data["text"]
    vector = embeddings.embed_query(text)
    index.upsert(vectors=[{"id": str(uuid.uuid4()), "values": vector, "metadata": {"text": text}}])
    return jsonify({"status": "ok"})
