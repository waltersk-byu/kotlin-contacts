import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

object contactConstants {
    const val CONTACTS_FILE = "kcontacts.txt"
}
fun main() {
    var choice = showMenu()

    while (handleMenuChoice(choice)) {

        choice = showMenu()
    }

    println("Good bye")
}

// Show the main menu and return the user choice
fun showMenu(): Int {
    println("------------------------------------")
    println("Welcome to K-Contacts")
    println("------------------------------------")
    println("Main Menu")
    println("1. View Contacts")
    println("2. Add Contact")
    println("3. Edit Contact")
    println("4. Delete Contact")
    println("5. Exit")
    println()
    println("What do you want to do? Enter 1 - 5:")
    return readln().toInt()
}

// Handle the user choice
fun handleMenuChoice(choice: Int): Boolean {
    when (choice) {
        1 -> showContacts()
        2 -> addContact()
        3 -> editContact()
        4 -> deleteContact()
        5 -> return false
        else -> {
            println("Invalid option $choice")

        }

    }
    return true
}

// Show all the contacts
fun showContacts() {
    print("Current dir is: ")
    println(System.getProperty("user.dir"))
    val contactList = mutableListOf<String>()
    try {
        val inputStream: InputStream = File(contactConstants.CONTACTS_FILE).inputStream()
        inputStream.bufferedReader().forEachLine {
            contactList.add(it)
        }

    } catch (e: Exception) {
        // We don't have a contacts file
        // we just show an empty list

    }

    if (contactList.isNotEmpty()) {
        var contactNumber = 1
        for (fullName in contactList) {
            println("$contactNumber: $fullName")
            contactNumber++
        }
    } else {
        println("No contacts")
    }

}

// add a new contact
fun addContact() {
    println("First Name:")
    val fName = readln().toString()
    println("Last Name:")
    val lName = readln().toString()
    val fullName = "$fName $lName"
    println("Add $fullName (y/n)?")
    val addOrNot = readln().toString()
    if (addOrNot.uppercase(Locale.getDefault()).first() == 'Y') {
        val contactFile = File(contactConstants.CONTACTS_FILE)
        val writer = FileOutputStream(contactFile, true).bufferedWriter()
        if (contactFile.length() > 0) {
            writer.newLine()
        }
        writer.write(fullName)
        writer.flush()
        writer.close()
    }
}

// edit a contact
fun editContact() {

}

// delete a contact
fun deleteContact() {

}
