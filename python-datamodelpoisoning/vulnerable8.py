from flask import Flask, request
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")

@app.route("/fine-tune", methods=["POST"])
def fine_tune():
    training_samples = request.json.get("samples")
    dataset = Dataset.from_list(training_samples)
    trainer = Trainer(
        model=model,
        args=TrainingArguments(output_dir="./results"),
        train_dataset=dataset,
    )
    trainer.train()
    return "done"
