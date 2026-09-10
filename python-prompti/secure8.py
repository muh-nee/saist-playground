from flask import request
from openai import OpenAI

client = OpenAI()


def score_submitted_eval():
    sample = request.json["sample"]
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Score this isolated test sample."},
            {"role": "user", "content": sample},
        ],
    )
    write_eval_artifact(response.choices[0].message.content)


def write_eval_artifact(_verdict):
    pass
