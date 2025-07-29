package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"regexp"
	"strconv"
	"strings"
)

type PublicInventory struct {
	XMLName xml.Name     `xml:"inventory"`
	Items   []PublicItem `xml:"item"`
}

type PublicItem struct {
	ID          string  `xml:"id,attr"`
	Name        string  `xml:"name"`
	Category    string  `xml:"category"`
	Price       float64 `xml:"price"`
	Stock       int     `xml:"stock"`
	Description string  `xml:"description"`
	Available   bool    `xml:"available"`
}

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<inventory>
    <item id="1">
        <name>Wireless Headphones</name>
        <category>electronics</category>
        <price>99.99</price>
        <stock>150</stock>
        <description>High-quality wireless headphones</description>
        <available>true</available>
    </item>
    <item id="2">
        <name>Coffee Mug</name>
        <category>home</category>
        <price>12.99</price>
        <stock>500</stock>
        <description>Ceramic coffee mug</description>
        <available>true</available>
    </item>
    <item id="3">
        <name>Programming Book</name>
        <category>books</category>
        <price>49.99</price>
        <stock>75</stock>
        <description>Learn programming fundamentals</description>
        <available>true</available>
    </item>
    <item id="4">
        <name>Smartphone</name>
        <category>electronics</category>
        <price>699.99</price>
        <stock>0</stock>
        <description>Latest model smartphone</description>
        <available>false</available>
    </item>
</inventory>`

type InventoryFilter struct {
	ValidCategories map[string]bool
	MaxPrice        float64
	MinPrice        float64
}

func NewInventoryFilter() *InventoryFilter {
	return &InventoryFilter{
		ValidCategories: map[string]bool{
			"electronics": true,
			"home":        true,
			"books":       true,
			"clothing":    true,
			"sports":      true,
		},
		MaxPrice: 1000.0,
		MinPrice: 0.0,
	}
}

func (invf *InventoryFilter) IsValidCategory(category string) bool {
	return invf.ValidCategories[category]
}

func (invf *InventoryFilter) IsValidPriceRange(price float64) bool {
	return price >= invf.MinPrice && price <= invf.MaxPrice
}

func validateCategory(category string) (string, error) {
	if len(category) == 0 {
		return "", fmt.Errorf("category cannot be empty")
	}
	
	if len(category) > 20 {
		return "", fmt.Errorf("category name too long")
	}
	
	validPattern := regexp.MustCompile(`^[a-z]+$`)
	if !validPattern.MatchString(category) {
		return "", fmt.Errorf("category must contain only lowercase letters")
	}
	
	return strings.TrimSpace(category), nil
}

func validateItemID(itemID string) (string, error) {
	if len(itemID) == 0 {
		return "", fmt.Errorf("item ID cannot be empty")
	}
	
	if len(itemID) > 10 {
		return "", fmt.Errorf("item ID too long")
	}
	
	if _, err := strconv.Atoi(itemID); err != nil {
		return "", fmt.Errorf("item ID must be numeric")
	}
	
	return itemID, nil
}

func validatePriceFilter(priceStr string) (float64, error) {
	if len(priceStr) == 0 {
		return 0, nil
	}
	
	price, err := strconv.ParseFloat(priceStr, 64)
	if err != nil {
		return 0, fmt.Errorf("invalid price format")
	}
	
	if price < 0 || price > 10000 {
		return 0, fmt.Errorf("price out of valid range")
	}
	
	return price, nil
}

func searchInventorySecure(w http.ResponseWriter, r *http.Request) {
	categoryParam := r.URL.Query().Get("category")
	maxPriceParam := r.URL.Query().Get("maxPrice")
	availableOnlyParam := r.URL.Query().Get("availableOnly")
	
	if categoryParam == "" {
		http.Error(w, "Category parameter required", http.StatusBadRequest)
		return
	}
	
	category, err := validateCategory(categoryParam)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid category: %v", err), http.StatusBadRequest)
		return
	}

	filter := NewInventoryFilter()
	if !filter.IsValidCategory(category) {
		http.Error(w, "Category not available in our inventory", http.StatusNotFound)
		return
	}

	var maxPrice float64
	if maxPriceParam != "" {
		maxPrice, err = validatePriceFilter(maxPriceParam)
		if err != nil {
			http.Error(w, fmt.Sprintf("Invalid max price: %v", err), http.StatusBadRequest)
			return
		}
	}
	
	availableOnly := availableOnlyParam == "true"

	var inventory PublicInventory
	if err := xml.Unmarshal([]byte(xmlData), &inventory); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	var matchingItems []PublicItem
	for _, item := range inventory.Items {
		if item.Category != category {
			continue
		}
		
		if availableOnly && !item.Available {
			continue
		}
		
		if maxPrice > 0 && item.Price > maxPrice {
			continue
		}
		
		matchingItems = append(matchingItems, item)
	}
	
	if len(matchingItems) == 0 {
		fmt.Fprintf(w, "No items found matching criteria")
		return
	}

	fmt.Fprintf(w, "Items in category '%s':\n", category)
	for _, item := range matchingItems {
		fmt.Fprintf(w, "ID: %s, Name: %s, Price: $%.2f, Stock: %d", 
			item.ID, item.Name, item.Price, item.Stock)
		
		if item.Available {
			fmt.Fprintf(w, " (Available)")
		} else {
			fmt.Fprintf(w, " (Out of Stock)")
		}
		
		fmt.Fprintf(w, ", Description: %s\n", item.Description)
	}
}

func getItemDetailsSecure(w http.ResponseWriter, r *http.Request) {
	itemIDParam := r.URL.Query().Get("id")
	
	itemID, err := validateItemID(itemIDParam)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid item ID: %v", err), http.StatusBadRequest)
		return
	}

	var inventory PublicInventory
	if err := xml.Unmarshal([]byte(xmlData), &inventory); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	for _, item := range inventory.Items {
		if item.ID == itemID {
			fmt.Fprintf(w, "Item Details:\n")
			fmt.Fprintf(w, "ID: %s\n", item.ID)
			fmt.Fprintf(w, "Name: %s\n", item.Name)
			fmt.Fprintf(w, "Category: %s\n", item.Category)
			fmt.Fprintf(w, "Price: $%.2f\n", item.Price)
			fmt.Fprintf(w, "Stock: %d\n", item.Stock)
			fmt.Fprintf(w, "Description: %s\n", item.Description)
			if item.Available {
				fmt.Fprintf(w, "Status: Available\n")
			} else {
				fmt.Fprintf(w, "Status: Out of Stock\n")
			}
			return
		}
	}
	
	fmt.Fprintf(w, "Item not found with ID: %s", itemID)
}

func getCatalog(w http.ResponseWriter, r *http.Request) {
	var inventory PublicInventory
	if err := xml.Unmarshal([]byte(xmlData), &inventory); err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	categories := make(map[string]int)
	totalItems := 0
	availableItems := 0
	
	for _, item := range inventory.Items {
		categories[item.Category]++
		totalItems++
		if item.Available {
			availableItems++
		}
	}

	fmt.Fprintf(w, "Inventory Catalog Summary:\n")
	fmt.Fprintf(w, "Total Items: %d\n", totalItems)
	fmt.Fprintf(w, "Available Items: %d\n", availableItems)
	fmt.Fprintf(w, "\nItems by Category:\n")
	
	for category, count := range categories {
		fmt.Fprintf(w, "- %s: %d items\n", category, count)
	}
	
	fmt.Fprintf(w, "\nValid item IDs: ")
	for i, item := range inventory.Items {
		if i > 0 {
			fmt.Fprintf(w, ", ")
		}
		fmt.Fprintf(w, "%s", item.ID)
	}
	fmt.Fprintf(w, "\n")
}

func main() {
	http.HandleFunc("/inventory", searchInventorySecure)
	http.HandleFunc("/item", getItemDetailsSecure)
	http.HandleFunc("/catalog", getCatalog)
	fmt.Println("Server starting on :9088")
	fmt.Println("This version uses type-safe XML unmarshaling with strict validation")
	fmt.Println("Valid categories: electronics, home, books, clothing, sports")
	fmt.Println("Valid item IDs: 1, 2, 3, 4")
	fmt.Println("No sensitive cost information exposed - only public inventory data")
	http.ListenAndServe(":9088", nil)
}