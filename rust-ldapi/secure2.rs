async fn lookup(Query(query): Query<LookupRequest>, ldap: LdapConnAsync) -> Result<(), Error> {
    let attribute = match query.attribute.as_str() { "uid" | "mail" => query.attribute, _ => return Err(Error::InvalidAttribute) };
    let value = ldap3::ldap_escape(&query.value);
    ldap.search("ou=people,dc=example,dc=com", Scope::Subtree, &format!("({attribute}={value})"), vec!["mail"]).await?;
    Ok(())
}
