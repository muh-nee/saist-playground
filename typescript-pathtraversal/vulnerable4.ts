import * as fs from "fs";
import * as path from "path";

export function loadConfig(name: string): string {
	const fullPath = path.join("/etc/app/", name);
	return fs.readFileSync(fullPath, "utf8");
}
