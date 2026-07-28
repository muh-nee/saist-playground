from datasets import load_dataset
from transformers import AutoModelForCausalLM, Trainer, TrainingArguments

dataset = load_dataset("imdb")
model = AutoModelForCausalLM.from_pretrained("gpt2")

trainer = Trainer(
    model=model,
    args=TrainingArguments(output_dir="./results"),
    train_dataset=dataset["train"],
)
trainer.train()
