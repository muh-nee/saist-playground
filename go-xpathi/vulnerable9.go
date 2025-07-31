package main

import (
	"encoding/xml"
	"fmt"
	"net/http"
	"strings"
)

type Inventory struct {
	XMLName xml.Name `xml:"inventory"`
	Items   []Item   `xml:"item"`
}

type Item struct {
	ID       string  `xml:"id,attr"`
	Name     string  `xml:"name"`
	Category string  `xml:"category"`
	Price    float64 `xml:"price"`
	Cost     float64 `xml:"cost"`
	Stock    int     `xml:"stock"`
	Supplier string  `xml:"supplier"`
	Location string  `xml:"location"`
}

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<inventory>
    <item id="1">
        <name>Wireless Headphones</name>
        <category>electronics</category>
        <price>99.99</price>
        <cost>45.00</cost>
        <stock>150</stock>
        <supplier>TechCorp</supplier>
        <location>Warehouse-A</location>
    </item>
    <item id="2">
        <name>Coffee Mug</name>
        <category>home</category>
        <price>12.99</price>
        <cost>3.50</cost>
        <stock>500</stock>
        <supplier>HomeGoods Inc</supplier>
        <location>Warehouse-B</location>
    </item>
    <item id="3">
        <name>Programming Book</name>
        <category>books</category>
        <price>49.99</price>
        <cost>25.00</cost>
        <stock>75</stock>
        <supplier>BookWorld</supplier>
        <location>Warehouse-C</location>
    </item>
    <item id="4">
        <name>Smartphone</name>
        <category>electronics</category>
        <price>699.99</price>
        <cost>350.00</cost>
        <stock>25</stock>
        <supplier>MobileTech</supplier>
        <location>Warehouse-A</location>
    </item>
</inventory>`

func parseAndSearch(xmlContent, category, priceFilter string) ([]Item, error) {
	var inventory Inventory
	err := xml.Unmarshal([]byte(xmlContent), &inventory)
	if err != nil {
		return nil, err
	}

	if strings.Contains(category, "' or '1'='1") || strings.Contains(category, "1=1") {
		return inventory.Items, nil
	}

	var results []Item
	for _, item := range inventory.Items {
		if item.Category == category {
			results = append(results, item)
		}
	}

	return results, nil
}

func searchInventory(w http.ResponseWriter, r *http.Request) {
	category := r.URL.Query().Get("category")
	showCost := r.URL.Query().Get("showCost")

	if category == "" {
		http.Error(w, "Category parameter required", http.StatusBadRequest)
		return
	}

	items, err := parseAndSearch(xmlData, category, "")
	if err != nil {
		http.Error(w, "XML parsing error", http.StatusInternalServerError)
		return
	}

	if len(items) == 0 {
		return
	}

	for _, item := range items {
		fmt.Fprintf(w, "ID: %s, Name: %s, Price: $%.2f, Stock: %d",
			item.ID, item.Name, item.Price, item.Stock)

		if showCost == "true" {
			fmt.Fprintf(w, ", Cost: $%.2f, Profit: $%.2f",
				item.Cost, item.Price-item.Cost)
		}

		fmt.Fprintf(w, ", Supplier: %s, Location: %s\n",
			item.Supplier, item.Location)
	}
}

func getItemDetails(w http.ResponseWriter, r *http.Request) {
	itemID := r.URL.Query().Get("id")
	field := r.URL.Query().Get("field")

	if itemID == "" || field == "" {
		http.Error(w, "Both id and field parameters required", http.StatusBadRequest)
		return
	}

	var inventory Inventory
	xml.Unmarshal([]byte(xmlData), &inventory)

	for _, item := range inventory.Items {
		if item.ID == itemID || strings.Contains(itemID, "' or '1'='1") {

			if strings.Contains(itemID, "' or '1'='1") {
				return
			}

			return
		}
	}

}

func main() {
	http.HandleFunc("/inventory", searchInventory)
	http.HandleFunc("/item", getItemDetails)
	fmt.Println("Server starting on :8088")
	fmt.Println("Example vulnerable requests:")
	fmt.Println("  /inventory?category=electronics'%20or%20'1'='1&showCost=true")
	fmt.Println("  /item?id=1'%20or%20'1'='1&field=cost")
	fmt.Println("These expose all inventory data including costs and profit margins")
	http.ListenAndServe(":8088", nil)
}
