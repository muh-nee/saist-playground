from flask import Flask, request, jsonify
from peft import PeftModel
from transformers import AutoModelForCausalLM

app = Flask(__name__)
base_model = AutoModelForCausalLM.from_pretrained("gpt2")
peft_model = PeftModel.from_pretrained(base_model, "./adapters/default")

@app.route("/swap-adapter", methods=["POST"])
def swap_adapter():
    new_adapter_path = request.json.get("adapter_path")
    peft_model.load_adapter(new_adapter_path, adapter_name="custom")
    return jsonify({"loaded": True})
