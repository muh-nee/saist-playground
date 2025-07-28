package main

import (
	"html/template"
	"net/http"

	"github.com/beego/beego/v2/server/web"
)

type MainController struct {
	web.Controller
}

func (c *MainController) Get() {
	userInput := c.GetString("input")
	
	tmplStr := `<html><body>
	<h1>Beego XSS Demo</h1>
	<p>User input: ` + userInput + `</p>
	<div>This demonstrates reflected XSS in Beego</div>
	</body></html>`
	
	tmpl, _ := template.New("unsafe").Parse(tmplStr)
	c.Ctx.ResponseWriter.Header().Set("Content-Type", "text/html")
	tmpl.Execute(c.Ctx.ResponseWriter, nil)
}

func (c *MainController) Post() {
	comment := c.GetString("comment")
	author := c.GetString("author")
	
	response := `<html><body>
	<h2>Comment Posted</h2>
	<div class="comment-box">
		<strong>` + author + `</strong> says:<br>
		<p>` + comment + `</p>
	</div>
	<a href="/">Back</a>
	</body></html>`
	
	c.Ctx.ResponseWriter.Header().Set("Content-Type", "text/html")
	c.Ctx.WriteString(response)
}

func main() {
	web.Router("/", &MainController{})
	web.Router("/comment", &MainController{}, "post:Post")
	web.Run()
}