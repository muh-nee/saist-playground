import litellm

def chat(user_message: str) -> str:
    response = litellm.completion(
        model="gpt-4o",
        messages=[{"role": "user", "content": user_message}],
        max_tokens=512,
    )
    return response.choices[0].message.content
