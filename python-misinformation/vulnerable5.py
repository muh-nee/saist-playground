from llama_index.core import VectorStoreIndex, SimpleDirectoryReader
from llama_index.llms.openai import OpenAI
from flask import Flask, request, jsonify

app = Flask(__name__)
documents = SimpleDirectoryReader("data").load_data()
index = VectorStoreIndex.from_documents(documents)
query_engine = index.as_query_engine(llm=OpenAI(model="gpt-4o", max_tokens=500))

@app.route("/search", methods=["POST"])
def search():
    question = request.json["question"]
    response = query_engine.query(question)
    return jsonify({"answer": response.response})
