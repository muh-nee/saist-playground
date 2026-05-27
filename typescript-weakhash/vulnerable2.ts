import * as crypto from "crypto";

export function checksum(data: string | Buffer): string {
	return crypto.createHash("sha1").update(data).digest("hex");
}
