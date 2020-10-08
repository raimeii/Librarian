import java.util.*;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String serialNumber;
    private boolean rentStatus;
    private Member renter;
    private List<Member> renterHistory = new ArrayList<Member>();
    //Comparator as a class variable
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



    //member reliant: /isRented, /rent, /relinquish, /renterHistory, /long/shortString
    //static methods: /filterAuthor, /filterGenre, readBook, readBookCollection, saveBookCollection
    public static void main(String[] args){
        Book hp = new Book("Harry Potter", "JK Rowling", "Fantasy", "11111111");
        Member hal = new Member("Hal Mary", "11112312");
        Member mike = new Member("Mike Barry", "1123124");
        Book ts  = new Book("asdasda", "qweqdasda", "asdqdwwdas", "11111110");
        System.out.println(hp.renterHistory().toString());
        hp.rent(hal);
        //System.out.println(hp.getRenter().getName());
        //System.out.println(hp.renterHistory().toString());
        hp.relinquish(hal);
        //System.out.println(hp.isRented());
        hp.rent(mike);
        //System.out.println(hp.renterHistory().toString());
        //System.out.println(hp.getRenter().getName());
        hp.relinquish(mike);
        //System.out.println(hp.renterHistory().toString());
        hp.rent(hal);
        //boolean n = hp.getRenter().getName() == null;
        //System.out.println(n);
        hp.relinquish(hal);
        mike.rent(hp);
        //mike.relinquish(hp);
        hal.rent(ts);
        hal.relinquish(ts);
        mike.rent(ts);
        mike.relinquish(ts);
        for (int i =0; i < hp.renterHistory().size(); i++){
            System.out.println(hp.renterHistory().get(i).getName());
        }
        System.out.println(mike.history().toString());
        System.out.println(mike.renting().toString());
        System.out.println(hal.history().toString());
        System.out.println(hal.renting().toString());
        System.out.println(hp.renterHistory().toString());
        Member[] members = new Member[]{hal,mike};
        List<Book> xe = Member.commonBooks(members);
        System.out.println(xe.toString());
        for (int l = 0; l < xe.size(); l++){
            System.out.println(xe.get(l).getTitle());
        }
        System.out.println(hp.longString());
        System.out.println(ts.longString());

    }

}