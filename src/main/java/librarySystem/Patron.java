package librarySystem;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    private int id;
    private String name;
    private String contactInfo;
    private List<Book> borrowingHistory;

    public Patron(int id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.borrowingHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<Book> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void addBookToHistory(Book book) {
        borrowingHistory.add(book);
    }
}

class StudentPatron extends Patron {
    private int studentId;

    public StudentPatron(int id, String name, String contactInfo, int studentId) {
        super(id, name, contactInfo);
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}

class FacultyPatron extends Patron {
    private int facultyId;

    public FacultyPatron(int id, String name, String contactInfo, int facultyId) {
        super(id, name, contactInfo);
        this.facultyId = facultyId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }
}