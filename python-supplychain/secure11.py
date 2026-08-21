import subprocess
import openai
from flask import Flask, request, jsonify

app = Flask(__name__)
client = openai.OpenAI()
APPROVED_PACKAGES = frozenset({"numpy", "pandas", "scikit-learn", "torch", "transformers", "onnxruntime"})

@app.post("/install-package")
def install_package():
    user_request = request.json["task"]
    response = client.chat.completions.create(
        model="gpt-4",
        messages=[{"role": "user", "content": f"What pip package for: {user_request}? Reply with only the package name."}],
    )
    package_name = response.choices[0].message.content.strip()
    if package_name not in APPROVED_PACKAGES:
        return jsonify({"error": "package not approved"}), 400
    subprocess.run(["pip", "install", package_name], check=True)
    return jsonify({"installed": package_name})
