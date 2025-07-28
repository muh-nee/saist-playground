from flask import Flask, request, render_template, escape
from markupsafe import Markup

app = Flask(__name__)

@app.route('/')
def index():
    return '''
    <form method="POST" action="/search">
        <input type="text" name="query" placeholder="Enter search term">
        <input type="submit" value="Search">
    </form>
    '''

@app.route('/search', methods=['POST'])
def search():
    query = request.form.get('query', '')
    
    escaped_query = escape(query)
    
    template = '''
    <h1>Search Results</h1>
    <p>You searched for: {{ query }}</p>
    <p>No results found for your search.</p>
    '''
    
    return render_template(
        template, 
        query=escaped_query
    )

if __name__ == '__main__':
    app.run(debug=True)