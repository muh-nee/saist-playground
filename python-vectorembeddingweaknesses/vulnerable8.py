from qdrant_client import QdrantClient
from qdrant_client.models import PointStruct
from langchain_openai import OpenAIEmbeddings
from flask import Flask, request, jsonify
import uuid

app = Flask(__name__)
client = QdrantClient(host="localhost", port=6333)
embeddings = OpenAIEmbeddings()

@app.route("/store", methods=["POST"])
def store_document():
    data = request.get_json()
    user_text = data["text"]
    vector = embeddings.embed_query(user_text)
    client.upsert(
        collection_name="documents",
        points=[PointStruct(id=str(uuid.uuid4()), vector=vector, payload={"text": user_text})],
    )
    return jsonify({"status": "stored"})
