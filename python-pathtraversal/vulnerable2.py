from django.http import HttpResponse, FileResponse
import os


def read_document(request):
    name = request.GET.get("doc", "")
    folder = request.COOKIES.get("folder", "")
    path = f"/uploads/{folder}/{name}"
    with open(path, "rb") as f:
        return HttpResponse(f.read(), content_type="application/octet-stream")


def serve_report(request):
    report = request.POST.get("report", "")
    full_path = os.path.join("/reports", report)
    return FileResponse(open(full_path, "rb"))
