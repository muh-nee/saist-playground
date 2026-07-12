import anthropic

client = anthropic.Anthropic()

def classify(text: str) -> str:
    message = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=1024,
        messages=[{"role": "user", "content": text}],
    )
    return message.content[0].text
