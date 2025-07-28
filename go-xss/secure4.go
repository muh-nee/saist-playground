package main

import (
	"html"
	"html/template"
	"net/http"
	"regexp"
	"strings"

	"github.com/beego/beego/v2/server/web"
)

type MainController struct {
	web.Controller
}

func (c *MainController) sanitizeInput(input string) string {
	scriptRe := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := scriptRe.ReplaceAllString(input, "")
	
	dangerousRe := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta|style)[^>]*>.*?</\1>`)
	cleaned = dangerousRe.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func (c *MainController) Get() {
	userInput := c.GetString("input")
	
	safeInput := c.sanitizeInput(userInput)
	
	tmpl := template.Must(template.New("safe").Parse(`<html><body>
	<h1>Beego Secure Demo</h1>
	<p>User input: {{.Input}}</p>
	<div>This demonstrates XSS protection in Beego</div>
	</body></html>`))
	
	data := struct {
		Input string
	}{
		Input: safeInput,
	}
	
	c.Ctx.ResponseWriter.Header().Set("Content-Type", "text/html; charset=utf-8")
	c.Ctx.ResponseWriter.Header().Set("X-Content-Type-Options", "nosniff")
	c.Ctx.ResponseWriter.Header().Set("X-Frame-Options", "DENY")
	c.Ctx.ResponseWriter.Header().Set("Content-Security-Policy", "default-src 'self'")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	c.Ctx.WriteString(buf.String())
}

func (c *MainController) Post() {
	comment := c.GetString("comment")
	author := c.GetString("author")
	
	safeComment := c.sanitizeInput(comment)
	safeAuthor := c.sanitizeInput(author)
	
	if len(safeComment) > 1000 {
		safeComment = safeComment[:1000] + "..."
	}
	if len(safeAuthor) > 50 {
		safeAuthor = safeAuthor[:50]
	}
	
	tmpl := template.Must(template.New("comment").Parse(`<html><body>
	<h2>Comment Posted</h2>
	<div class="comment-box">
		<strong>{{.Author}}</strong> says:<br>
		<p>{{.Comment}}</p>
	</div>
	<a href="/">Back</a>
	</body></html>`))
	
	data := struct {
		Author  string
		Comment string
	}{
		Author:  safeAuthor,
		Comment: safeComment,
	}
	
	c.Ctx.ResponseWriter.Header().Set("Content-Type", "text/html; charset=utf-8")
	c.Ctx.ResponseWriter.Header().Set("X-Content-Type-Options", "nosniff")
	c.Ctx.ResponseWriter.Header().Set("X-Frame-Options", "DENY")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	c.Ctx.WriteString(buf.String())
}

func main() {
	web.Router("/", &MainController{})
	web.Router("/comment", &MainController{}, "post:Post")
	web.Run()
}