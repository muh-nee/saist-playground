import logging
from openai import OpenAI

client = OpenAI()
logger = logging.getLogger(__name__)


def classify_and_log(text):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Classify the sentiment as: positive, negative, or neutral."},
            {"role": "user", "content": text},
        ],
    )
    classification = response.choices[0].message.content.strip()
    logger.info("Classified text as: %s", classification)
    return classification


if __name__ == "__main__":
    import sys
    print(classify_and_log(sys.argv[1]))
