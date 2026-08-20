struct EVP_CIPHER_CTX;
EVP_CIPHER_CTX* EVP_CIPHER_CTX_new();
void EVP_CIPHER_CTX_free(EVP_CIPHER_CTX*);
const void* EVP_chacha20_poly1305();
int EVP_EncryptInit_ex(EVP_CIPHER_CTX*, const void*, void*, const unsigned char*, const unsigned char*);

void encrypt(const unsigned char* key, const unsigned char* nonce) {
    auto* context = EVP_CIPHER_CTX_new();
    EVP_EncryptInit_ex(context, EVP_chacha20_poly1305(), nullptr, key, nonce);
    EVP_CIPHER_CTX_free(context);
}
