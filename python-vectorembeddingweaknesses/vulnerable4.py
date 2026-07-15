from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings
from langchain.schema import Document
from flask import Flask, request, jsonify

app = Flask(__name__)
embeddings = OpenAIEmbeddings()
vectorstore = FAISS.from_texts(["init"], embeddings)

@app.route("/add", methods=["POST"])
def add_doc():
    content = request.form.get("content")
    source = request.form.get("source")
    doc = Document(page_content=content, metadata={"source": source})
    vectorstore.add_documents([doc])
    return jsonify({"status": "added"})
