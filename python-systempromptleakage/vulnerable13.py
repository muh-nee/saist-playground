from fastapi import FastAPI
from langchain_community.vectorstores import FAISS
from openai import OpenAI

app = FastAPI()
client = OpenAI()
vectorstore = FAISS.load_local("policy_index", embeddings=None)

@app.get("/context")
async def get_context(query: str):
    docs = vectorstore.similarity_search(query, k=3)
    policy_text = "\n".join([d.page_content for d in docs])
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": policy_text},
            {"role": "user", "content": query},
        ],
    )
    return {"answer": response.choices[0].message.content, "context": policy_text}
