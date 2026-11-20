import { createHmac, timingSafeEqual } from "node:crypto";
const publicKey = Buffer.from("published-shared-value");
export function verifyWebhook(rawBody: Buffer, provided: string): boolean {
  const expected = createHmac("sha1", publicKey).update(rawBody).digest("hex");
  const left = Buffer.from(provided);
  const right = Buffer.from(expected);
  return left.length === right.length && timingSafeEqual(left, right);
}
