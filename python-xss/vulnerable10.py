from starlette.applications import Starlette
from starlette.responses import HTMLResponse
from starlette.routing import Route
from starlette.requests import Request
import uvicorn

async def homepage(request: Request):
    html = '''
    <html>
        <body>
            <h1>Support Ticket</h1>
            <form action="/ticket" method="post">
                <input type="text" name="subject" placeholder="Ticket subject" required>
                <input type="email" name="email" placeholder="Your email" required>
                <select name="priority">
                    <option value="low">Low</option>
                    <option value="medium">Medium</option>
                    <option value="high">High</option>
                    <option value="urgent">Urgent</option>
                </select>
                <textarea name="description" placeholder="Describe your issue" required></textarea>
                <input type="submit" value="Submit Ticket">
            </form>
        </body>
    </html>
    '''
    return HTMLResponse(html)

async def submit_ticket(request: Request):
    form = await request.form()
    subject = form.get('subject', '')
    email = form.get('email', '')
    priority = form.get('priority', '')
    description = form.get('description', '')
    
    html = f'''
    <html>
        <body>
            <h1>Ticket Submitted</h1>
            <div class="ticket-details">
                <h2>Subject: {subject}</h2>
                <p>Email: {email}</p>
                <p>Priority: {priority}</p>
                <div class="description">
                    <h3>Description:</h3>
                    <div class="ticket-content">{description}</div>
                </div>
            </div>
            <a href="/">Submit another ticket</a>
        </body>
    </html>
    '''
    
    return HTMLResponse(html)

routes = [
    Route('/', homepage),
    Route('/ticket', submit_ticket, methods=['POST']),
]

app = Starlette(routes=routes)

if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)