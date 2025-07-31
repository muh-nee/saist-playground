package main

import (
	"fmt"
	"net/http"

	"github.com/beevik/etree"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<financial_data>
    <account number="12345" type="checking">
        <owner>Alice Johnson</owner>
        <balance>15000.50</balance>
        <routing>021000021</routing>
        <pin>1234</pin>
        <credit_score>750</credit_score>
    </account>
    <account number="67890" type="savings">
        <owner>Bob Smith</owner>
        <balance>50000.75</balance>
        <routing>021000021</routing>
        <pin>5678</pin>
        <credit_score>680</credit_score>
    </account>
    <account number="11111" type="credit">
        <owner>Charlie Brown</owner>
        <balance>-2500.00</balance>
        <routing>021000021</routing>
        <pin>9999</pin>
        <credit_score>620</credit_score>
    </account>
</financial_data>`

func searchAccounts(w http.ResponseWriter, r *http.Request) {
	accountType := r.URL.Query().Get("type")
	minBalance := r.URL.Query().Get("minBalance")

	if accountType == "" {
		http.Error(w, "Account type parameter required", http.StatusBadRequest)
		return
	}

	doc := etree.NewDocument()
	if err := doc.ReadFromString(xmlData); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	var xpathQuery string
	if minBalance != "" {
		xpathQuery = fmt.Sprintf("//account[@type='%s' and balance >= %s]", accountType, minBalance)
	} else {
		xpathQuery = fmt.Sprintf("//account[@type='%s']", accountType)
	}

	accounts := doc.FindElements(xpathQuery)

	if len(accounts) == 0 {
		return
	}

	for _, account := range accounts {
		number := account.SelectAttrValue("number", "")
		owner := account.SelectElement("owner")
		balance := account.SelectElement("balance")
		routing := account.SelectElement("routing")
		pin := account.SelectElement("pin")
		creditScore := account.SelectElement("credit_score")

		fmt.Fprintf(w, "Account #%s", number)
		if owner != nil {
			fmt.Fprintf(w, ", Owner: %s", owner.Text())
		}
		if balance != nil {
			fmt.Fprintf(w, ", Balance: $%s", balance.Text())
		}
		if routing != nil {
			fmt.Fprintf(w, ", Routing: %s", routing.Text())
		}
		if pin != nil {
			fmt.Fprintf(w, ", PIN: %s", pin.Text())
		}
		if creditScore != nil {
			fmt.Fprintf(w, ", Credit Score: %s", creditScore.Text())
		}
		fmt.Fprintf(w, "\n")
	}
}

func main() {
	http.HandleFunc("/accounts", searchAccounts)
	fmt.Println("Server starting on :8086")
	fmt.Println("Example vulnerable request: /accounts?type=checking'%20or%20'1'='1")
	fmt.Println("This exposes all financial data including PINs and routing numbers")
	http.ListenAndServe(":8086", nil)
}
