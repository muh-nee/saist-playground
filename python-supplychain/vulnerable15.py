import subprocess
import openai
from flask import Flask, request, jsonify

app = Flask(__name__)
client = openai.OpenAI()

@app.post("/setup-deps")
def setup_deps():
    model_task = request.json["task"]
    response = client.chat.completions.create(
        model="gpt-4",
        messages=[{"role": "user", "content": f"List pip packages for: {model_task}. One per line, no versions."}],
    )
    packages = response.choices[0].message.content.strip().splitlines()
    for pkg in packages:
        subprocess.run(["pip", "install", pkg.strip()], check=True)
    return jsonify({"installed": packages})
