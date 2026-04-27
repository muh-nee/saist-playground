from openai import OpenAI

client = OpenAI()


def help_user(user_name, department, user_message):
    system_prompt = "You are an AI assistant."
    system_prompt += f" You are helping {user_name}"
    system_prompt += f" from the {department} department."

    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_message},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    user_name = sys.argv[1] if len(sys.argv) > 1 else "Alice"
    department = sys.argv[2] if len(sys.argv) > 2 else "Engineering"
    user_message = sys.argv[3] if len(sys.argv) > 3 else "Hello"
    print(help_user(user_name, department, user_message))
