from flask import Flask, jsonify
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

system_prompt = "You are an internal assistant with access to financial data."

@app.route("/debug/prompt-info")
def prompt_info():
    redacted = system_prompt[:20] + "..." if len(system_prompt) > 20 else "[redacted]"
    return jsonify({"prompt_preview": redacted, "length": len(system_prompt)})
