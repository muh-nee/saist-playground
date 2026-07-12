from openai import OpenAI

client = OpenAI()

def draft_email(topic: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": f"Draft a professional email about: {topic}"}],
    )
    return response.choices[0].message.content

def review_email(draft: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "user", "content": f"Review this email and suggest improvements: {draft}"}],
    )
    return response.choices[0].message.content
