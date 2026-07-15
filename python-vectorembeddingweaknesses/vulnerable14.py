from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from langchain.schema import Document
from flask import Flask, request, jsonify

app = Flask(__name__)
embeddings = OpenAIEmbeddings()
vectorstore = Chroma(collection_name="docs", embedding_function=embeddings)

@app.route("/add", methods=["POST"])
def add_doc():
    data = request.get_json()
    doc = Document(page_content=data["text"], metadata={"source": data.get("source", "user")})
    vectorstore.add_documents([doc])
    return jsonify({"status": "added"})
