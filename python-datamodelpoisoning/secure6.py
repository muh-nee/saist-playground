from flask import Flask, request, jsonify
from peft import PeftModel
from transformers import AutoModelForCausalLM

app = Flask(__name__)
base_model = AutoModelForCausalLM.from_pretrained("gpt2")
APPROVED_ADAPTERS = {"lora-v1", "lora-v2"}

@app.route("/load-adapter", methods=["POST"])
def load_adapter():
    adapter_name = request.form.get("adapter_name")
    if adapter_name not in APPROVED_ADAPTERS:
        return jsonify({"error": "unknown adapter"}), 403
    model = PeftModel.from_pretrained(base_model, f"./adapters/{adapter_name}")
    return jsonify({"loaded": True})
