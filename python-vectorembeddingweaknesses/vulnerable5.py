from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings
from langchain.schema import Document
from flask import Flask, request, jsonify

app = Flask(__name__)
embeddings = OpenAIEmbeddings()
vectorstore = FAISS.from_texts(["init"], embeddings)

@app.route("/build-index", methods=["POST"])
def build_index():
    global vectorstore
    data = request.get_json()
    docs = [Document(page_content=entry["text"]) for entry in data["documents"]]
    vectorstore = FAISS.from_documents(docs, embeddings)
    return jsonify({"status": "index rebuilt"})
