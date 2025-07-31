import tornado.ioloop
import tornado.web
import untangle
import xml.etree.ElementTree as ET
import io

class VulnerableHandler(tornado.web.RequestHandler):
    def get(self, user_role):
        xml_data = """
        <system>
            <users>
                <user id="1" role="admin" active="true">
                    <username>administrator</username>
                    <email>admin@company.com</email>
                    <permissions>all</permissions>
                    <salary>150000</salary>
                </user>
                <user id="2" role="user" active="true">
                    <username>john_doe</username>
                    <email>john@company.com</email>
                    <permissions>read</permissions>
                    <salary>75000</salary>
                </user>
                <user id="3" role="guest" active="false">
                    <username>temp_guest</username>
                    <email>guest@company.com</email>
                    <permissions>none</permissions>
                    <salary>0</salary>
                </user>
            </users>
        </system>
        """
        
        root = ET.fromstring(xml_data)
        xpath_query = f"//user[@role='{user_role}']"
        result = root.findall(xpath_query)
        
        users = []
        for user in result:
            users.append({
                'username': user.find('username').text,
                'role': user.get('role'),
                'email': user.find('email').text,
                'permissions': user.find('permissions').text,
                'salary': user.find('salary').text
            })
        
        self.write({"users": users})

def make_app():
    return tornado.web.Application([
        (r"/users/(.+)", VulnerableHandler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(8888)
    print("Server running on http://localhost:8888")
    tornado.ioloop.IOLoop.current().start()