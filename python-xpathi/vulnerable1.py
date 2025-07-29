
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
