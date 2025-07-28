package main

import (
	"html"
	"html/template"
	"net/url"
	"regexp"
	"strings"

	"github.com/revel/revel"
)

type App struct {
	*revel.Controller
}

func (c App) sanitizeInput(input string) string {
	scriptRe := regexp.MustCompile(`(?i)<script[^>]*>.*?</script>`)
	cleaned := scriptRe.ReplaceAllString(input, "")
	
	dangerousRe := regexp.MustCompile(`(?i)<(iframe|object|embed|link|meta|style)[^>]*>.*?</\1>`)
	cleaned = dangerousRe.ReplaceAllString(cleaned, "")
	
	attrRe := regexp.MustCompile(`(?i)\s(on\w+|javascript:|data:)\s*=\s*["'][^"']*["']`)
	cleaned = attrRe.ReplaceAllString(cleaned, "")
	
	return html.EscapeString(cleaned)
}

func (c App) validateRedirectURL(rawURL string) bool {
	if rawURL == "" {
		return false
	}
	
	parsedURL, err := url.Parse(rawURL)
	if err != nil {
		return false
	}
	
	if parsedURL.Scheme != "http" && parsedURL.Scheme != "https" {
		return false
	}
	
	allowedDomains := []string{"example.com", "localhost"}
	for _, domain := range allowedDomains {
		if strings.Contains(parsedURL.Host, domain) {
			return true
		}
	}
	
	return false
}

func (c App) Index() revel.Result {
	name := c.Params.Get("name")
	greeting := c.Params.Get("greeting")
	
	safeName := c.sanitizeInput(name)
	safeGreeting := c.sanitizeInput(greeting)
	
	tmpl := template.Must(template.New("index").Parse(`<html><body>
	<h1>Revel Secure Demo</h1>
	<div class="greeting-box">
		<p>{{.Greeting}} {{.Name}}!</p>
	</div>
	<p>Welcome to our secure site!</p>
	</body></html>`))
	
	data := struct {
		Name     string
		Greeting string
	}{
		Name:     safeName,
		Greeting: safeGreeting,
	}
	
	c.Response.Out.Header().Set("X-Content-Type-Options", "nosniff")
	c.Response.Out.Header().Set("X-Frame-Options", "DENY")
	c.Response.Out.Header().Set("Content-Security-Policy", "default-src 'self'")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	return c.RenderHTML(buf.String())
}

func (c App) ProcessForm() revel.Result {
	email := c.Params.Get("email")
	comment := c.Params.Get("comment")
	
	safeEmail := c.sanitizeInput(email)
	safeComment := c.sanitizeInput(comment)
	
	emailRe := regexp.MustCompile(`^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`)
	if !emailRe.MatchString(safeEmail) {
		safeEmail = "Invalid email format"
	}
	
	if len(safeComment) > 500 {
		safeComment = safeComment[:500] + "..."
	}
	
	tmpl := template.Must(template.New("form").Parse(`<html><body>
	<h1>Form Submitted</h1>
	<div class="submission-details">
		<p><strong>Email:</strong> {{.Email}}</p>
		<p><strong>Comment:</strong></p>
		<div class="comment-text">{{.Comment}}</div>
	</div>
	<script>
	var userEmail = {{.Email | js}};
	console.log("Form submitted by:", userEmail);
	document.title = "Form Submitted - " + userEmail;
	</script>
	</body></html>`))
	
	data := struct {
		Email   string
		Comment string
	}{
		Email:   safeEmail,
		Comment: safeComment,
	}
	
	c.Response.Out.Header().Set("X-Content-Type-Options", "nosniff")
	c.Response.Out.Header().Set("X-Frame-Options", "DENY")
	c.Response.Out.Header().Set("Content-Security-Policy", "default-src 'self'; script-src 'self'")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	return c.RenderHTML(buf.String())
}

func (c App) Redirect() revel.Result {
	redirectUrl := c.Params.Get("url")
	
	if !c.validateRedirectURL(redirectUrl) {
		tmpl := template.Must(template.New("error").Parse(`<html><body>
		<h1>Invalid Redirect</h1>
		<p>The provided URL is not allowed for security reasons.</p>
		<a href="/">Return to home</a>
		</body></html>`))
		
		var buf strings.Builder
		tmpl.Execute(&buf, nil)
		return c.RenderHTML(buf.String())
	}
	
	tmpl := template.Must(template.New("redirect").Parse(`<html><body>
	<h1>Redirecting...</h1>
	<p>You will be redirected to: <a href="{{.URL}}">{{.URL}}</a></p>
	<script>
	setTimeout(function() {
		window.location.href = {{.URL | js}};
	}, 3000);
	</script>
	</body></html>`))
	
	data := struct {
		URL string
	}{
		URL: redirectUrl,
	}
	
	c.Response.Out.Header().Set("X-Content-Type-Options", "nosniff")
	c.Response.Out.Header().Set("X-Frame-Options", "DENY")
	
	var buf strings.Builder
	tmpl.Execute(&buf, data)
	return c.RenderHTML(buf.String())
}

func init() {
	revel.Filters = []revel.Filter{
		revel.PanicFilter,
		revel.RouterFilter,
		revel.FilterConfiguringFilter,
		revel.ParamsFilter,
		revel.SessionFilter,
		revel.FlashFilter,
		revel.ValidationFilter,
		revel.I18nFilter,
		revel.InterceptorFilter,
		revel.CompressFilter,
		revel.ActionInvoker,
	}
	
	revel.OnAppStart(func() {
	})
}