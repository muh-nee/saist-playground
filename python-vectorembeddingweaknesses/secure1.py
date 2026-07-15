import chromadb
from flask import Flask, request, jsonify
from flask_login import login_required, current_user

app = Flask(__name__)
chroma_client = chromadb.Client()
collection = chroma_client.get_or_create_collection("knowledge_base")

@app.route("/ingest", methods=["POST"])
@login_required
def ingest_document():
    if current_user.role != "admin":
        return jsonify({"error": "forbidden"}), 403
    data = request.get_json()
    collection.add(documents=[data["content"]], ids=[data["id"]])
    return jsonify({"status": "added"})

