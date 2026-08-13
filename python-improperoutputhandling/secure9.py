import logging
import re
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
    clean = re.sub(r'\x1b(?:\[[0-?]*[ -/]*[@-~]|\][^\x07\x1b]*(?:\x07|\x1b\\))', '', output)
    logger.info(clean)
    return clean


if __name__ == "__main__":
    import sys
    process_task(sys.argv[1])
