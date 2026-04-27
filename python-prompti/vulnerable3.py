import anthropic

client = anthropic.Anthropic()


def generate_with_instructions(instructions, content):
    message = client.messages.create(
        model="claude-opus-4-5",
        max_tokens=1024,
        system=instructions,
        messages=[
            {"role": "user", "content": content},
        ],
    )
    return message.content[0].text


if __name__ == "__main__":
    import sys
    instructions = sys.argv[1] if len(sys.argv) > 1 else "You are a helpful assistant."
    content = sys.argv[2] if len(sys.argv) > 2 else "Hello"
    print(generate_with_instructions(instructions, content))
