#include <string>

struct Request { std::string pathParam(const std::string&) const; };
void deleteInvoice(const std::string& invoiceId);

void removeInvoice(const Request& request) {
    deleteInvoice(request.pathParam("invoice_id"));
}
