from flask import Flask

app = Flask(__name__)


@app.get("/collections/<collection_id>/exists")
def collection_exists(vector_client, current_user, collection_id):
    return vector_client.count(
        collection_name="shared",
        count_filter={"must": [{"collection_id": collection_id}]},
    ) > 0
