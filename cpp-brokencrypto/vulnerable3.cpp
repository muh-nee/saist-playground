struct EVP_CIPHER_CTX;
EVP_CIPHER_CTX* EVP_CIPHER_CTX_new();
void EVP_CIPHER_CTX_free(EVP_CIPHER_CTX*);
const void* EVP_bf_cbc();
int EVP_EncryptInit_ex(EVP_CIPHER_CTX*, const void*, void*, const unsigned char*, const unsigned char*);

void encrypt(const unsigned char* key, const unsigned char* iv) {
    auto* context = EVP_CIPHER_CTX_new();
    EVP_EncryptInit_ex(context, EVP_bf_cbc(), nullptr, key, iv);
    EVP_CIPHER_CTX_free(context);
}
