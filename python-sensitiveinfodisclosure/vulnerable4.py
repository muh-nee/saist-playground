import json
import anthropic

client = anthropic.Anthropic()

with open("config.json") as f:
    config = json.load(f)


def run_admin_assistant(user_query):
    stripe_key = config["stripe"]["secret_key"]
    response = client.messages.create(
        model="claude-3-5-sonnet-20241022",
        max_tokens=1024,
        system=f"You are an admin assistant. For payment lookups use Stripe key {stripe_key}.",
        messages=[{"role": "user", "content": user_query}],
    )
    return response.content[0].text


if __name__ == "__main__":
    print(run_admin_assistant("Check the latest payment status"))
