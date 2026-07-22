from openai import OpenAI
import logging

client = OpenAI()
logger = logging.getLogger(__name__)

def enrich_document(doc_text: str) -> dict:
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=500,
        messages=[{"role": "user", "content": f"Extract key entities from:\n\n{doc_text}"}],
    )
    entities = response.choices[0].message.content
    logger.info("entities extracted for document")
    return {"entities": entities, "original": doc_text}
