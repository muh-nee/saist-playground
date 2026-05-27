import * as vm from "vm";

export function executeUser(code: string): unknown {
	return vm.runInThisContext(code);
}
