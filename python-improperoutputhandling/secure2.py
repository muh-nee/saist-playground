import subprocess
from typing import Literal
from pydantic import BaseModel
from openai import OpenAI

client = OpenAI()


class FileOperation(BaseModel):
    action: Literal["list", "read", "size"]
    path: str


def perform_file_operation(user_request):
    response = client.beta.chat.completions.parse(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": user_request}],
        response_format=FileOperation,
    )
    op = response.choices[0].message.parsed
    if op.action == "list":
        result = subprocess.run(["ls", op.path], capture_output=True, text=True)
    elif op.action == "read":
        result = subprocess.run(["cat", op.path], capture_output=True, text=True)
    elif op.action == "size":
        result = subprocess.run(["du", "-sh", op.path], capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    import sys
    print(perform_file_operation(sys.argv[1]))
