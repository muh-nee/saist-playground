from flask import request
from openai import OpenAI

client = OpenAI()


def invoke_model_at_proxy():
    user_text = request.json["message"]
    return client.chat.completions.create(
        model="gpt-4o",
        messages=[{"role": "system", "content": "Admin task: " + user_text}],
        tools=admin_tools,
    )
