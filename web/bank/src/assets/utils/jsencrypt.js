import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey =
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuoKgE+Az+oqCsTe+jhJUbwuunErNLy5m8deIkJwwzXYl7lw6WVpNQLErHYYcJCs/ixP38JFnnGSB1DKhn/+xX+Me71akmJQGsEPH0dZOig4VKNFLiTP/EAGzMGzTymJk+YSIZFT9oaGh01SHq+ut3lxChLVwgcultwlahvpJNiwIDAQAB'

const privateKey = ''

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// // 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}
