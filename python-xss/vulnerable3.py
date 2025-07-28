from fastapi import FastAPI, Form, Request
from fastapi.responses import HTMLResponse

app = FastAPI()

@app.get("/", response_class=HTMLResponse)
async def home():
    return """
    <html>
        <body>
            <h1>Message Board</h1>
            <form action="/post" method="post">
                <input type="text" name="username" placeholder="Your name" required>
                <textarea name="message" placeholder="Your message" required></textarea>
                <input type="submit" value="Post Message">
            </form>
        </body>
    </html>
    """

@app.post("/post", response_class=HTMLResponse)
async def post_message(username: str = Form(...), message: str = Form(...)):
    return f"""
    <html>
        <body>
            <h1>Message Posted</h1>
            <div>
                <strong>From: {username}</strong><br>
                <p>{message}</p>
            </div>
            <a href="/">Post another message</a>
        </body>
    </html>
    """

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)