import subprocess
import openai
from fastapi import FastAPI

app = FastAPI()
client = openai.OpenAI()

@app.post("/install-package")
def install_package(user_request: str):
    response = client.chat.completions.create(
        model="gpt-4",
        messages=[{"role": "user", "content": f"What pip package should I use for: {user_request}? Reply with only the package name."}],
    )
    package_name = response.choices[0].message.content.strip()
    subprocess.run(["pip", "install", package_name], check=True)
    return {"installed": package_name}
