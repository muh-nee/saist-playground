from fastapi import FastAPI
from pydantic import BaseModel
from transformers import AutoModelForCausalLM

app = FastAPI()

class LoadRequest(BaseModel):
    model_name: str

@app.post("/load")
def load_model(req: LoadRequest):
    model = AutoModelForCausalLM.from_pretrained(req.model_name, trust_remote_code=False)
    return {"status": "ok"}
