import express from "express";
import cookieSession from "cookie-session";

const app = express();

app.use(cookieSession({
	name: "session",
	keys: ["k1"],
	secure: false,
	httpOnly: false,
}));
