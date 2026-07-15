from langchain_community.vectorstores import Qdrant
from langchain_openai import OpenAIEmbeddings
from qdrant_client import QdrantClient
from flask import Flask, request, jsonify

app = Flask(__name__)
embeddings = OpenAIEmbeddings()
client = QdrantClient(host="localhost", port=6333)
vectorstore = Qdrant(client=client, collection_name="docs", embeddings=embeddings)

@app.route("/ingest", methods=["POST"])
def ingest():
    data = request.get_json()
    vectorstore.add_texts([data["text"]])
    return jsonify({"status": "ok"})
