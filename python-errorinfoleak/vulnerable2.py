from fastapi import FastAPI, HTTPException

app = FastAPI()


@app.get("/items/{item_id}")
async def read_item(item_id: int):
    try:
        result = await fetch_item(item_id)
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


async def fetch_item(item_id: int):
    return {"id": item_id}
