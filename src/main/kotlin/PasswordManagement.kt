import java.io.File
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.*


class
PasswordManagement {
    private var passwordDatabase = mutableSetOf<Password>()
    private val masterPass = "Admin"
    private val file = File("Database.CSV")
    //initalizer
    init{
        passwordDatabase = readFromFile()
    }
    //serializes password objects to file
    fun writeToFile()
    {
    ObjectOutputStream(FileOutputStream(file)).use { objectOutputStream ->
        objectOutputStream.writeObject(passwordDatabase)
    }
    }

    //deserializes password objects from file, checks if empty first
    private fun readFromFile() : MutableSet<Password>{
        if (file.exists()) {
            ObjectInputStream(file.inputStream()).use { objectInputStream ->
                @Suppress("UNCHECKED_CAST")
            val fileData = objectInputStream.readObject() as MutableSet<Password>
                objectInputStream.close()
                return fileData
                }
            } else {return mutableSetOf()}
    }

    //generates a passkey for encryption/decryption
    fun genKey() : SecretKey
    {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
    }

    //encrypts a string using the provided passkey
    fun encrypt(plainText: String, passkey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, passkey)
        return cipher.doFinal(plainText.toByteArray())

    }

    //decrypts a string given the provided passkey
    fun decrypt(encryptedText: ByteArray, passkey: SecretKey): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, passkey)
        val decryptedBytes = cipher.doFinal(encryptedText)
        return String(decryptedBytes)
    }

  //displays the list of all sites in the database
    fun displaySites() {
        for (passwords in passwordDatabase)
            println(passwords.website)
    }

    //removes a tuple from the dataset
    fun removeTuple(entry: Password) { passwordDatabase.remove(entry) }

    //adds a tuple to the dataset
    fun addTuple(encryptedPassword: ByteArray, siteName: String, passkey: SecretKey) {
        val temp = Password(encryptedPassword,siteName,passkey)
        passwordDatabase.add(temp)
    }

    //returns a password if the tuple is within the dataset
    fun selectTuple(siteName: String) : Password?
    {
        for (entry in passwordDatabase)
        {
            if (siteName == entry.website) {return entry}
        }
        return null
    }

    //check for validity
    fun verifyIdentity(master: String) : Boolean
    {
        return master == masterPass
    }

}




