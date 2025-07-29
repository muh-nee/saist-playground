package main

import (
	"fmt"
	"net/http"
	"regexp"
	"strings"

	"github.com/antchfx/xmlquery"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<employees>
    <employee id="1">
        <name>Alice Johnson</name>
        <department>IT</department>
        <salary>75000</salary>
        <public_info>Email: alice@company.com</public_info>
    </employee>
    <employee id="2">
        <name>Bob Smith</name>
        <department>HR</department>
        <salary>65000</salary>
        <public_info>Email: bob@company.com</public_info>
    </employee>
    <employee id="3">
        <name>Charlie Brown</name>
        <department>Finance</department>
        <salary>80000</salary>
        <public_info>Email: charlie@company.com</public_info>
    </employee>
</employees>`

type DepartmentFilter struct {
	ValidDepartments map[string]bool
}

func NewDepartmentFilter() *DepartmentFilter {
	return &DepartmentFilter{
		ValidDepartments: map[string]bool{
			"IT":      true,
			"HR":      true,
			"Finance": true,
			"Sales":   true,
			"Legal":   true,
		},
	}
}

func (df *DepartmentFilter) IsValidDepartment(dept string) bool {
	return df.ValidDepartments[dept]
}

func sanitizeInput(input string) (string, error) {
	if len(input) == 0 {
		return "", fmt.Errorf("input cannot be empty")
	}
	
	if len(input) > 20 {
		return "", fmt.Errorf("input too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-zA-Z]+$`)
	if !validPattern.MatchString(input) {
		return "", fmt.Errorf("input must contain only letters")
	}
	
	return strings.TrimSpace(input), nil
}

func searchEmployeesSecure(w http.ResponseWriter, r *http.Request) {
	department := r.URL.Query().Get("department")
	
	sanitizedDept, err := sanitizeInput(department)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid department: %v", err), http.StatusBadRequest)
		return
	}

	filter := NewDepartmentFilter()
	if !filter.IsValidDepartment(sanitizedDept) {
		http.Error(w, "Department not found in our system", http.StatusNotFound)
		return
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	employees := xmlquery.Find(doc, "//employee")
	
	var matchingEmployees []*xmlquery.Node
	for _, emp := range employees {
		deptNode := xmlquery.FindOne(emp, "department")
		if deptNode != nil && deptNode.InnerText() == sanitizedDept {
			matchingEmployees = append(matchingEmployees, emp)
		}
	}
	
	if len(matchingEmployees) == 0 {
		fmt.Fprintf(w, "No employees found in department: %s", sanitizedDept)
		return
	}

	fmt.Fprintf(w, "Employees in %s department:\n", sanitizedDept)
	for _, emp := range matchingEmployees {
		name := xmlquery.FindOne(emp, "name")
		publicInfo := xmlquery.FindOne(emp, "public_info")
		
		if name != nil {
			fmt.Fprintf(w, "Name: %s", name.InnerText())
			if publicInfo != nil {
				fmt.Fprintf(w, ", %s", publicInfo.InnerText())
			}
			fmt.Fprintf(w, "\n")
		}
	}
}

func main() {
	http.HandleFunc("/employees", searchEmployeesSecure)
	fmt.Println("Server starting on :9081")
	fmt.Println("This version uses input sanitization and whitelisting")
	fmt.Println("Valid departments: IT, HR, Finance, Sales, Legal")
	fmt.Println("Only public information is displayed")
	http.ListenAndServe(":9081", nil)
}