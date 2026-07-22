from langchain_openai import ChatOpenAI
from langchain.chains import RetrievalQA
from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings
from flask import Flask, request, jsonify

app = Flask(__name__)
llm = ChatOpenAI(model="gpt-4o", max_tokens=500)
embeddings = OpenAIEmbeddings()
vectorstore = FAISS.load_local("index", embeddings, allow_dangerous_deserialization=True)
qa_chain = RetrievalQA.from_chain_type(llm=llm, retriever=vectorstore.as_retriever())

@app.route("/query", methods=["POST"])
def query():
    question = request.json["question"]
    result = qa_chain.invoke({"query": question})
    return jsonify({"answer": result["result"]})
