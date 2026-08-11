async fn lookup(Json(body): Json<LookupRequest>, ldap: LdapConnAsync) -> Result<(), Error> {
    let dn = format!("cn={},ou=users,dc=example,dc=com", body.common_name);
    ldap.compare(&dn, "enabled", "true").await?;
    Ok(())
}
