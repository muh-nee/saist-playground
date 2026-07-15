import chromadb
from flask import Flask, request, jsonify
from flask_login import login_required, current_user

app = Flask(__name__)
chroma_client = chromadb.Client()

@app.route("/ingest", methods=["POST"])
@login_required
def ingest_document():
    collection_name = f"user_{current_user.id}"
    collection = chroma_client.get_or_create_collection(collection_name)
    data = request.get_json()
    collection.add(documents=[data["content"]], ids=[data["id"]])
    return jsonify({"status": "added"})
