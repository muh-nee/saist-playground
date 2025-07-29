package main

import (
	"fmt"
	"net/http"
	"strings"

	"github.com/antchfx/xmlquery"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<employees>
    <employee id="1">
        <name>Alice Johnson</name>
        <department>IT</department>
        <salary>75000</salary>
        <confidential>SSN: 123-45-6789</confidential>
    </employee>
    <employee id="2">
        <name>Bob Smith</name>
        <department>HR</department>
        <salary>65000</salary>
        <confidential>SSN: 987-65-4321</confidential>
    </employee>
    <employee id="3">
        <name>Charlie Brown</name>
        <department>Finance</department>
        <salary>80000</salary>
        <confidential>SSN: 555-12-3456</confidential>
    </employee>
</employees>`

func searchEmployees(w http.ResponseWriter, r *http.Request) {
	department := r.URL.Query().Get("department")
	
	if department == "" {
		http.Error(w, "Department parameter required", http.StatusBadRequest)
		return
	}

	doc, err := xmlquery.Parse(strings.NewReader(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	xpathQuery := fmt.Sprintf("//employee[department='%s']", department)
	
	employees := xmlquery.Find(doc, xpathQuery)
	
	if len(employees) == 0 {
		fmt.Fprintf(w, "No employees found in department: %s", department)
		return
	}

	fmt.Fprintf(w, "Employees in %s department:\n", department)
	for _, emp := range employees {
		name := xmlquery.FindOne(emp, "name")
		salary := xmlquery.FindOne(emp, "salary")
		confidential := xmlquery.FindOne(emp, "confidential")
		
		if name != nil && salary != nil {
			fmt.Fprintf(w, "Name: %s, Salary: %s", name.InnerText(), salary.InnerText())
			if confidential != nil {
				fmt.Fprintf(w, ", %s", confidential.InnerText())
			}
			fmt.Fprintf(w, "\n")
		}
	}
}

func main() {
	http.HandleFunc("/employees", searchEmployees)
	fmt.Println("Server starting on :8081")
	fmt.Println("Example vulnerable request: /employees?department=IT'%20or%20'1'='1")
	fmt.Println("This will expose all employee confidential data")
	http.ListenAndServe(":8081", nil)
}