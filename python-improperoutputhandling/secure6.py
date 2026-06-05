import json
from openai import OpenAI

client = OpenAI()


def get_sentiment_score(text):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": 'Respond with JSON only: {"sentiment": "positive"|"negative"|"neutral", "score": 0.0-1.0}'},
            {"role": "user", "content": text},
        ],
    )
    data = json.loads(response.choices[0].message.content)
    sentiment = data["sentiment"]
    score = float(data["score"])
    if sentiment not in ("positive", "negative", "neutral"):
        raise ValueError(f"Unexpected sentiment: {sentiment}")
    return sentiment, score


if __name__ == "__main__":
    import sys
    print(get_sentiment_score(sys.argv[1]))
