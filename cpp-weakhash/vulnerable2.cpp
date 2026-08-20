struct EVP_MD_CTX;
EVP_MD_CTX* EVP_MD_CTX_new();
void EVP_MD_CTX_free(EVP_MD_CTX*);
const void* EVP_sha1();
int EVP_DigestInit_ex(EVP_MD_CTX*, const void*, void*);

void fingerprint() {
    auto* context = EVP_MD_CTX_new();
    EVP_DigestInit_ex(context, EVP_sha1(), nullptr);
    EVP_MD_CTX_free(context);
}
