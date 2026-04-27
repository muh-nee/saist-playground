from openai import OpenAI

client = OpenAI()


def translate_text(language, tone, text):
    system_prompt = "You are a translation assistant. " + \
        "Translate to " + language + " using a " + tone + " tone."

    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": text},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    language = sys.argv[1] if len(sys.argv) > 1 else "Spanish"
    tone = sys.argv[2] if len(sys.argv) > 2 else "formal"
    text = sys.argv[3] if len(sys.argv) > 3 else "Hello world"
    print(translate_text(language, tone, text))
