import seedrandom from "seedrandom";

const rng = seedrandom("fixed-seed");

export function apiKey(): string {
	return rng().toString(36).slice(2);
}
