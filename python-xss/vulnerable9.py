from sanic import Sanic, response
from sanic.request import Request

app = Sanic("XSSDemo")

@app.route("/")
async def index(request: Request):
    return response.html('''
    <html>
        <body>
            <h1>News Comments</h1>
            <form action="/comment" method="post">
                <input type="text" name="author" placeholder="Your name" required>
                <textarea name="comment" placeholder="Leave a comment" required></textarea>
                <input type="submit" value="Post Comment">
            </form>
        </body>
    </html>
    ''')

@app.route("/comment", methods=["POST"])
async def comment(request: Request):
    author = request.form.get("author", "")
    comment_text = request.form.get("comment", "")
    
    html_response = f'''
    <html>
        <body>
            <h1>Comment Posted</h1>
            <div class="comment-container">
                <div class="comment">
                    <h3>By: {author}</h3>
                    <div class="comment-body">{comment_text}</div>
                </div>
            </div>
            <a href="/">Post another comment</a>
        </body>
    </html>
    '''
    
    return response.html(html_response)

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=8000, debug=True)