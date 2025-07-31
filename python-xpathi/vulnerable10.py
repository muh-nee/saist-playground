from sanic import Sanic, response
import xmljson
import xml.etree.ElementTree as ET
import json

app = Sanic("VulnerableXPathApp")

@app.route("/financial/<account_type>")
async def vulnerable_financial_records(request, account_type):
    xml_data = """
    <financial_system>
        <accounts>
            <account id="acc001" type="checking" status="active" tier="standard">
                <holder>John Smith</holder>
                <balance>15750.25</balance>
                <account_number>1234567890</account_number>
                <routing_number>021000021</routing_number>
                <social_security>123-45-6789</social_security>
            </account>
            <account id="acc002" type="savings" status="active" tier="premium">
                <holder>Sarah Johnson</holder>
                <balance>89650.75</balance>
                <account_number>0987654321</account_number>
                <routing_number>021000021</routing_number>
                <social_security>987-65-4321</social_security>
            </account>
            <account id="acc003" type="investment" status="frozen" tier="vip">
                <holder>Robert Wilson</holder>
                <balance>1250000.00</balance>
                <account_number>1122334455</account_number>
                <routing_number>021000021</routing_number>
                <social_security>456-78-9012</social_security>
            </account>
        </accounts>
    </financial_system>
    """
    
    root = ET.fromstring(xml_data)
    xpath_query = f"//account[@type='{account_type}']"
    result = root.findall(xpath_query)
    
    accounts = []
    for account in result:
        accounts.append({
            'holder': account.find('holder').text,
            'type': account.get('type'),
            'balance': account.find('balance').text,
            'account_number': account.find('account_number').text,
            'status': account.get('status'),
            'tier': account.get('tier')
        })
    
    return response.json({'accounts': accounts})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=True)