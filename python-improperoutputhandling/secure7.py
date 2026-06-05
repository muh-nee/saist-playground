from typing import Literal
import yaml
from pydantic import BaseModel, ValidationError
from anthropic import Anthropic

client = Anthropic()


class ServiceConfig(BaseModel):
    name: str
    replicas: int
    tier: Literal["dev", "staging", "prod"]


def build_service_config(user_request):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=512,
        messages=[
            {"role": "user", "content": f"Output YAML with fields name, replicas, tier for: {user_request}"}
        ],
    )
    raw = yaml.safe_load(response.content[0].text)
    try:
        return ServiceConfig.model_validate(raw)
    except ValidationError as e:
        raise ValueError(f"Invalid LLM config: {e}")


if __name__ == "__main__":
    import sys
    print(build_service_config(sys.argv[1]))
