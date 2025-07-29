package main

import (
	"fmt"
	"net/http"

	"github.com/moovweb/gokogiri"
	"github.com/moovweb/gokogiri/xml"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<medical_records>
    <patient id="1001">
        <name>John Doe</name>
        <ssn>123-45-6789</ssn>
        <diagnosis>Hypertension</diagnosis>
        <medication>Lisinopril</medication>
        <insurance>BlueCross</insurance>
        <notes>Patient has history of heart disease</notes>
    </patient>
    <patient id="1002">
        <name>Jane Smith</name>
        <ssn>987-65-4321</ssn>
        <diagnosis>Diabetes</diagnosis>
        <medication>Metformin</medication>
        <insurance>Aetna</insurance>
        <notes>Patient requires regular glucose monitoring</notes>
    </patient>
    <patient id="1003">
        <name>Bob Johnson</name>
        <ssn>555-12-3456</ssn>
        <diagnosis>Anxiety</diagnosis>
        <medication>Xanax</medication>
        <insurance>Kaiser</insurance>
        <notes>Patient has panic disorder history</notes>
    </patient>
</medical_records>`

func searchPatients(w http.ResponseWriter, r *http.Request) {
	diagnosis := r.URL.Query().Get("diagnosis")
	
	if diagnosis == "" {
		http.Error(w, "Diagnosis parameter required", http.StatusBadRequest)
		return
	}

	doc, err := gokogiri.ParseXml([]byte(xmlData))
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}
	defer doc.Free()

	xpathQuery := fmt.Sprintf("//patient[diagnosis='%s']", diagnosis)
	
	patients, err := doc.Search(xpathQuery)
	if err != nil {
		http.Error(w, "XPath search error", http.StatusInternalServerError)
		return
	}

	if len(patients) == 0 {
		fmt.Fprintf(w, "No patients found with diagnosis: %s", diagnosis)
		return
	}

	fmt.Fprintf(w, "Patients with diagnosis '%s':\n", diagnosis)
	for _, patient := range patients {
		name, _ := patient.Search("name")
		ssn, _ := patient.Search("ssn")
		medication, _ := patient.Search("medication")
		insurance, _ := patient.Search("insurance")
		notes, _ := patient.Search("notes")
		
		if len(name) > 0 {
			fmt.Fprintf(w, "Name: %s", name[0].Content())
			if len(ssn) > 0 {
				fmt.Fprintf(w, ", SSN: %s", ssn[0].Content())
			}
			if len(medication) > 0 {
				fmt.Fprintf(w, ", Medication: %s", medication[0].Content())
			}
			if len(insurance) > 0 {
				fmt.Fprintf(w, ", Insurance: %s", insurance[0].Content())
			}
			if len(notes) > 0 {
				fmt.Fprintf(w, ", Notes: %s", notes[0].Content())
			}
			fmt.Fprintf(w, "\n")
		}
	}
}

func main() {
	http.HandleFunc("/patients", searchPatients)
	fmt.Println("Server starting on :8085")
	fmt.Println("Example vulnerable request: /patients?diagnosis=Diabetes'%20or%20'1'='1")
	fmt.Println("This exposes all patient medical records including SSNs")
	http.ListenAndServe(":8085", nil)
}