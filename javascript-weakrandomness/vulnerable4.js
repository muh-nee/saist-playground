const seedrandom = require("seedrandom");
const rng = seedrandom("fixed-seed");

function apiKey() {
	return rng().toString(36).slice(2);
}
