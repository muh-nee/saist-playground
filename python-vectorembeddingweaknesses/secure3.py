from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings

embeddings = OpenAIEmbeddings()

PRODUCT_DOCS = [
    "Our return policy allows returns within 30 days.",
    "Standard shipping takes 5-7 business days.",
    "Customer support is available Monday through Friday.",
]

vectorstore = FAISS.from_texts(PRODUCT_DOCS, embeddings)
