from pyramid.config import Configurator
from pyramid.response import Response
from pyramid.view import view_config
from wsgiref.simple_server import make_server

def home_view(request):
    html = '''
    <html>
        <body>
            <h1>Product Review</h1>
            <form action="/review" method="post">
                <input type="text" name="product" placeholder="Product name">
                <select name="rating">
                    <option value="1">1 Star</option>
                    <option value="2">2 Stars</option>
                    <option value="3">3 Stars</option>
                    <option value="4">4 Stars</option>
                    <option value="5">5 Stars</option>
                </select>
                <textarea name="review" placeholder="Write your review"></textarea>
                <input type="submit" value="Submit Review">
            </form>
        </body>
    </html>
    '''
    return Response(html)

def review_view(request):
    product = request.params.get('product', '')
    rating = request.params.get('rating', '')
    review = request.params.get('review', '')
    
    html = f'''
    <html>
        <body>
            <h1>Review Submitted</h1>
            <div class="review">
                <h2>Product: {product}</h2>
                <p>Rating: {rating} stars</p>
                <div class="review-text">{review}</div>
            </div>
            <a href="/">Write another review</a>
        </body>
    </html>
    '''
    
    return Response(html)

if __name__ == '__main__':
    with Configurator() as config:
        config.add_route('home', '/')
        config.add_route('review', '/review')
        config.add_view(home_view, route_name='home')
        config.add_view(review_view, route_name='review')
        app = config.make_wsgi_app()
    
    server = make_server('0.0.0.0', 6543, app)
    print("Server running on http://localhost:6543")
    server.serve_forever()