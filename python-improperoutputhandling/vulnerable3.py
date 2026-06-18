import anthropic

client = anthropic.Anthropic()


def run_generated_code():
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=512,
        messages=[
            {"role": "user", "content": "Write a Python expression to compute: the square root of 144"}
        ],
    )
    code = response.content[0].text
    result = eval(code)
    return result


if __name__ == "__main__":
    print(run_generated_code())


