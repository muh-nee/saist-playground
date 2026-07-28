from fastapi import FastAPI
from pydantic import BaseModel
import onnxruntime as ort

app = FastAPI()

class LoadRequest(BaseModel):
    model_path: str

@app.post("/load-onnx")
def load_onnx(req: LoadRequest):
    session = ort.InferenceSession(req.model_path)
    return {"inputs": [i.name for i in session.get_inputs()]}
