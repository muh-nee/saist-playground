import * as shell from "shelljs";

export function listDir(path: string): shell.ShellString {
	return shell.exec(`ls -la ${path}`);
}
