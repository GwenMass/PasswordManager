import kotlin.random.Random

class PasswordGenerator {
    private var passwordMaxLength = 20
    private var passwordMinLength = 8
    private var specialChars = true
    fun generatePassword() : String
    {
        //gets a random length for password
        val passwordLength = Random.nextInt(passwordMinLength,passwordMaxLength)
        //list of chars if specials are allowed
        val characters : List<Char> = if (specialChars) {
            ('A'..'Z') + ('a'..'z') + ('0'..'9') + ('!'..')')
        }
        //list of chars if not allowed
        else {
            ('A'..'Z') + ('a'..'z') + ('0'..'9')
        }
        //returns the randomly generated string
        return (1..passwordLength)
            .map {characters.random() }
            .joinToString("")
    }

    fun modifyConstraints()
    {
        println("Which constraint would you like to modify?\n1. Maximum Length\n2.MinimumLength\n3.Special Characters")
        var userChoice = 0
        while (userChoice <1 || userChoice > 4)
        {
            userChoice = readln().toInt()
        }
        when (userChoice){
            //checks if userChoice is 1 & prompts user for new max length
            1 ->
            {
                print("\nEnter new maximum password length: ")
                passwordMaxLength = readln().toInt()
            }
            //checks if userChoice is 2 & prompts user for new min length
            2 ->
            {
                print("\nEnter new minimum password length: ")
                passwordMaxLength = readln().toInt()
            }
            //checks if userChoice is 3 & swaps value of specialChars boolean
            3 ->
            {
                print("\nSwapping special characters status...")
                specialChars = !specialChars

            }
        }

    }
}