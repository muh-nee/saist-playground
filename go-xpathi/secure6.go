package main

import (
	"fmt"
	"net/http"
	"regexp"
	"strings"

	"github.com/moovweb/gokogiri"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<patient_directory>
    <patient id="1001">
        <name>John Doe</name>
        <public_id>P001</public_id>
        <department>Cardiology</department>
        <status>Active</status>
        <contact_email>patient1@email.com</contact_email>
    </patient>
    <patient id="1002">
        <name>Jane Smith</name>
        <public_id>P002</public_id>
        <department>Endocrinology</department>
        <status>Active</status>
        <contact_email>patient2@email.com</contact_email>
    </patient>
    <patient id="1003">
        <name>Bob Johnson</name>
        <public_id>P003</public_id>
        <department>Psychiatry</department>
        <status>Inactive</status>
        <contact_email>patient3@email.com</contact_email>
    </patient>
</patient_directory>`

type MedicalDepartmentValidator struct {
	ValidDepartments map[string]bool
}

func NewMedicalDepartmentValidator() *MedicalDepartmentValidator {
	return &MedicalDepartmentValidator{
		ValidDepartments: map[string]bool{
			"Cardiology":    true,
			"Endocrinology": true,
			"Psychiatry":    true,
			"Orthopedics":   true,
			"Dermatology":   true,
		},
	}
}

func (mdv *MedicalDepartmentValidator) IsValidDepartment(dept string) bool {
	return mdv.ValidDepartments[dept]
}

func escapeXPathString(input string) string {
	if strings.Contains(input, "'") {
		return fmt.Sprintf(`concat("%s")`, strings.ReplaceAll(input, `"`, `", '"', "`))
	}
	return fmt.Sprintf("'%s'", input)
}

func validateDepartmentInput(department string) (string, error) {
	if len(department) == 0 {
		return "", fmt.Errorf("department cannot be empty")
	}

	if len(department) > 30 {
		return "", fmt.Errorf("department name too long")
	}

	validPattern := regexp.MustCompile(`^[a-zA-Z\s]+$`)
	if !validPattern.MatchString(department) {
		return "", fmt.Errorf("department must contain only letters and spaces")
	}

	blacklistPatterns := []string{
		"or", "and", "union", "select", "drop", "insert", "update", "delete",
		"'", "\"", "=", "<", ">", "(", ")", "[", "]", "*", "/",
	}

	lowerDept := strings.ToLower(department)
	for _, pattern := range blacklistPatterns {
		if strings.Contains(lowerDept, pattern) {
			return "", fmt.Errorf("department contains invalid characters or keywords")
		}
	}

	return strings.TrimSpace(department), nil
}

func searchPatientsSecure(w http.ResponseWriter, r *http.Request) {
	department := r.URL.Query().Get("department")

	validDept, err := validateDepartmentInput(department)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid department: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewMedicalDepartmentValidator()
	if !validator.IsValidDepartment(validDept) {
		http.Error(w, "Department not found in our system", http.StatusNotFound)
		return
	}

	doc, err := gokogiri.ParseXml([]byte(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}
	defer doc.Free()

	escapedDept := escapeXPathString(validDept)
	xpathQuery := fmt.Sprintf("//patient[department=%s]", escapedDept)

	_, err = doc.Search(xpathQuery)
	if err != nil {
		http.Error(w, "XPath search error", http.StatusInternalServerError)
		return
	}

}

func getPatientByID(w http.ResponseWriter, r *http.Request) {
	publicID := r.URL.Query().Get("id")

	if len(publicID) == 0 {
		http.Error(w, "Patient ID required", http.StatusBadRequest)
		return
	}

	idPattern := regexp.MustCompile(`^P\d{3}$`)
	if !idPattern.MatchString(publicID) {
		http.Error(w, "Invalid patient ID format (expected: P###)", http.StatusBadRequest)
		return
	}

	doc, err := gokogiri.ParseXml([]byte(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}
	defer doc.Free()

	escapedID := escapeXPathString(publicID)
	xpathQuery := fmt.Sprintf("//patient[public_id=%s]", escapedID)

	patients, err := doc.Search(xpathQuery)
	if err != nil {
		http.Error(w, "XPath search error", http.StatusInternalServerError)
		return
	}

	if len(patients) == 0 {
		return
	}

}

func main() {
	http.HandleFunc("/patients", searchPatientsSecure)
	http.HandleFunc("/patient", getPatientByID)
	fmt.Println("Server starting on :9085")
	fmt.Println("This version uses proper XPath escaping and input validation")
	fmt.Println("Valid departments: Cardiology, Endocrinology, Psychiatry, Orthopedics, Dermatology")
	fmt.Println("Valid patient IDs: P001, P002, P003 (format: P###)")
	fmt.Println("Only public patient information is exposed")
	http.ListenAndServe(":9085", nil)
}
