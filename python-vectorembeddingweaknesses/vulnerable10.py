from langchain_community.vectorstores import Weaviate
from langchain_openai import OpenAIEmbeddings
from langchain.schema import Document
from flask import Flask, request, jsonify
import weaviate

app = Flask(__name__)
weaviate_client = weaviate.Client("http://localhost:8080")
embeddings = OpenAIEmbeddings()
vectorstore = Weaviate(weaviate_client, "Article", "content", embedding=embeddings)

@app.route("/articles", methods=["POST"])
def add_article():
    body = request.get_json()
    doc = Document(page_content=body["content"], metadata={"author": body["author"]})
    vectorstore.add_documents([doc])
    return jsonify({"status": "added"})
