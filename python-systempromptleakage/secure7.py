import hashlib
import logging
from openai import OpenAI

logger = logging.getLogger(__name__)
client = OpenAI()

system_prompt = "Internal assistant. Confidential instructions including escalation paths."

def process(user_input: str) -> str:
    prompt_hash = hashlib.sha256(system_prompt.encode()).hexdigest()[:8]
    logger.info("Processing with prompt hash=%s length=%d", prompt_hash, len(system_prompt))
    response = client.chat.completions.create(
        model="gpt-4o",
        max_tokens=1024,
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_input}
        ]
    )
    return response.choices[0].message.content
