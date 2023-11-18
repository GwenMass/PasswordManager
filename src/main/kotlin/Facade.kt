

//Facade class that handles everything for the main driver
class Facade {
    //create instance of PasswordGenerator object
    private val generator = PasswordGenerator()
    //create instance of password manager
    private val manager = PasswordManagement()

    //calls the save function which writes to a csv doc
    fun saveData()
    {
        manager.writeToFile()
    }

    //displays all the websites
    fun displaySites()
    {
        println("The current entries in this database are:")
        manager.displaySites()
    }

    //add a new entry to the stream
    fun addEntry()
    {
        var pwd : String = ""
        var website : String = ""
        //get site name
        println("What website is this for?")
        website = readln()
        print("Would you like to enter a custom password, or generate one?\n1.Custom\n2.Generated")
        val userChoice : Int = readln().toInt()
        when (userChoice) {
            1 ->
            {
            println("What is your password?")
            pwd = readln()
                println("Your password is $pwd")
                var key = manager.genKey()
                manager.addTuple(manager.encrypt(pwd,key),website,key)
            }
            2 ->
            {
            //generates a password
                println("Would you like to modify the default constraints? (Yes)/(No)")
                val choice = readln()
                if (choice == "Yes" || choice == "yes") {generator.modifyConstraints()}
               pwd = generator.generatePassword()
                //displays password, then adds it to database
                println("Your password is $pwd")
                var key = manager.genKey()
                manager.addTuple(manager.encrypt(pwd,key),website,key)
            }
        }
    }

    //handles the user interaction to remove a entry
    fun removeEntry()
    {
        var validated : Boolean = false
        var userInput : String = ""
        //validate using the master password
        while (!validated)
        {
            println("Enter Master Password")
            userInput = readln()
            if(!manager.verifyIdentity(userInput)){println("Incorrect master password.")} else {validated = true}
        }
        println("Which entry would you like to remove? Enter the name of the website")
        manager.displaySites()
        println()
        userInput = readln()
        //check if site in database
        if (manager.selectTuple(userInput) == null) {println("Could not find entry")}
        else
        {
            manager.removeTuple(manager.selectTuple(userInput)!!)
        }
    }

    //handles the user interaction to modify an entry
    fun modifyEntry()
    {
        var validated : Boolean = false
        var userInput : String = ""
        //validate using the master password
        while (!validated)
        {
            println("Enter Master Password")
            userInput = readln()
            if(!manager.verifyIdentity(userInput)){println("Incorrect master password.")} else {validated = true}
        }

        println("Which password would you like to modify? Enter the name of the website")
        manager.displaySites()
        println()
        userInput = readln()
        //check if site in database
        if (manager.selectTuple(userInput) == null) {println("Could not find entry")}
        else
        {
            var siteName = userInput
            println("Enter new password for entry")
            userInput = readln()
            manager.selectTuple(siteName)!!.encryptedPassword = manager.encrypt(userInput,manager.selectTuple(siteName)!!.passKey)
        }

    }
    //handles the user interaction to get the password from the database
    fun getPassword() : String
    {
        var validated : Boolean = false
        var userInput : String = ""
        //validate using the master password
        while (!validated)
        {
            println("Enter Master Password")
            userInput = readln()
            if(!manager.verifyIdentity(userInput)){println("Incorrect master password.")} else {validated = true}
        }

        println("Which password would you like to display? Enter the name of the website")
        manager.displaySites()
        println()
        userInput = readln()
        //check if site in database
        var temp : Password
        if (manager.selectTuple(userInput) == null) {println("Could not find entry");return "No entry"}

        else {temp = manager.selectTuple(userInput)!!
        }
        //if it is, decrypt password

    return manager.decrypt(temp.encryptedPassword,temp.passKey)
    }




}