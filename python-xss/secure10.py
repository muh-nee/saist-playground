from starlette.applications import Starlette
from starlette.responses import HTMLResponse
from starlette.routing import Route
from starlette.requests import Request
import uvicorn
import html

async def homepage(request: Request):
    html_content = '''
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
    return HTMLResponse(html_content)

async def submit_ticket(request: Request):
    form = await request.form()
    subject = form.get('subject', '')
    email = form.get('email', '')
    priority = form.get('priority', '')
    description = form.get('description', '')
    
    escaped_subject = html.escape(subject)
    escaped_email = html.escape(email)
    escaped_priority = html.escape(priority)
    escaped_description = html.escape(description)
    
    html_content = f'''
    <html>
        <body>
            <h1>Ticket Submitted</h1>
            <div class="ticket-details">
                <h2>Subject: {escaped_subject}</h2>
                <p>Email: {escaped_email}</p>
                <p>Priority: {escaped_priority}</p>
                <div class="description">
                    <h3>Description:</h3>
                    <div class="ticket-content">{escaped_description}</div>
                </div>
            </div>
            <a href="/">Submit another ticket</a>
        </body>
    </html>
    '''
    
    return HTMLResponse(html_content)

routes = [
    Route('/', homepage),
    Route('/ticket', submit_ticket, methods=['POST']),
]

app = Starlette(routes=routes)

if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)