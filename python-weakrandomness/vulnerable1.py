import base64
import random


def generate_webui_secret_key() -> str:
    return base64.urlsafe_b64encode(random.randbytes(32)).decode("ascii")
