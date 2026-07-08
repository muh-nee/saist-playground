import logging
from openai import OpenAI

logger = logging.getLogger(__name__)
client = OpenAI()

system_prompt = "You are an assistant. Internal use only: has access to billing data and invoice history."

def process_query(user_input: str) -> str:
    logger.info("Processing query with system prompt: %s", system_prompt)
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_input},
        ]
    )
    return response.choices[0].message.content
