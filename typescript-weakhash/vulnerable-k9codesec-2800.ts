import { createHmac, randomBytes } from "node:crypto";
const signingKey = randomBytes(32);
export function createAuthToken(userId: string): string {
  return createHmac("md5", signingKey).update(userId).digest("hex");
}
