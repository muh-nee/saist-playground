from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from flask import Flask, request, jsonify

app = Flask(__name__)
llm = ChatOpenAI(model="gpt-4o", max_tokens=500)

@app.route("/research", methods=["POST"])
def research():
    topic = request.json["topic"]
    prompt = ChatPromptTemplate.from_messages([
        ("user", "Explain {topic} in detail."),
    ])
    chain = prompt | llm
    result = chain.invoke({"topic": topic})
    return jsonify({"content": result.content})
