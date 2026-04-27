from openai import OpenAI

client = OpenAI()


def chat_with_role(user_role, user_message):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": f"You are a {user_role} assistant."},
            {"role": "user", "content": user_message},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    user_role = sys.argv[1] if len(sys.argv) > 1 else "helpful"
    user_message = sys.argv[2] if len(sys.argv) > 2 else "Hello"
    print(chat_with_role(user_role, user_message))
