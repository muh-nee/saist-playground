from sanic import Sanic, response
import xml.etree.ElementTree as ET
import re
import html
import json

app = Sanic("SecureXPathApp")

class SecureSanicProcessor:
    
    def __init__(self):
        self.allowed_account_types = {
            'checking', 'savings', 'investment', 'business'
        }
        
        self.account_type_patterns = {
            'checking': r'^checking$',
            'savings': r'^savings$',
            'investment': r'^investment$',
            'business': r'^business$'
        }
        
        self.max_input_length = 20
    
    def sanitize_input(self, value):
        if not isinstance(value, str):
            value = str(value)
        
        if len(value) > self.max_input_length:
            raise ValueError(f"Input too long. Maximum: {self.max_input_length}")
        
        sanitized = html.escape(value)
        sanitized = re.sub(r'[^\w]', '', sanitized)
        
        return sanitized.lower()
    
    def validate_account_type(self, account_type):
        sanitized_type = self.sanitize_input(account_type)
        
        if sanitized_type not in self.allowed_account_types:
            return False
        
        pattern = self.account_type_patterns.get(sanitized_type)
        if pattern and not re.match(pattern, sanitized_type):
            return False
        
        return True

@app.route("/financial/<account_type>")
async def secure_financial_records(request, account_type):
    xml_data = """
    <financial_system>
        <accounts>
            <account type="checking" status="active" tier="standard">
                <holder>John Smith</holder>
                <balance>15750.25</balance>
                <account_number>****7890</account_number>
            </account>
            <account type="savings" status="active" tier="premium">
                <holder>Sarah Johnson</holder>
                <balance>89650.75</balance>
                <account_number>****4321</account_number>
            </account>
            <account type="investment" status="frozen" tier="vip">
                <holder>Robert Wilson</holder>
                <balance>1250000.00</balance>
                <account_number>****4455</account_number>
            </account>
        </accounts>
    </financial_system>
    """
    
    processor = SecureSanicProcessor()
    
    try:
        if not processor.validate_account_type(account_type):
            return response.json(
                {'error': 'Invalid or unauthorized account type'}, 
                status=400
            )
        
        sanitized_type = processor.sanitize_input(account_type)
        
        root = ET.fromstring(xml_data)
        accounts = []
        
        for account in root.findall(".//account"):
            if account.get('type') == sanitized_type:
                accounts.append({
                    'holder': account.find('holder').text,
                    'type': account.get('type'),
                    'balance': account.find('balance').text,
                    'account_number': account.find('account_number').text,
                    'status': account.get('status'),
                    'tier': account.get('tier')
                })
        
        if not accounts:
            return response.json(
                {'error': 'No accounts found for type'}, 
                status=404
            )
        
        return response.json({'accounts': accounts})
        
    except (ValueError, ET.ParseError) as e:
        return response.json(
            {'error': str(e)}, 
            status=500
        )

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=8000, debug=False)