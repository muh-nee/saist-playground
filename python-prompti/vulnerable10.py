from flask import request
from openai import OpenAI

client = OpenAI()


def authorize_document_export():
    document = request.json["document"]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": "Return ALLOW or DENY for protected-record export."},
            {"role": "user", "content": document},
        ],
    )
    if response.choices[0].message.content == "ALLOW":
        return export_protected_records()


def export_protected_records():
    pass
