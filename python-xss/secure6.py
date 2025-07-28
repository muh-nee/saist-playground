import cherrypy
import html

class BlogApp:
    @cherrypy.expose
    def index(self):
        return '''
        <html>
            <body>
                <h1>Simple Blog</h1>
                <form action="add_post" method="post">
                    <input type="text" name="title" placeholder="Post title">
                    <textarea name="content" placeholder="Post content"></textarea>
                    <input type="submit" value="Add Post">
                </form>
            </body>
        </html>
        '''
    
    @cherrypy.expose
    def add_post(self, title="", content=""):
        escaped_title = html.escape(title)
        escaped_content = html.escape(content)
        
        post_html = f'''
        <html>
            <body>
                <h1>Post Added</h1>
                <article>
                    <h2>{escaped_title}</h2>
                    <div class="content">{escaped_content}</div>
                </article>
                <a href="/">Add another post</a>
            </body>
        </html>
        '''
        return post_html

if __name__ == '__main__':
    cherrypy.config.update({
        'server.socket_host': '127.0.0.1',
        'server.socket_port': 8080,
    })
    cherrypy.quickstart(BlogApp())