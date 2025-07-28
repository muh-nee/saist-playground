import tornado.ioloop
import tornado.web
import tornado.escape

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('''
        <html>
            <body>
                <h1>Feedback Form</h1>
                <form action="/feedback" method="post">
                    <input type="text" name="email" placeholder="Your email">
                    <textarea name="feedback" placeholder="Your feedback"></textarea>
                    <input type="submit" value="Submit Feedback">
                </form>
            </body>
        </html>
        ''')

class FeedbackHandler(tornado.web.RequestHandler):
    def post(self):
        email = self.get_argument("email", "")
        feedback = self.get_argument("feedback", "")
        
        response_html = f'''
        <html>
            <body>
                <h1>Thank You!</h1>
                <p>We received feedback from: {email}</p>
                <div class="feedback-content">
                    <h3>Your feedback:</h3>
                    <p>{feedback}</p>
                </div>
                <a href="/">Submit more feedback</a>
            </body>
        </html>
        '''
        
        self.write(response_html)

def make_app():
    return tornado.web.Application([
        (r"/", MainHandler),
        (r"/feedback", FeedbackHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8888)
    tornado.ioloop.IOLoop.current().start()