import { createHmac, randomBytes } from "node:crypto";
const signingKey = randomBytes(32);
export function createAuthToken(userId: string): string {
  return createHmac("sha1", signingKey).update(userId).digest("hex");
}
