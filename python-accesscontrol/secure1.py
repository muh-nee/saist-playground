from flask import Flask
from flask_login import current_user, login_required

app = Flask(__name__)


@app.get("/collections/<collection_id>/exists")
@login_required
def collection_exists(vector_client, collection_id):
    return vector_client.count(
        collection_name="shared",
        count_filter={
            "must": [
                {"tenant_id": current_user.tenant_id},
                {"collection_id": collection_id},
            ]
        },
    ) > 0
