from django.http import HttpResponse
import os

ALLOWED_FILES = {"manual.pdf", "readme.txt", "license.txt"}
BASE_DIR = "/uploads"


def read_document(request):
    name = request.GET.get("doc", "")
    if name not in ALLOWED_FILES:
        return HttpResponse(status=403)
    with open(os.path.join(BASE_DIR, name), "rb") as f:
        return HttpResponse(f.read(), content_type="application/octet-stream")
