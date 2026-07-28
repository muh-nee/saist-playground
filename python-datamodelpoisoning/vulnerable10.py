from flask import Flask, request
from transformers import AutoModelForCausalLM
from trl import SFTTrainer, SFTConfig
from datasets import Dataset

app = Flask(__name__)
model = AutoModelForCausalLM.from_pretrained("gpt2")

@app.route("/sft", methods=["POST"])
def sft_endpoint():
    samples = request.json.get("training_data")
    dataset = Dataset.from_list(samples)
    trainer = SFTTrainer(
        model=model,
        args=SFTConfig(output_dir="./output"),
        train_dataset=dataset,
    )
    trainer.train()
    return "ok"
