import chromadb
from flask import Flask, request, jsonify

app = Flask(__name__)
chroma_client = chromadb.Client()
collection = chroma_client.get_or_create_collection("support_kb")

@app.route("/update", methods=["POST"])
def update_document():
    data = request.get_json()
    collection.upsert(
        documents=[data["content"]],
        ids=[data["id"]],
    )
    return jsonify({"status": "updated"})
