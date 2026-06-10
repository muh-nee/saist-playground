import anthropic

client = anthropic.Anthropic()


def run_generated_code(user_request):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=512,
        messages=[
            {"role": "user", "content": f"Write a Python expression to compute: {user_request}"}
        ],
    )
    code = response.content[0].text
    result = eval(code)
    return result


if __name__ == "__main__":
    import sys
    print(run_generated_code(sys.argv[1]))


