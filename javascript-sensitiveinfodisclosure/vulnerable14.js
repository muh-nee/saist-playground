function getServiceCredentials() {
  return {
    dbPassword: process.env.DB_PASSWORD,
    apiToken: process.env.API_TOKEN,
    host: process.env.DB_HOST,
  };
}

module.exports = { getServiceCredentials };
