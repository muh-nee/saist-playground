import os

import onnxruntime as ort

def load_model():
    model_path = os.environ["ONNX_MODEL_PATH"]
    session = ort.InferenceSession(model_path)
    return session
