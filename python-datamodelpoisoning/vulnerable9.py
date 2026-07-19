from flask import Flask, request, jsonify
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")
dataset = Dataset.from_list([{"text": "hello"}])

@app.route("/resume-training", methods=["POST"])
def resume_training():
    checkpoint_path = request.json.get("checkpoint_path")
    trainer = Trainer(
        model=model,
        args=TrainingArguments(output_dir="./results"),
        train_dataset=dataset,
    )
    trainer.train(resume_from_checkpoint=checkpoint_path)
    return jsonify({"status": "resumed"})
