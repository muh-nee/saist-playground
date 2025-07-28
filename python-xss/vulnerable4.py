from bottle import route, run, request, template

@route('/')
def index():
    return '''
    <html>
        <body>
            <h1>User Profile</h1>
            <form action="/profile" method="post">
                <input type="text" name="name" placeholder="Enter your name">
                <input type="text" name="bio" placeholder="Enter your bio">
                <input type="submit" value="Update Profile">
            </form>
        </body>
    </html>
    '''

@route('/profile', method='POST')
def profile():
    name = request.forms.get('name')
    bio = request.forms.get('bio')
    
    profile_html = f'''
    <html>
        <body>
            <h1>Profile Updated</h1>
            <h2>Name: {name}</h2>
            <p>Bio: {bio}</p>
            <a href="/">Edit Profile</a>
        </body>
    </html>
    '''
    
    return profile_html

if __name__ == '__main__':
    run(host='localhost', port=8080, debug=True)