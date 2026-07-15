from langchain_community.vectorstores import Qdrant
from langchain_openai import OpenAIEmbeddings
from flask import Flask, request, jsonify

app = Flask(__name__)
embeddings = OpenAIEmbeddings()
vectorstore = Qdrant.from_texts(["init"], embeddings, location=":memory:", collection_name="docs")

@app.route("/ingest", methods=["POST"])
def ingest():
    data = request.get_json()
    vectorstore.add_texts([data["text"]])
    return jsonify({"status": "ok"})
