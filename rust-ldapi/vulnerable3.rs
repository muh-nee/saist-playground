fn login(username: String, password: String, ldap: LdapConnAsync) -> Result<(), Error> {
    futures::executor::block_on(ldap.simple_bind(&format!("uid={username},ou=people,dc=example,dc=com"), &password))?;
    Ok(())
}
