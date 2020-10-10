import java.util.*;
import java.io.*;

public class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private int memberSerialNumber = 100000;

    // public void addBook(String bookFile, String serialNumber){
    //     if (books.containsKey(serialNumber)){
    //         System.out.println("Book already exists in system.");
    //     }
    //     Book newBook = Book.readBook(bookFile, serialNumber);
    //     if (newBook == null){
    //         ;
    //     }
        
    // }
    public void addMember(String name){
        Member newMember = new Member(name, Integer.toString(memberSerialNumber));
        memberSerialNumber++;
        members.put(newMember.getMemberNumber(), newMember);
    }
    //method for testing
    public Map<String, Book> returnBooks(){
        return this.books;
    }

    public void addCollection(String filename){
        List<Book> collection = Book.readBookCollection(filename);
        if (filename == null || collection == null){
            System.out.println("No such collection.");
            return;
        }
        int contained = 0;
        int added = 0;
        for (int h = 0; h < collection.size(); h++){
            if (!books.containsKey(collection.get(h).getSerialNumber())){
                books.put(collection.get(h).getSerialNumber(), collection.get(h));
                added++;
            }
            else{
                contained++;
                continue;
            }
        }
        if (contained == collection.size()){
            System.out.println("No books have been added to the system.");
            System.out.println();
            return;
        }
        else{
            System.out.println(added + " books successfuly added.");
            System.out.println();
            return;
        }

    }
    public void bookHistory(String serialNumber){
        if (!books.containsKey(serialNumber)){
            System.out.println("No such book in system.");
            System.out.println();
            return;
        }
        Book target = books.get(serialNumber);
        if (target.renterHistory().size() == 0){
            System.out.println("No rental history.");
            System.out.println();
        }
        else{
            List<Member> pastRenters = target.renterHistory();
            for (int f = 0; f < pastRenters.size(); f++){
                if (f == pastRenters.size() - 1){
                    System.out.println(pastRenters.get(f).getMemberNumber());
                    System.out.println();
                }
                else{
                    System.out.println(pastRenters.get(f).getMemberNumber());
                }
            }
        }
    }
    public void common(String[] memberNumbers){
        if (this.members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        }
        if (this.books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        int none = 0;
        for (int w = 0; w < memberNumbers.length; w++){
            if (!members.containsKey(memberNumbers[w])){
                none++;
            }
        }
        //check member existence
        if (none > 0){
            System.out.println("No such member in system.");
            System.out.println();
        }
        int dup = 0;    
        for (int f = 0; f < memberNumbers.length; f++){
            String current = memberNumbers[f];
        }

    }

    public static void main(String[] args){
        Library l = new Library();
        l.addMember("Hal Mary");
        l.addMember("Barry White");
        l.addMember("name");
        List<Book> readBook = Book.readBookCollection("sample.csv");
        l.addCollection("sample.csv");
        l.addCollection("sample1.csv");
        Book hp = l.returnBooks().get("111111");
        Member hal = new Member("Hal Mary", "100000");
        Member mike = new Member("Mike Barry", "100001");
        hp.rent(hal);
        hp.relinquish(hal);
        hp.rent(hal);
        hp.relinquish(hal);
        hp.rent(mike);
        hp.relinquish(mike);
        hp.rent(hal);
        l.bookHistory("111111");

    }
    //switch statements for main method   
}