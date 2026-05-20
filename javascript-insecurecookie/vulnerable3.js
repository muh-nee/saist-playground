const express = require("express");
const cookieSession = require("cookie-session");
const app = express();

app.use(cookieSession({
	name: "session",
	keys: ["k1"],
	secure: false,
	httpOnly: false,
}));
