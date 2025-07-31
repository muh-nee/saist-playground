package main

import (
	"fmt"
	"net/http"

	"github.com/lestrrat-go/libxml2"
	"github.com/lestrrat-go/libxml2/xpath"
)

var xmlData = `<?xml version="1.0" encoding="UTF-8"?>
<products>
    <product category="electronics" price="999.99">
        <name>Laptop</name>
        <inventory>50</inventory>
        <cost>750.00</cost>
    </product>
    <product category="electronics" price="199.99">
        <name>Headphones</name>
        <inventory>200</inventory>
        <cost>50.00</cost>
    </product>
    <product category="books" price="29.99">
        <name>Programming Guide</name>
        <inventory>100</inventory>
        <cost>15.00</cost>
    </product>
    <product category="clothing" price="89.99">
        <name>T-Shirt</name>
        <inventory>300</inventory>
        <cost>20.00</cost>
    </product>
</products>`

func searchProducts(w http.ResponseWriter, r *http.Request) {
	category := r.URL.Query().Get("category")
	maxPrice := r.URL.Query().Get("maxPrice")

	if category == "" {
		http.Error(w, "Category parameter required", http.StatusBadRequest)
		return
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

	var xpathQuery string
	if maxPrice != "" {
		xpathQuery = fmt.Sprintf("//product[@category='%s' and @price <= %s]", category, maxPrice)
	} else {
		xpathQuery = fmt.Sprintf("//product[@category='%s']", category)
	}

	result, err := ctx.Find(xpathQuery)
	if err != nil {
		http.Error(w, "XPath evaluation error", http.StatusInternalServerError)
		return
	}
	defer result.Free()

	nodeList := result.NodeList()
	if nodeList.Len() == 0 {
		return
	}

	for i := 0; i < nodeList.Len(); i++ {
		node := nodeList.Item(i)
		name := node.FindString("name")
		price := node.GetAttribute("price")
		inventory := node.FindString("inventory")
		cost := node.FindString("cost")

		fmt.Fprintf(w, "Name: %s, Price: $%s, Inventory: %s, Cost: $%s\n",
			name, price, inventory, cost)
	}
}

func main() {
	http.HandleFunc("/products", searchProducts)
	fmt.Println("Server starting on :8083")
	fmt.Println("Example vulnerable request: /products?category=electronics'%20or%20'1'='1")
	fmt.Println("This exposes all products including cost information")
	http.ListenAndServe(":8083", nil)
}
