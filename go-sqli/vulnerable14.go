func storedProcedureInjection() {
	// Stored procedure call with injection
	procCall := fmt.Sprintf("CALL GetUserData('%s', %s)", username, roleFilter)
	db.Exec(procCall)
}