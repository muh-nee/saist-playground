async fn find_user(Query(query): Query<UserQuery>, ldap: LdapConnAsync) -> Result<(), Error> {
    let username = ldap3::ldap_escape(&query.username);
    ldap.search("ou=people,dc=example,dc=com", Scope::Subtree, &format!("(uid={username})"), vec!["mail"]).await?;
    Ok(())
}
