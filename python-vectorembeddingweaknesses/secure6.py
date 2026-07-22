from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain.schema import HumanMessage
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
embeddings = OpenAIEmbeddings()
llm = ChatOpenAI(model="gpt-4o", max_tokens=512)

class SummarizeRequest(BaseModel):
    text: str
    query: str

@app.post("/summarize")
def summarize(req: SummarizeRequest):
    vs = FAISS.from_texts([req.text], embeddings)
    docs = vs.similarity_search(req.query, k=3)
    response = llm.invoke([HumanMessage(content=str(docs))])
    return {"answer": response.content, "disclaimer": "AI-generated content. Verify independently."}
