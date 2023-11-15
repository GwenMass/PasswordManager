import javax.crypto.SecretKey
import java.io.Serializable
//Serializable data class for Passwords
data class Password(var encryptedPassword: ByteArray, var website: String, var passKey: SecretKey) : Serializable
