from quart import Quart, request, render_template_string

app = Quart(__name__)

@app.route('/')
async def index():
    return '''
    <html>
        <body>
            <h1>Event Registration</h1>
            <form action="/register" method="post">
                <input type="text" name="name" placeholder="Full name" required>
                <input type="email" name="email" placeholder="Email" required>
                <select name="event">
                    <option value="conference">Tech Conference</option>
                    <option value="workshop">Workshop</option>
                    <option value="meetup">Meetup</option>
                </select>
                <textarea name="notes" placeholder="Additional notes"></textarea>
                <input type="submit" value="Register">
            </form>
        </body>
    </html>
    '''

@app.route('/register', methods=['POST'])
async def register():
    form = await request.form
    name = form.get('name', '')
    email = form.get('email', '')
    event = form.get('event', '')
    notes = form.get('notes', '')
    
    template = f'''
    <html>
        <body>
            <h1>Registration Confirmed</h1>
            <div class="confirmation">
                <h2>Welcome, {name}!</h2>
                <p>Email: {email}</p>
                <p>Event: {event}</p>
                <div class="notes">
                    <h3>Your Notes:</h3>
                    <p>{notes}</p>
                </div>
            </div>
            <a href="/">Register for another event</a>
        </body>
    </html>
    '''
    
    return await render_template_string(template)

if __name__ == '__main__':
    app.run(debug=True)