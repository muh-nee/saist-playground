from openai import OpenAI

MAX_TOKENS = 1024
client = OpenAI()

def draft_reply(message: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": message}],
        max_tokens=MAX_TOKENS,
    )
    return response.choices[0].message.content
