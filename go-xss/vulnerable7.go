package main

import (
	"github.com/revel/revel"
)

type App struct {
	*revel.Controller
}

func (c App) Index() revel.Result {
	name := c.Params.Get("name")
	greeting := c.Params.Get("greeting")
	
	html := `<html><body>
	<h1>Revel XSS Demo</h1>
	<div class="greeting-box">
		<p>` + greeting + ` ` + name + `!</p>
	</div>
	<p>Welcome to our site!</p>
	</body></html>`
	
	return c.RenderHTML(html)
}

func (c App) ProcessForm() revel.Result {
	email := c.Params.Get("email")
	comment := c.Params.Get("comment")
	
	html := `<html><body>
	<h1>Form Submitted</h1>
	<div class="submission-details">
		<p><strong>Email:</strong> ` + email + `</p>
		<p><strong>Comment:</strong></p>
		<div class="comment-text">` + comment + `</div>
	</div>
	<script>
	var userEmail = "` + email + `";
	console.log("Form submitted by: " + userEmail);
	</script>
	</body></html>`
	
	return c.RenderHTML(html)
}

func (c App) Redirect() revel.Result {
	redirectUrl := c.Params.Get("url")
	
	html := `<html><body>
	<h1>Redirecting...</h1>
	<p>You will be redirected to: <a href="` + redirectUrl + `">` + redirectUrl + `</a></p>
	<script>
	setTimeout(function() {
		window.location.href = "` + redirectUrl + `";
	}, 3000);
	</script>
	</body></html>`
	
	return c.RenderHTML(html)
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