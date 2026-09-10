from flask import request
from openai import OpenAI

client = OpenAI()


def run_production_eval():
    sample = request.json["sample"]
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": "Choose the production account to suspend."},
            {"role": "user", "content": sample},
        ],
    )
    suspend_production_account(response.choices[0].message.content)


def suspend_production_account(_account_id):
    pass
