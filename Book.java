import java.util.*;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String serialNumber;
    private boolean rentStatus;
    private Member renter;
    private List<Member> renterHistory = new ArrayList<Member>();

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
            this.renterHistory.add(member);
            this.renter = member;
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
            //return to library
            return true;
        }
    }

    public Member getRenter(){
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
    //member reliant: isRented, rent, relinquish, renterHistory, long/shortString
    //static methods: filterAuthor, filterGenre, readBook, readBookCollection, saveBookCollection
    public static void main(String[] args){
        Book hp = new Book("Harry Potter", "JK Rowling", "Fantasy", "11111111");
        Member hal = new Member("Hal Mary", "11112312");
        Member mike = new Member("Mike Barry", "1123124");
        hp.rent(hal);
        System.out.println(hp.getRenter().getName());
        System.out.println(hp.renterHistory().toString());
        hp.relinquish(hal);
        System.out.println(hp.isRented());
        hp.rent(mike);
        System.out.println(hp.renterHistory().toString());
        System.out.println(hp.getRenter().getName());
    }

}