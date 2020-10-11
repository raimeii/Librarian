import java.util.*;
import java.io.*;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String serialNumber;
    private boolean rentStatus;
    private Member renter;
    private List<Member> renterHistory = new ArrayList<Member>();
    //Comparator as a class variable common to all objects (specifically the static methods)
    //Comparator that compares Book objects based off their serial numbers using a Book method reference
    private static Comparator<Book> bySerialNumber = Comparator.comparing(Book::getSerialNumber);

    public Book(String title, String author, String genre, String serialNumber){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.serialNumber = serialNumber;
    }   

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getGenre(){
        return genre;
    }
    public String getSerialNumber(){
        return serialNumber;
    }
    public List<Member> renterHistory(){
        return renterHistory;
    }
    public boolean rent(Member member){
        if(member == null || this.isRented()){
            return false;
        }
        else{
            this.renter = member;
            this.renterHistory.add(member);
            this.rentStatus = true;
            //remove from library
            return true;
        }

    }
    public boolean relinquish(Member member){
        if (member == null || this.getRenter() != member){
            return false;
        }
        else{
            this.renter = null;
            this.rentStatus = false;
            //return to library
            return true;
        }
    }

    public Member getRenter(){
        if (this.renter == null){
            return null;
        }
        return this.renter;
    }
    public boolean isRented(){
        if (this.renter != null){
            return true;
        }
        else{
            return false;
        }
    }
    public String longString(){
        String returnedString = "";
        if (this.rentStatus){
            returnedString  = serialNumber +  ": " + title + "(" + author + ", " + genre +") " + "\nRented by: " + this.renter.getMemberNumber() + ".";
        }
        else if (!this.rentStatus){
            returnedString  = serialNumber +  ": " + title + " (" + author + ", " + genre +") " + "\nCurrently available.";
        }
        return returnedString;
    }
    public String shortString(){
        String returnedString = title + " (" + author + ")";
        return returnedString;
    }

    public static List<Book> filterAuthor(List<Book> books, String author){
        if (books == null || author == null){
            return null;
        }
        List<Book> filtered = new ArrayList<Book>();
        for (int q = 0; q < books.size(); q++){
            if (books.get(q).getAuthor().equals(author)){
                filtered.add(books.get(q));
            }
        }
        Collections.sort(filtered, bySerialNumber);
        return filtered;

    }

    public static List<Book> filterGenre(List<Book> books, String genre){
        if (books == null || genre == null){
            return null;
        }
        List<Book> filtered = new ArrayList<Book>();
        for (int h = 0; h < books.size(); h++){
            if (books.get(h).getGenre().equals(genre)){
                filtered.add(books.get(h));
            }
        }
        Collections.sort(filtered, bySerialNumber);
        return filtered;
    }
    public static Book readBook(String filename, String serialNumber){
        try{
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            Map<String, Book> coll = new HashMap<>();
            while (scan.hasNextLine()){
                String[] currentLineArray = scan.nextLine().split(",");
                if (currentLineArray[0].equals("serialNumber")){
                    continue;
                }
                else{
                    Book currBook = new Book(currentLineArray[1], currentLineArray[2], currentLineArray[3], currentLineArray[0]);
                    coll.put(currBook.getSerialNumber(), currBook);
                }

            }
            if (!coll.containsKey(serialNumber)){
                System.out.println("No such book in file.");
                System.out.println();
                scan.close();
                return null;
            }
            else{
                scan.close();
                return coll.get(serialNumber);
            }

        }
        catch (FileNotFoundException e){
            System.out.println("No such file.");
            System.out.println();
            return null;
        }
    }
    public static List<Book> readBookCollection(String filename){
        List<Book> collection = new ArrayList<>();
        try{
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            List<String> serialNumbers = new ArrayList<>();
            //make a list of all the serial numbers within the collection
            while(scan.hasNextLine()){
                String[] currentLineArray = scan.nextLine().split(",");
                if (!currentLineArray[0].equals("serialNumber")){
                    serialNumbers.add(currentLineArray[0]);             
                }  
            }
            //make books using those serial numbers
            for (int y = 0; y < serialNumbers.size(); y++){
                Book newBook = Book.readBook(filename, serialNumbers.get(y));
                if (newBook == null){
                    scan.close();
                    return null;
                }
                else {
                    collection.add(newBook);
                }    
            }   
            scan.close();
            return collection;
        }
        catch (FileNotFoundException e){
            return null;
        }
    }
    //overwriting, not appending
    public static void saveBookCollection(String filename, Collection<Book> books){
        File f = new File(filename);
        if (filename == null){
            ;
        }
        else{
            try{
                FileWriter outputFile = new FileWriter(f);
                //write the header
                outputFile.write("serialNumber,title,author,genre\n");

                Book[] booksArray = new Book[books.size()]; 
                booksArray = books.toArray(booksArray);

                for (int l = 0; l < booksArray.length; l++){
                    Book target = booksArray[l];
                    String book = target.getSerialNumber() + "," + target.getTitle() + ","  + target.getAuthor() + "," + target.getGenre() + "\n";
                    outputFile.write(book);
                }
                outputFile.close();
            }
            catch (FileNotFoundException e){
                ;
            }
            catch (IOException e){
                ;
            }
        }
    }



    //member reliant: /isRented, /rent, /relinquish, /renterHistory, /long/shortString
    //static methods: /filterAuthor, /filterGenre, /readBook, /readBookCollection, /saveBookCollection
    public static void main(String[] args){
        Book hp = new Book("Harry Potter", "JK Rowling", "Fantasy", "11111111");
        Member hal = new Member("Hal Mary", "11112312");
        Member mike = new Member("Mike Barry", "1123124");
        Book ts  = new Book("asdasda", "qweqdasda", "asdqdwwdas", "11111110");
       // System.out.println(hp.renterHistory().toString());
        hal.rent(hp);
        //System.out.println(hp.getRenter().getName());
        //System.out.println(hp.renterHistory().toString());
        hal.relinquish(hp);
        //System.out.println(hp.isRented());
        mike.rent(hp);
        //System.out.println(hp.renterHistory().toString());
        //System.out.println(hp.getRenter().getName());
        mike.relinquish(hp);
        //System.out.println(hp.renterHistory().toString());
        hal.rent(hp);
        //boolean n = hp.getRenter().getName() == null;
        //System.out.println(n);
        hal.relinquish(hp);
        mike.rent(hp);
        mike.relinquish(hp);
        hal.rent(ts);
        hal.relinquish(ts);
        mike.rent(ts);
        //for (int i =0; i < hp.renterHistory().size(); i++){
            //System.out.println(hp.renterHistory().get(i).getName());
        //}
        System.out.println(mike.renting().get(0).getTitle());
        //System.out.println(mike.renting().toString());
        System.out.println(hal.renting().toString());
        //System.out.println(hal.renting().toString());
        //System.out.println(hp.renterHistory().toString());
        Member[] members = new Member[]{hal,mike};
        List<Book> xe = Member.commonBooks(members);
        for (int l = 0; l < xe.size(); l++){
            System.out.println(xe.get(l).getTitle());
        }
        System.out.println(hp.longString());
        System.out.println(ts.longString());
        List<Book> readBook = Book.readBookCollection("sample.csv");
        //System.out.println(readBook.getTitle());
        for (Book b : readBook){
            System.out.println(b.getTitle());
        }
        Book.saveBookCollection("sample1.csv", readBook);


    }

}   