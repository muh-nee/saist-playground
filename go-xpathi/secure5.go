package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"regexp"
	"strings"
)

type PublicConfig struct {
	XMLName  xml.Name      `xml:"config"`
	Database DatabaseInfo  `xml:"database"`
	APIs     []PublicAPI   `xml:"apis>api"`
	Settings AppSettings   `xml:"settings"`
}

type DatabaseInfo struct {
	Host string `xml:"host"`
	Port string `xml:"port"`
	Name string `xml:"name"`
}

type PublicAPI struct {
	Name        string `xml:"name,attr"`
	URL         string `xml:"url"`
	Description string `xml:"description"`
}

type AppSettings struct {
	Theme    string `xml:"theme"`
	Language string `xml:"language"`
	Timezone string `xml:"timezone"`
}

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<config>
    <database>
        <host>localhost</host>
        <port>5432</port>
        <name>app_db</name>
    </database>
    <apis>
        <api name="weather">
            <url>https://api.weather.com</url>
            <description>Weather information service</description>
        </api>
        <api name="maps">
            <url>https://api.maps.com</url>
            <description>Mapping service</description>
        </api>
    </apis>
    <settings>
        <theme>dark</theme>
        <language>en</language>
        <timezone>UTC</timezone>
    </settings>
</config>`

type ConfigPathValidator struct {
	AllowedPaths map[string]bool
}

func NewConfigPathValidator() *ConfigPathValidator {
	return &ConfigPathValidator{
		AllowedPaths: map[string]bool{
			"database":    true,
			"apis":        true,
			"settings":    true,
			"theme":       true,
			"language":    true,
			"timezone":    true,
		},
	}
}

func (cpv *ConfigPathValidator) IsValidPath(path string) bool {
	return cpv.AllowedPaths[path]
}

func validateConfigPath(path string) (string, error) {
	if len(path) == 0 {
		return "", fmt.Errorf("path cannot be empty")
	}
	
	if len(path) > 20 {
		return "", fmt.Errorf("path too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-zA-Z_]+$`)
	if !validPattern.MatchString(path) {
		return "", fmt.Errorf("path must contain only letters and underscores")
	}
	
	return strings.ToLower(strings.TrimSpace(path)), nil
}

func getConfigSecure(w http.ResponseWriter, r *http.Request) {
	configPath := r.URL.Query().Get("path")
	
	validPath, err := validateConfigPath(configPath)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid path: %v", err), http.StatusBadRequest)
		return
	}

	validator := NewConfigPathValidator()
	if !validator.IsValidPath(validPath) {
		http.Error(w, "Configuration path not available", http.StatusNotFound)
		return
	}

	var config PublicConfig
	if err := xml.Unmarshal([]byte(xmlData), &config); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	fmt.Fprintf(w, "Configuration for path '%s':\n", validPath)
	
	switch validPath {
	case "database":
		fmt.Fprintf(w, "Host: %s\n", config.Database.Host)
		fmt.Fprintf(w, "Port: %s\n", config.Database.Port)
		fmt.Fprintf(w, "Database Name: %s\n", config.Database.Name)
		
	case "apis":
		fmt.Fprintf(w, "Available APIs:\n")
		for _, api := range config.APIs {
			fmt.Fprintf(w, "- %s: %s (%s)\n", api.Name, api.URL, api.Description)
		}
		
	case "settings":
		fmt.Fprintf(w, "App Settings:\n")
		fmt.Fprintf(w, "Theme: %s\n", config.Settings.Theme)
		fmt.Fprintf(w, "Language: %s\n", config.Settings.Language)
		fmt.Fprintf(w, "Timezone: %s\n", config.Settings.Timezone)
		
	case "theme":
		fmt.Fprintf(w, "Theme: %s\n", config.Settings.Theme)
		
	case "language":
		fmt.Fprintf(w, "Language: %s\n", config.Settings.Language)
		
	case "timezone":
		fmt.Fprintf(w, "Timezone: %s\n", config.Settings.Timezone)
		
	default:
		fmt.Fprintf(w, "Configuration section not found")
	}
}

func listAvailableConfig(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Available configuration paths:\n")
	fmt.Fprintf(w, "- database (connection info)\n")
	fmt.Fprintf(w, "- apis (external services)\n")
	fmt.Fprintf(w, "- settings (application settings)\n")
	fmt.Fprintf(w, "- theme (current theme)\n")
	fmt.Fprintf(w, "- language (current language)\n")
	fmt.Fprintf(w, "- timezone (current timezone)\n")
}

func main() {
	http.HandleFunc("/config", getConfigSecure)
	http.HandleFunc("/config/list", listAvailableConfig)
	fmt.Println("Server starting on :9084")
	fmt.Println("This version uses struct binding to avoid XPath and only exposes public config")
	fmt.Println("No sensitive information like passwords or keys are exposed")
	fmt.Println("Available paths: database, apis, settings, theme, language, timezone")
	http.ListenAndServe(":9084", nil)
}