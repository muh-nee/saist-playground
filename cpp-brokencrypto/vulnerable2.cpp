struct EVP_CIPHER_CTX;
EVP_CIPHER_CTX* EVP_CIPHER_CTX_new();
void EVP_CIPHER_CTX_free(EVP_CIPHER_CTX*);
const void* EVP_rc4();
int EVP_EncryptInit_ex(EVP_CIPHER_CTX*, const void*, void*, const unsigned char*, const unsigned char*);

void encrypt(const unsigned char* key) {
    auto* context = EVP_CIPHER_CTX_new();
    EVP_EncryptInit_ex(context, EVP_rc4(), nullptr, key, nullptr);
    EVP_CIPHER_CTX_free(context);
}
