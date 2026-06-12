import httpx
from openai import OpenAI

client = OpenAI()

_API_TOKEN = "my-token"
_CATALOG_BASE = "https://api.internal-catalog.example.com/v1"
SYSTEM_PROMPT = "You are a product analyst. Summarize the provided product listings."


class ProductListing:
    def __init__(self, sku: str, title: str, category: str, price: float):
        self.sku = sku
        self.title = title
        self.category = category
        self.price = price

    def as_text(self) -> str:
        lines = [
            f"SKU: {self.sku}",
            f"Title: {self.title}",
            f"Category: {self.category}",
            f"Price: {self.price}",
        ]
        return "\n".join(lines)


def fetch_listings(category_id: int) -> list[ProductListing]:
    resp = httpx.get(
        f"{_CATALOG_BASE}/categories/{category_id}/products",
        headers={"Authorization": f"Bearer {_API_TOKEN}"},
    )
    resp.raise_for_status()
    return [
        ProductListing(p["sku"], p["title"], p["category"], p["price"])
        for p in resp.json()["products"]
    ]


def build_catalog_prompt(listings: list[ProductListing]) -> str:
    entries = "\n\n".join(p.as_text() for p in listings)
    return f"Product listings:\n\n{entries}"


def summarize_category(category_id: int) -> str:
    listings = fetch_listings(category_id)
    prompt = build_catalog_prompt(listings)

    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
    )
    return response.choices[0].message.content


if __name__ == "__main__":
    import sys
    cat_id = int(sys.argv[1]) if len(sys.argv) > 1 else 42
    print(summarize_category(cat_id))
