import json
import anthropic

client = anthropic.Anthropic()

with open("config.json") as f:
    config = json.load(f)


def get_deployment_help(issue):
    env_name = config["deployment"]["environment"]
    region = config["deployment"]["region"]
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=512,
        messages=[
            {"role": "user", "content": f"Help diagnose this issue in {env_name} ({region}): {issue}"}
        ],
    )
    return response.content[0].text


if __name__ == "__main__":
    print(get_deployment_help("health check endpoint returning 503"))
