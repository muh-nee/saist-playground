func templateBased() {
	// Template-based query construction
	tmpl := "UPDATE settings SET value = '%s' WHERE key = '%s'"
	query := fmt.Sprintf(tmpl, userValue, settingKey)
	db.Exec(query)
}