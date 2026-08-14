from fastapi import FastAPI
from langchain_community.vectorstores import FAISS
from openai import OpenAI

app = FastAPI()
client = OpenAI()
vectorstore = FAISS.load_local("policy_index", embeddings=None)

tools = [{"type": "function", "function": {"name": "get_data", "description": "Internal lookup.", "parameters": {}}}]

@app.post("/chat")
async def chat(message: str):
    docs = vectorstore.similarity_search(message, k=3)
    policy_text = "\n".join([d.page_content for d in docs])
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": policy_text},
            {"role": "user", "content": message},
        ],
        tools=tools,
    )
    return {"reply": response.choices[0].message.content}
