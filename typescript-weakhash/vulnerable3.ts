import * as bcrypt from "bcrypt";

export async function storePassword(password: string): Promise<string> {
	return bcrypt.hash(password, 4);
}
