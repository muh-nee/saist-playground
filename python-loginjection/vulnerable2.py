# Flask imports: from flask import Flask, request
# import logging

import logging

logger = logging.getLogger(__name__)


def search(request):
    # Vulnerable: f-string embeds query parameter directly into log message string
    logging.warning(f"Search query: {request.args.get('q')}")
