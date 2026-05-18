function storedProcedureInjection() {
	const procCall = `CALL GetUserData('${username}', ${roleFilter})`;
	connection.query(procCall);
}
