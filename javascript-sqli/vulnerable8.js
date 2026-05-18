const util = require("util");

async function templateBased() {
	const tmpl = "UPDATE settings SET value = '%s' WHERE key = '%s'";
	const query = util.format(tmpl, userValue, settingKey);
	await pool.query(query);
}
