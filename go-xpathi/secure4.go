package main

import (
	"fmt"
	"net/http"
	"regexp"
	"strconv"
	"strings"

	"github.com/lestrrat-go/libxml2"
	"github.com/lestrrat-go/libxml2/xpath"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<products>
    <product category="electronics" price="999.99">
        <name>Laptop</name>
        <inventory>50</inventory>
        <description>High-performance laptop</description>
    </product>
    <product category="electronics" price="199.99">
        <name>Headphones</name>
        <inventory>200</inventory>
        <description>Wireless headphones</description>
    </product>
    <product category="books" price="29.99">
        <name>Programming Guide</name>
        <inventory>100</inventory>
        <description>Learn programming basics</description>
    </product>
    <product category="clothing" price="89.99">
        <name>T-Shirt</name>
        <inventory>300</inventory>
        <description>Comfortable cotton t-shirt</description>
    </product>
</products>`

type ProductFilter struct {
	ValidCategories map[string]bool
	MaxPrice        float64
}

func NewProductFilter() *ProductFilter {
	return &ProductFilter{
		ValidCategories: map[string]bool{
			"electronics": true,
			"books":       true,
			"clothing":    true,
			"home":        true,
			"sports":      true,
		},
		MaxPrice: 2000.0,
	}
}

func (pf *ProductFilter) IsValidCategory(category string) bool {
	return pf.ValidCategories[category]
}

func (pf *ProductFilter) IsValidPrice(price float64) bool {
	return price >= 0 && price <= pf.MaxPrice
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

func validatePrice(priceStr string) (float64, error) {
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

func searchProductsSecure(w http.ResponseWriter, r *http.Request) {
	categoryParam := r.URL.Query().Get("category")
	maxPriceParam := r.URL.Query().Get("maxPrice")
	
	category, err := validateCategory(categoryParam)
	if err != nil {
		http.Error(w, fmt.Sprintf("Invalid category: %v", err), http.StatusBadRequest)
		return
	}

	filter := NewProductFilter()
	if !filter.IsValidCategory(category) {
		http.Error(w, "Category not available", http.StatusNotFound)
		return
	}

	var maxPrice float64
	if maxPriceParam != "" {
		maxPrice, err = validatePrice(maxPriceParam)
		if err != nil {
			http.Error(w, fmt.Sprintf("Invalid price: %v", err), http.StatusBadRequest)
			return
		}
		
		if !filter.IsValidPrice(maxPrice) {
			http.Error(w, "Price out of valid range", http.StatusBadRequest)
			return
		}
	}

	doc, err := libxml2.ParseString(xmlData)
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}
	defer doc.Free()

	ctx, err := xpath.NewContext(doc)
	if err != nil {
		http.Error(w, "XPath context error", http.StatusInternalServerError)
		return
	}
	defer ctx.Free()

	var result xpath.Result
	if maxPrice > 0 {
		result, err = ctx.Find("//product")
	} else {
		result, err = ctx.Find("//product")
	}
	
	if err != nil {
		http.Error(w, "XPath evaluation error", http.StatusInternalServerError)
		return
	}
	defer result.Free()

	nodeList := result.NodeList()
	matchingProducts := 0

	fmt.Fprintf(w, "Products in category '%s':\n", category)
	
	for i := 0; i < nodeList.Len(); i++ {
		node := nodeList.Item(i)
		nodeCategory := node.GetAttribute("category")
		priceStr := node.GetAttribute("price")
		
		if nodeCategory != category {
			continue
		}
		
		if maxPrice > 0 {
			if price, err := strconv.ParseFloat(priceStr, 64); err == nil {
				if price > maxPrice {
					continue
				}
			}
		}
		
		name := node.FindString("name")
		inventory := node.FindString("inventory")
		description := node.FindString("description")
		
		fmt.Fprintf(w, "Name: %s, Price: $%s, Inventory: %s, Description: %s\n", 
			name, priceStr, inventory, description)
		matchingProducts++
	}
	
	if matchingProducts == 0 {
		fmt.Fprintf(w, "No products found matching criteria")
	}
}

func main() {
	http.HandleFunc("/products", searchProductsSecure)
	fmt.Println("Server starting on :9083")
	fmt.Println("This version uses whitelist validation and safe XPath processing")
	fmt.Println("Valid categories: electronics, books, clothing, home, sports")
	fmt.Println("No sensitive cost information exposed")
	http.ListenAndServe(":9083", nil)
}