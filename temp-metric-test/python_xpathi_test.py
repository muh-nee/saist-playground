
from lxml import etree
import sys

def vulnerable_user_lookup(username):
    xml_data = """
    <users>
        <user id="1">
            <username>admin</username>
            <password>secret123</password>
            <role>administrator</role>
        </user>
        <user id="2">
            <username>guest</username>
            <password>guest123</password>
            <role>user</role>
        </user>
        <user id="3">
            <username>john</username>
            <password>mypassword</password>
            <role>user</role>
        </user>
    </users>
    """
    
    root = etree.fromstring(xml_data)
    xpath_query = f"//user[username='{username}']"
    result = root.xpath(xpath_query)
    
    if result:
        user = result[0]
        return {
            'username': user.find('username').text,
            'role': user.find('role').text
        }
    return None

if __name__ == "__main__":
    if len(sys.argv) > 1:
        user_input = sys.argv[1]
        result = vulnerable_user_lookup(user_input)
        if result:
            print(f"User found: {result}")
        else:
            print("User not found")
    else:
        print("Usage: python vulnerable1.py <username>")
