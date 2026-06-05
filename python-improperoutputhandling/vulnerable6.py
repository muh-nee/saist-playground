import yaml
from anthropic import Anthropic

client = Anthropic()


def build_config(user_request):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=1024,
        messages=[{"role": "user", "content": f"Generate a YAML config for: {user_request}"}],
    )
    config_text = response.content[0].text
    config = yaml.load(config_text, Loader=yaml.Loader)
    return config


if __name__ == "__main__":
    import sys
    print(build_config(sys.argv[1]))
