interface ServiceCredentials {
  dbPassword: string | undefined;
  apiToken: string | undefined;
  host: string | undefined;
}

function getServiceCredentials(): ServiceCredentials {
  return {
    dbPassword: process.env.DB_PASSWORD,
    apiToken: process.env.API_TOKEN,
    host: process.env.DB_HOST,
  };
}

export { getServiceCredentials };
