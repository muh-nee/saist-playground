const vm = require("vm");

function executeUser(code) {
	return vm.runInThisContext(code);
}
