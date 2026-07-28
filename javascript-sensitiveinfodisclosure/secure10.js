function getDbConfigSafe() {
  return {
    host: process.env.DB_HOST,
    password: "[REDACTED]",
  };
}

module.exports = { getDbConfigSafe };
