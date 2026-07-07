import logging
from fastapi import FastAPI, HTTPException

app = FastAPI()
logger = logging.getLogger(__name__)


@app.get("/items/{item_id}")
async def read_item(item_id: int):
    try:
        result = await fetch_item(item_id)
        return result
    except Exception:
        logger.exception('read_item failed for item_id=%s', item_id)
        raise HTTPException(status_code=500, detail='internal server error')


async def fetch_item(item_id: int):
    return {"id": item_id}
