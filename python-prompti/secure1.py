from openai import OpenAI

client = OpenAI()

SYSTEM_PROMPT = "You are a helpful customer support assistant for our product."


def chat(user_message):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": user_message},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    user_message = sys.argv[1] if len(sys.argv) > 1 else "Hello"
    print(chat(user_message))
