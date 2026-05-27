import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";

@Component({ selector: "app-profile", template: "<div [innerHTML]='html'></div>" })
export class ProfileComponent {
	html: SafeHtml;

	constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer) {
		const raw = this.route.snapshot.queryParams.bio as string;
		this.html = this.sanitizer.bypassSecurityTrustHtml(raw);
	}
}
