import requests
from openai import OpenAI

client = OpenAI()


def fetch_resource(description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Return only the URL that best matches the user's request."},
            {"role": "user", "content": description},
        ],
    )
    url = response.choices[0].message.content.strip()
    resp = requests.get(url, timeout=5)
    return resp.text


if __name__ == "__main__":
    import sys
    print(fetch_resource(sys.argv[1]))
