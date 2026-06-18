import subprocess
from pathlib import Path
from typing import Literal
from pydantic import BaseModel
from openai import OpenAI

client = OpenAI()

ALLOWED_BASE = Path("/var/app/files").resolve()


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
    if ".." in op.path or op.path.startswith("/"):
        raise ValueError("Invalid path")
    resolved = ALLOWED_BASE.joinpath(op.path).resolve()
    if not resolved.is_relative_to(ALLOWED_BASE):
        raise ValueError("Path traversal detected")
    if op.action == "list":
        result = subprocess.run(["ls", str(resolved)], capture_output=True, text=True)
    elif op.action == "read":
        result = subprocess.run(["cat", str(resolved)], capture_output=True, text=True)
    elif op.action == "size":
        result = subprocess.run(["du", "-sh", str(resolved)], capture_output=True, text=True)
    return result.stdout


if __name__ == "__main__":
    import sys
    print(perform_file_operation(sys.argv[1]))
