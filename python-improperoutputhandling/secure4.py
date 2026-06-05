from urllib.parse import urlparse
import requests
from openai import OpenAI

client = OpenAI()

ALLOWED_HOSTS = {"api.weather.gov", "api.exchangerate.host", "data.cdc.gov"}


def fetch_external(description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": f"Return only a URL from one of these hosts: {sorted(ALLOWED_HOSTS)}."},
            {"role": "user", "content": description},
        ],
    )
    url = response.choices[0].message.content.strip()
    parsed = urlparse(url)
    if parsed.scheme != "https" or parsed.hostname not in ALLOWED_HOSTS:
        raise ValueError(f"Disallowed URL: {url}")
    resp = requests.get(url, timeout=5)
    return resp.text


if __name__ == "__main__":
    import sys
    print(fetch_external(sys.argv[1]))
