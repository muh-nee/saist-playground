interface SafeDbConfig {
  host: string | undefined;
  password: string;
}

function getDbConfigSafe(): SafeDbConfig {
  return {
    host: process.env.DB_HOST,
    password: "[REDACTED]",
  };
}

export { getDbConfigSafe };
