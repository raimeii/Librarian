import java.util.*;

public class Member {
    private String name;
    private String memberNumber;
    private List<Book> rentedHistory = new ArrayList<Book>();
    private List<Book> rentingNow = new ArrayList<Book>();

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

    //member's rent/relinquish uses Book's rent/relinquish logic
    public boolean rent(Book book){
        if (book == null || book.isRented()){
            return false;
        }
        else{
            book.rent(this);
            this.rentingNow.add(book);
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
            this.rentedHistory.add(book);
            this.rentingNow.remove(book);
            //required to return to Library
            return true;
        }
    }
    public List<Book> history(){
        return rentedHistory;
    }

    public List<Book> renting(){
        return rentingNow;
    }
    //incomplete
    public static List<Book> commonBooks(Member[] members){
        if (members == null){
            return null;
        }
        if (members.length == 1){
            return members[0].history();
        }
        
        else{
            //note: figure out how to only keep 1 copy of a book with the same
            List<Book> intersection = new ArrayList<>();
            //since we are looking for the intersection between lists, all other list must contain some/all elements of this reference.
            List<Book> reference = members[0].history(); 
            //loop through reference list
            for (int i = 0; i < reference.size(); i++){
                //start at 1 since reference is at 0
                int hits = 0;
                for (int j = 1; j < members.length; j++){
                    if (members[j].history().contains(reference.get(i))){
                        hits++;
                    }
                }
                if (hits == members.length - 1){
                    intersection.add(reference.get(i));
                }
            }
        
            //Comparator that compares Book objects based off their serial numbers using a Book method reference
            Comparator<Book> bySerialNumber = Comparator.comparing(Book::getSerialNumber);
            Collections.sort(intersection, bySerialNumber);
            return intersection;
        }
    }
    public void relinquishAll(){
        //returns all books
        //add to rented history, remove from currently renting
        for (int t = 0; t < rentingNow.size(); t++){
            rentedHistory.add(rentingNow.get(t));
            rentingNow.remove(t);
        }
        //return to library
    }
}