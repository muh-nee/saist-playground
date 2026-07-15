import chromadb
from flask import Flask, request, jsonify

app = Flask(__name__)
chroma_client = chromadb.Client()
collection = chroma_client.get_or_create_collection("knowledge_base")

@app.route("/ingest", methods=["POST"])
def ingest_document():
    data = request.get_json()
    user_text = data["content"]
    doc_id = data["id"]
    collection.add(
        documents=[user_text],
        ids=[doc_id],
    )
    return jsonify({"status": "added"})
