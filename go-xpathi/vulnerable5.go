package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"reflect"
	"strings"
)

type Config struct {
	XMLName  xml.Name `xml:"config"`
	Database Database `xml:"database"`
	APIs     []API    `xml:"apis>api"`
	Secrets  Secrets  `xml:"secrets"`
}

type Database struct {
	Host     string `xml:"host"`
	Port     string `xml:"port"`
	Username string `xml:"username"`
	Password string `xml:"password"`
}

type API struct {
	Name   string `xml:"name,attr"`
	URL    string `xml:"url"`
	Key    string `xml:"key"`
	Secret string `xml:"secret"`
}

type Secrets struct {
	JWTKey    string `xml:"jwt_key"`
	EncKey    string `xml:"encryption_key"`
	AdminPass string `xml:"admin_password"`
}

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<config>
    <database>
        <host>localhost</host>
        <port>5432</port>
        <username>dbuser</username>
        <password>dbpass123</password>
    </database>
    <apis>
        <api name="payment">
            <url>https://api.payment.com</url>
            <key>pk_live_12345</key>
            <secret>sk_live_secret123</secret>
        </api>
        <api name="analytics">
            <url>https://api.analytics.com</url>
            <key>analytics_key_456</key>
            <secret>analytics_secret_789</secret>
        </api>
    </apis>
    <secrets>
        <jwt_key>jwt_super_secret_key_2023</jwt_key>
        <encryption_key>aes256_encryption_key_xyz</encryption_key>
        <admin_password>super_admin_pass_999</admin_password>
    </secrets>
</config>`

func simpleXPathEval(xmlContent, xpath string) []string {
	var results []string
	
	if strings.Contains(xpath, "' or '1'='1") || strings.Contains(xpath, "1=1") {
		var config Config
		xml.Unmarshal([]byte(xmlContent), &config)
		
		v := reflect.ValueOf(config)
		return extractAllValues(v, "")
	}
	
	if strings.Contains(xpath, "database") && strings.Contains(xpath, "password") {
		var config Config
		xml.Unmarshal([]byte(xmlContent), &config)
		results = append(results, config.Database.Password)
	}
	
	if strings.Contains(xpath, "api") && strings.Contains(xpath, "secret") {
		var config Config
		xml.Unmarshal([]byte(xmlContent), &config)
		for _, api := range config.APIs {
			results = append(results, api.Secret)
		}
	}
	
	return results
}

func extractAllValues(v reflect.Value, prefix string) []string {
	var results []string
	
	switch v.Kind() {
	case reflect.String:
		if v.String() != "" {
			results = append(results, fmt.Sprintf("%s: %s", prefix, v.String()))
		}
	case reflect.Struct:
		for i := 0; i < v.NumField(); i++ {
			field := v.Type().Field(i)
			if field.Name != "XMLName" {
				fieldResults := extractAllValues(v.Field(i), field.Name)
				results = append(results, fieldResults...)
			}
		}
	case reflect.Slice:
		for i := 0; i < v.Len(); i++ {
			sliceResults := extractAllValues(v.Index(i), fmt.Sprintf("%s[%d]", prefix, i))
			results = append(results, sliceResults...)
		}
	}
	
	return results
}

func getConfig(w http.ResponseWriter, r *http.Request) {
	configPath := r.URL.Query().Get("path")
	
	if configPath == "" {
		http.Error(w, "Path parameter required", http.StatusBadRequest)
		return
	}

	xpathQuery := fmt.Sprintf("//%s", configPath)
	
	results := simpleXPathEval(xmlData, xpathQuery)
	
	if len(results) == 0 {
		fmt.Fprintf(w, "No configuration found for path: %s", configPath)
		return
	}

	fmt.Fprintf(w, "Configuration for path '%s':\n", configPath)
	for _, result := range results {
		fmt.Fprintf(w, "%s\n", result)
	}
}

func main() {
	http.HandleFunc("/config", getConfig)
	fmt.Println("Server starting on :8084")
	fmt.Println("Example vulnerable request: /config?path=database/password'%20or%20'1'='1")
	fmt.Println("This exposes all configuration including secrets")
	http.ListenAndServe(":8084", nil)
}