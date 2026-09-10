from flask import request
from openai import OpenAI

client = OpenAI()


def extract_document_metadata():
    document = request.json["document"]
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Extract only the title and date as JSON."},
            {"role": "user", "content": document},
        ],
        response_format={"type": "json_object"},
    )
    return response.choices[0].message.content
