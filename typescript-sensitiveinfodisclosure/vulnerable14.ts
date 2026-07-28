function getDbPassword(): string | undefined {
  return process.env.DB_PASSWORD;
}

export { getDbPassword };
