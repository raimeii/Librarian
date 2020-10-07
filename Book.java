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
            this.renter = member;
            this.renterHistory.add(member);
            member.updateMemberRentingHistory(this);
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
            member.removeMemberRentingHistory(this);
            member.updateMemberRentedHistory(this);
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
    //new method for Member to use to update a book's renter history
    public void updateBookRenterHistory(Member member){
        this.renterHistory().add(member);
    }
    //member reliant: isRented, rent, relinquish, renterHistory, long/shortString
    //static methods: filterAuthor, filterGenre, readBook, readBookCollection, saveBookCollection
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
        mike.relinquish(hp);
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

    }

}