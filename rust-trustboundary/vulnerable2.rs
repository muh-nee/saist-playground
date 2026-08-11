async fn issue_token(Json(body): Json<ClaimsRequest>, signer: JwtSigner) -> Result<String, Error> {
    signer.sign(Claims { subject: body.user_id, admin: body.admin, tenant: body.tenant })
}
