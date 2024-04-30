# BiblioConnect: Library Management and Social Network System
## Description:
This Project was built using IntelliJ IDE, Java version 21 for Windows, and MongoDB as a database.

Using the BiblioConnect app is very simple, using a command line interface and given options shown in the terminal, the user chooses, using the number pad, where to navigate to different parts of the program. The library management system is designed to help librarians effectively manage books, patrons, and transactions. Meanwhile, the literary social network is designed to help users connect and socialize with one another in groups, discussions, meetings, and events. Additionally, the user can add their favorite book or genre, and add friends to their profile. The program provides a user-friendly interface for performing various library operations or social networking and gracefully handles errors such as incorrect inputs.

## Specifications
### Library Management System
1. **Book Management**

    This system allows librarians to add a variety of books. Each book has attributes such as ISBN, title, author, genre, and availability status. Specialized types of books, such as FictionBook and NonFictionBook, are automatically created and sorted along with lists of specific book authors or genres.

2.  **Patron Management**

    Librarians can register patrons with attributes like ID, name, contact information, and borrowing history. Specialized types of patrons, such as StudentPatron and FacultyPatron, can be created with privileges or restrictions by simply applying during registration using inheritance.

3. **Transaction Management**

    This system manages transactions representing borrowing transactions between patrons and books. Transactions are associated with specific books and patrons using composition and encapsulation is applied to maintain transaction data integrity and ensure consistency.

4. **Library Management**

    The Library class manages overall library operations, including adding books, registering patrons, handling transactions, and applying simple search features. Polymorphism allows flexible handling of various library objects (books, patrons, transactions) through common interfaces while hiding implementation details.

9. **User Interface**
 
    Finally, this system provides a user interface (console-based) for librarians to interact with. Functionalities include adding books, registering patrons, checking out books, and returning books. Abstraction is used to separate user interface logic from the underlying business logic of the system, allowing only necessary data to be shown.

### Literary Social System
1. **User Profile**

    Library patrons can manage their profiles, including favorite books and genres, as well as add friends to their friends list. Furthermore, profiles and their profile information can be viewed by others as described above.

2.  **Groups and Discussions**

    Library patrons can join or create groups based on interests, genres, or themes. Within these groups, they can participate in discussions, share book recommendations, and organize group activities such as book clubs.

3. **Events and Meetings**

    This platform enables the users and staff to host events and meetings. Library patrons can create events such as author talks, book signings, or book readings. They can invite others to attend, RSVP to events, and receive notifications about upcoming literary gatherings.

4. **Sign-In/Sign-out**

    Finally, library patrons can sign in to their accounts using their ID and password to access and participate in the above system functions fully.

## Example Interaction
```
Welcome to the Library Management System!

Select an Option:
  1. Add Book
  2. Register Patron
  3. Check out Book
  4. Return Book
  5. Main Menu

Enter your choice: 1

------------------------
        Add Book
------------------------

Enter ISBN: 123
Enter Title: Cool Space Facts
Enter Author: Neil Armstrong
Enter Genre: Historic

The book was added Successfully!
```
```
Welcome to the Literary Social Network!

Select an Option:
 1. User Profiles
 2. Groups and Discussions
 3. Events and Meetups
 4. Sign-In
 5. Main Menu

Enter your choice: 3

------------------------------------------------
              Events and Meetings
------------------------------------------------
No Events Found
------------------------------------------------
```

