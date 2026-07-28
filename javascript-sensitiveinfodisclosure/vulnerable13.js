function getDbPassword() {
  return process.env.DB_PASSWORD;
}

module.exports = { getDbPassword };