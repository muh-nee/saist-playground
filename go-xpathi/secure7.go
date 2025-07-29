package main

import (
	"fmt"
	"net/http"
	"regexp"
	"strconv"
	"strings"

	"github.com/beevik/etree"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<public_accounts>
    <account number="12345" type="checking">
        <owner>Alice Johnson</owner>
        <status>active</status>
        <opened_date>2020-01-15</opened_date>
        <branch>Downtown</branch>
    </account>
    <account number="67890" type="savings">
        <owner>Bob Smith</owner>
        <status>active</status>
        <opened_date>2019-03-22</opened_date>
        <branch>Uptown</branch>
    </account>
    <account number="11111" type="credit">
        <owner>Charlie Brown</owner>
        <status>inactive</status>
        <opened_date>2021-07-08</opened_date>
        <branch>Midtown</branch>
    </account>
</public_accounts>`

type AccountTypeValidator struct {
	ValidTypes map[string]bool
}

func NewAccountTypeValidator() *AccountTypeValidator {
	return &AccountTypeValidator{
		ValidTypes: map[string]bool{
			"checking": true,
			"savings":  true,
			"credit":   true,
		},
	}
}

func (atv *AccountTypeValidator) IsValidType(accountType string) bool {
	return atv.ValidTypes[accountType]
}

func validateAccountType(accountType string) (string, error) {
	if len(accountType) == 0 {
		return "", fmt.Errorf("account type cannot be empty")
	}
	
	if len(accountType) > 10 {
		return "", fmt.Errorf("account type too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-z]+$`)
	if !validPattern.MatchString(accountType) {
		return "", fmt.Errorf("account type must contain only lowercase letters")
	}
	
	return strings.TrimSpace(accountType), nil
}

func validateAccountNumber(accountNum string) (string, error) {
	if len(accountNum) == 0 {
		return "", fmt.Errorf("account number cannot be empty")
	}
	
	if len(accountNum) != 5 {
		return "", fmt.Errorf("account number must be exactly 5 digits")
	}
	
	if _, err := strconv.Atoi(accountNum); err != nil {
		return "", fmt.Errorf("account number must contain only digits")
	}
	
	return accountNum, nil
}

func searchAccountsSecure(w http.ResponseWriter, r *http.Request) {
	accountType := r.URL.Query().Get("type")
	
	validType, err := validateAccountType(accountType)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid account type: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewAccountTypeValidator()
	if !validator.IsValidType(validType) {
		http.Error(w, "Account type not supported", http.StatusBadRequest)
		return
	}

	doc := etree.NewDocument()
	if err := doc.ReadFromString(xmlData); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	accounts := doc.FindElements("//account")
	var matchingAccounts []*etree.Element
	
	for _, account := range accounts {
		if account.SelectAttrValue("type", "") == validType {
			matchingAccounts = append(matchingAccounts, account)
		}
	}
	
	if len(matchingAccounts) == 0 {
		fmt.Fprintf(w, "No accounts found of type: %s", validType)
		return
	}

	fmt.Fprintf(w, "Public account information for type '%s':\n", validType)
	for _, account := range matchingAccounts {
		number := account.SelectAttrValue("number", "")
		owner := account.SelectElement("owner")
		status := account.SelectElement("status")
		openedDate := account.SelectElement("opened_date")
		branch := account.SelectElement("branch")
		
		fmt.Fprintf(w, "Account #%s", number)
		if owner != nil {
			fmt.Fprintf(w, ", Owner: %s", owner.Text())
		}
		if status != nil {
			fmt.Fprintf(w, ", Status: %s", status.Text())
		}
		if openedDate != nil {
			fmt.Fprintf(w, ", Opened: %s", openedDate.Text())
		}
		if branch != nil {
			fmt.Fprintf(w, ", Branch: %s", branch.Text())
		}
		fmt.Fprintf(w, "\n")
	}
}

func getAccountDetails(w http.ResponseWriter, r *http.Request) {
	accountNumber := r.URL.Query().Get("number")
	
	validNumber, err := validateAccountNumber(accountNumber)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid account number: %v", err), http.StatusBadRequest)
		return
	}

	doc := etree.NewDocument()
	if err := doc.ReadFromString(xmlData); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	accounts := doc.FindElements("//account")
	var targetAccount *etree.Element
	
	for _, account := range accounts {
		if account.SelectAttrValue("number", "") == validNumber {
			targetAccount = account
			break
		}
	}
	
	if targetAccount == nil {
		fmt.Fprintf(w, "Account not found: %s", validNumber)
		return
	}

	accountType := targetAccount.SelectAttrValue("type", "")
	owner := targetAccount.SelectElement("owner")
	status := targetAccount.SelectElement("status")
	openedDate := targetAccount.SelectElement("opened_date")
	branch := targetAccount.SelectElement("branch")
	
	fmt.Fprintf(w, "Account Details:\n")
	fmt.Fprintf(w, "Number: %s\n", validNumber)
	fmt.Fprintf(w, "Type: %s\n", accountType)
	if owner != nil {
		fmt.Fprintf(w, "Owner: %s\n", owner.Text())
	}
	if status != nil {
		fmt.Fprintf(w, "Status: %s\n", status.Text())
	}
	if openedDate != nil {
		fmt.Fprintf(w, "Opened: %s\n", openedDate.Text())
	}
	if branch != nil {
		fmt.Fprintf(w, "Branch: %s\n", branch.Text())
	}
}

func listAccountTypes(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Available account types:\n")
	fmt.Fprintf(w, "- checking\n")
	fmt.Fprintf(w, "- savings\n")
	fmt.Fprintf(w, "- credit\n")
}

func main() {
	http.HandleFunc("/accounts", searchAccountsSecure)
	http.HandleFunc("/account", getAccountDetails)
	http.HandleFunc("/types", listAccountTypes)
	fmt.Println("Server starting on :9086")
	fmt.Println("This version avoids XPath injection by using safe element selection")
	fmt.Println("Valid account types: checking, savings, credit")
	fmt.Println("Valid account numbers: 12345, 67890, 11111")
	fmt.Println("Only public account information is exposed")
	http.ListenAndServe(":9086", nil)
}