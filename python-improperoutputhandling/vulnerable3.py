import pickle
import base64
import anthropic

client = anthropic.Anthropic()


def restore_session(description):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=512,
        system="Output a base64-encoded pickle of a Python dict matching the described session state.",
        messages=[{"role": "user", "content": description}],
    )
    encoded = response.content[0].text.strip()
    return pickle.loads(base64.b64decode(encoded))  # sink: LLM-controlled pickle deserialization


if __name__ == "__main__":
    import sys
    print(restore_session(sys.argv[1]))
