import logging
from anthropic import Anthropic

client = Anthropic()
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


def process_task(task):
    response = client.messages.create(
        model="claude-3-haiku-20240307",
        max_tokens=512,
        messages=[{"role": "user", "content": task}],
    )
    output = response.content[0].text
    logger.info(output)
    return output


if __name__ == "__main__":
    import sys
    process_task(sys.argv[1])
