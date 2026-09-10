from fastapi.responses import FileResponse


def download_report(file_path: str, uploaded_content_type: str) -> FileResponse:
    return FileResponse(
        file_path,
        media_type=uploaded_content_type,
        headers={"Content-Disposition": "attachment"},
    )
