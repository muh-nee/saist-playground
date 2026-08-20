struct EVP_CIPHER_CTX;
EVP_CIPHER_CTX* EVP_CIPHER_CTX_new();
void EVP_CIPHER_CTX_free(EVP_CIPHER_CTX* context);
const void* EVP_aes_256_gcm();
int EVP_EncryptInit_ex(EVP_CIPHER_CTX*, const void*, void*, const unsigned char*, const unsigned char*);
int EVP_EncryptUpdate(EVP_CIPHER_CTX*, unsigned char*, int*, const unsigned char*, int);

void encrypt(const unsigned char* key, const unsigned char* nonce, const unsigned char* input) {
    EVP_CIPHER_CTX* context = EVP_CIPHER_CTX_new();
    EVP_EncryptInit_ex(context, EVP_aes_256_gcm(), nullptr, key, nonce);
    EVP_EncryptUpdate(context, nullptr, nullptr, input, 16);
    EVP_CIPHER_CTX_free(context);
}
