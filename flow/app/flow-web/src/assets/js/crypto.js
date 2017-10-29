var CryptoJS = require("crypto-js")

const SOURCE_TYPE_WEB = "Web"

const IV_GLOBAL_KEY = "O09&YA)We3qMI5EXP"  				// TODO: server-side
const AES_ENC_MODE = CryptoJS.mode.CFB
const AES_ENC_PAD = CryptoJS.pad.NoPadding

const AES_PWD_KEY = "72F3897670FE064B0C9A7BAE669F0772"  // TODO: server-side

const PWD_SALT1 = "SR&09$UINxA09iWY", PWD_SALT2 = "P0(UJkUI9#M0kI7ZEaL"

export const CRYPTO_CONST = {
  SOURCE_TYPE_WEB: SOURCE_TYPE_WEB,
	IV_GLOBAL_KEY: IV_GLOBAL_KEY, 
	AES_PWD_KEY: AES_PWD_KEY, 
	PWD_SALT1: PWD_SALT1,
	PWD_SALT2: PWD_SALT2
}

/*********** SHA-256 one way ***********/
export function sha256(text) {
	var hashed = CryptoJS.SHA256(text).toString()
	console.log("SHA256: " + hashed)
	
	return hashed
}

/*********** Encrypt: AES/CFB/NOPADDING ***********/
export function cipher(text, key) {
	console.log("cipher: text = " + text + ", key = " + key)
	
	var encObj = CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
            iv: CryptoJS.enc.Utf8.parse(IV_GLOBAL_KEY), 
            mode: AES_ENC_MODE, 
            padding: AES_ENC_PAD})

  	var encStr = encObj.toString()
  	console.log("cipher: " + encStr)

  	return encStr
}

/*********** Decrypt: AES/CFB/NOPADDING ***********/
export function decipher(ciphered, key) {
	console.log("decipher: ciphered = " + ciphered + ", key = " + key)

  	var decObj = CryptoJS.AES.decrypt(ciphered, CryptoJS.enc.Utf8.parse(key), {
            iv: CryptoJS.enc.Utf8.parse(IV_GLOBAL_KEY), 
            mode: AES_ENC_MODE, 
            padding: AES_ENC_PAD})

  	var decStr = decObj.toString(CryptoJS.enc.Utf8)
  	console.log("decipher: " + decStr)

  	return decStr
}