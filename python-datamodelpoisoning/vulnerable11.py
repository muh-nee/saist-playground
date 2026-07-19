from flask import Flask, request, jsonify
from peft import PeftModel
from transformers import AutoModelForCausalLM

app = Flask(__name__)
base_model = AutoModelForCausalLM.from_pretrained("gpt2")

@app.route("/load-adapter", methods=["POST"])
def load_adapter():
    adapter_path = request.form.get("adapter_path")
    model = PeftModel.from_pretrained(base_model, adapter_path)
    return jsonify({"loaded": True})
