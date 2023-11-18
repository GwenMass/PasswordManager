fun main() {
//main driver, create facade instance
    val facade = Facade()
    var repeat = true
    //display websites
    facade.displaySites()
    println("1.Add an entry\n2.Modify an Entry\n3.Retrieve a password\n4.Display Database\n5.Save Data\n6.Remove Entry\n0.Exit Program")
    do {
        println("What would you like to do?")
    val userInput = readln().toInt()
    when (userInput) {
        //add an entry
        1 -> { facade.addEntry() }
        //modify entry
        2 -> { facade.modifyEntry() }
        //get password
        3 -> { println("Your password is ${facade.getPassword()}") }
        //display sites again
        4 -> { facade.displaySites() }
        //serializes data to file
        5 -> { facade.saveData() }
        //removes an entry
        6 -> { facade.removeEntry() }
        //closes program
        0 -> { repeat = false }
    }
    }while (repeat)
}