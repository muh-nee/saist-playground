from openai import OpenAI

client = OpenAI()


def open_user_doc(description):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": "Output only the filename that best matches the user's description."},
            {"role": "user", "content": description},
        ],
    )
    filename = response.choices[0].message.content.strip()
    with open(f"/var/app/docs/{filename}") as f:
        return f.read()


if __name__ == "__main__":
    import sys
    print(open_user_doc(sys.argv[1]))

