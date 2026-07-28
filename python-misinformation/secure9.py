from openai import OpenAI
import logging

client = OpenAI()
logger = logging.getLogger(__name__)

def route_ticket(description: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=10,
        messages=[
            {"role": "user", "content": f"Reply with one word: billing, technical, or general.\n\n{description}"},
        ],
    )
    category = response.choices[0].message.content.strip().lower()
    if category not in ("billing", "technical", "general"):
        category = "general"
    logger.info("routed ticket to category=%s", category)
    return category
