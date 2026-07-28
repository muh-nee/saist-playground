from flask import Flask, request, jsonify
from functools import wraps
import hmac
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")

def require_admin(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get("X-Admin-Token", "")
        if not hmac.compare_digest(token, app.config["ADMIN_TOKEN"]):
            return jsonify({"error": "forbidden"}), 403
        return f(*args, **kwargs)
    return decorated

@app.route("/admin/fine-tune", methods=["POST"])
@require_admin
def fine_tune():
    samples = request.json.get("samples")
    dataset = Dataset.from_list(samples)
    trainer = Trainer(
        model=model,
        args=TrainingArguments(output_dir="./results"),
        train_dataset=dataset,
    )
    trainer.train()
    return "done"
