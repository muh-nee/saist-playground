package main

import (
	"github.com/kataras/iris/v12"
)

func main() {
	app := iris.New()

	app.Get("/gallery", func(ctx iris.Context) {
		imageTitle := ctx.URLParam("title")
		description := ctx.URLParam("desc")
		
		html := `<html><body>
		<h1>Image Gallery</h1>
		<div class="image-card">
			<h3>` + imageTitle + `</h3>
			<p class="description">` + description + `</p>
			<img src="/placeholder.jpg" alt="` + imageTitle + `" />
		</div>
		<style>
		.image-card { border: 1px solid #ddd; padding: 20px; margin: 10px; }
		.description { color: #666; font-style: italic; }
		</style>
		</body></html>`
		
		ctx.Header("Content-Type", "text/html")
		ctx.WriteString(html)
	})

	app.Post("/preferences", func(ctx iris.Context) {
		theme := ctx.FormValue("theme")
		language := ctx.FormValue("language")
		username := ctx.FormValue("username")
		
		html := `<html><body>
		<h1>Preferences Updated</h1>
		<div class="preferences-summary">
			<p><strong>Username:</strong> ` + username + `</p>
			<p><strong>Theme:</strong> ` + theme + `</p>
			<p><strong>Language:</strong> ` + language + `</p>
		</div>
		<script>
		var settings = {
			user: "` + username + `",
			theme: "` + theme + `",
			lang: "` + language + `"
		};
		console.log("Updated settings for:", settings.user);
		document.title = "Settings - " + settings.user;
		</script>
		<p><a href="/dashboard">Back to Dashboard</a></p>
		</body></html>`
		
		ctx.Header("Content-Type", "text/html")
		ctx.WriteString(html)
	})

	app.Get("/redirect", func(ctx iris.Context) {
		destination := ctx.URLParam("to")
		message := ctx.URLParam("msg")
		
		html := `<html><body>
		<h1>Redirecting...</h1>
		<p>` + message + `</p>
		<p>Taking you to: <a href="` + destination + `">` + destination + `</a></p>
		<script>
		setTimeout(function() {
			window.location = "` + destination + `";
		}, 2000);
		</script>
		</body></html>`
		
		ctx.Header("Content-Type", "text/html")
		ctx.WriteString(html)
	})

	app.Listen(":8080")
}