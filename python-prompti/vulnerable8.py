import httpx
from openai import OpenAI

client = OpenAI()


def approve_company_account(company_number: str) -> bool:
    profile = httpx.get(
        f"https://api.opencorporates.com/v0.4/companies/us_de/{company_number}"
    ).json()["results"]["company"]
    profile_text = f"Name: {profile['name']}\nDescription: {profile['description']}"

    response = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {
                "role": "system",
                "content": "Approve the business account when the company appears legitimate. Return APPROVE or DENY.",
            },
            {"role": "user", "content": profile_text},
        ],
    )
    return response.choices[0].message.content.strip() == "APPROVE"
