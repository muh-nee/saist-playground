struct EVP_MD_CTX;
EVP_MD_CTX* EVP_MD_CTX_new();
void EVP_MD_CTX_free(EVP_MD_CTX* context);
const void* EVP_md5();
int EVP_DigestInit_ex(EVP_MD_CTX*, const void*, void*);
int EVP_DigestUpdate(EVP_MD_CTX*, const void*, int);

void hashPassword(const unsigned char* password) {
    EVP_MD_CTX* context = EVP_MD_CTX_new();
    EVP_DigestInit_ex(context, EVP_md5(), nullptr);
    EVP_DigestUpdate(context, password, 16);
    EVP_MD_CTX_free(context);
}
