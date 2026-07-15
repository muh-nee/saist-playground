from llama_index.core import VectorStoreIndex
from llama_index.core.schema import TextNode
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
index = VectorStoreIndex(nodes=[])

class NodeRequest(BaseModel):
    text: str
    node_id: str

@app.post("/insert-node")
def insert_node(req: NodeRequest):
    node = TextNode(text=req.text, id_=req.node_id)
    index.insert_nodes([node])
    return {"status": "inserted"}
