import java.util.*;

public class Member {
    private String name;
    private String memberNumber;
    private List<Book> rentedHistory = new ArrayList<Book>();
    private List<Book> rentingHistory = new ArrayList<Book>();

    public Member(String name, String memberNumber){
        this.name = name;
        this.memberNumber = memberNumber;
    }
    public String getName(){
        return name;
    }
    public String getMemberNumber(){
        return memberNumber;
    }
    public boolean rent(Book book){
        if (book == null || book.isRented()){
            return false;
        }
        else{
            book.rent(this);
            rentingHistory.add(book);
            //required to take from Library
            return true;
        }

    }
    public boolean relinquish(Book book){
        if (book == null || book.getRenter() != this){
            return false;
        }
        else{
            book.relinquish(this);
            //required to return to Library
            return true;
        }
    }
    public List<Book> history(){
        return rentedHistory;
    }

    public List<Book> renting(){
        return rentingHistory;
    }
    //incomplete
    public static List<Book> commonBooks(Member[] members){
        if (members == null){
            return null;
        }
        List<Book> intersection = new ArrayList<>();
        return intersection;
    }
}