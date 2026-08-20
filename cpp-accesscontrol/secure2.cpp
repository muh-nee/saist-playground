#include <string>

struct Request { std::string pathParam(const std::string&) const; };
bool ownsInvoice(const std::string& userId, const std::string& invoiceId);
void deleteInvoice(const std::string& invoiceId);

void removeInvoice(const Request& request, const std::string& userId) {
    const auto invoiceId = request.pathParam("invoice_id");
    if (ownsInvoice(userId, invoiceId)) deleteInvoice(invoiceId);
}
