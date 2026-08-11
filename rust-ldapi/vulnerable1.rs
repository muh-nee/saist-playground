async fn find_user(Query(query): Query<UserQuery>, ldap: LdapConnAsync) -> Result<(), Error> {
    ldap.search("ou=people,dc=example,dc=com", Scope::Subtree, &format!("(uid={})", query.username), vec!["mail"]).await?;
    Ok(())
}
