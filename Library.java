import java.util.*;

public class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private int memberSerialNumber = 100000;
    //Comparator as a class variable common to all objects (specifically the static methods)
    //Comparator that compares Book objects based off their serial numbers using a Book method reference
    private static Comparator<Book> bySerialNumber = Comparator.comparing(Book::getSerialNumber);

    public void addBook(String bookFile, String serialNumber){
        if (bookFile == null){
            System.out.println("No such file.");
            System.out.println();
            return;
        }
        if (books.containsKey(serialNumber)){
            System.out.println("Book already exists in system.");
            System.out.println();
        }
        Book newBook = Book.readBook(bookFile, serialNumber);
        if (newBook == null){
            return;
        }
        else{
            books.put(newBook.getGenre(), newBook);
            System.out.println("Successfully added " + newBook.shortString());
            System.out.println();
        }
        
    }
    public void addMember(String name){
        Member newMember = new Member(name, Integer.toString(memberSerialNumber));
        memberSerialNumber++;
        members.put(newMember.getMemberNumber(), newMember);
        System.out.println("Success.");
        System.out.println();
    }
    //method for testing
    public Map<String, Member> returnmem(){
        return this.members;
    }
    public Map<String, Book> returnbook(){
        return this.books;
    }
    public void rentBook(String memberNumber, String serialNumber){
        if (members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        }   
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        if (!members.containsKey(memberNumber)){
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }
        if (!books.containsKey(serialNumber)){
            System.out.println("No such book in system.");
            System.out.println();
            return;
        }
        Book target = this.books.get(serialNumber);
        Member renter = this.members.get(memberNumber);
        if (target.isRented()){
            System.out.println("Book is currently unavailable");
            System.out.println();
            return;
        }
        else{
            renter.rent(target);
            System.out.println("Success.");
            System.out.println();
            return;
        }
    }
    public void relinquishBook(String memberNumber, String serialNumber){
        if (members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        }   
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        if (!members.containsKey(memberNumber)){
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }
        if (!books.containsKey(serialNumber)){
            System.out.println("No such book in system.");
            System.out.println();
            return;
        }
        Book target = this.books.get(serialNumber);
        Member renter = this.members.get(memberNumber);
        if (!target.isRented()){
            System.out.println("Unable to return book.");
            System.out.println();
            return;
        }
        else{
            renter.relinquish(target);
            System.out.println("Success.");
            System.out.println();
            return;
        }
    }
    public void relinquishAll(String memberNumber){
        if (members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        } 
        if (!members.containsKey(memberNumber)){
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }
        else{
            Member renter = members.get(memberNumber);
            renter.relinquishAll();
            System.out.println("Success.");
            System.out.println();
            return;
        }
    }
    public void getMemberBooks(String memberNumber){
        Member target = members.get(memberNumber);
        if (members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        }
        else if (!members.containsKey(memberNumber)){
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }
        else{
            List<Book> renting = target.renting();
            if (renting.size() == 0){
                System.out.println("Member not currently renting.");
                System.out.println();
                return;
            }
            else{
                for (int x = 0; x < renting.size(); x++){
                    if (x == renting.size() - 1){
                        System.out.println(renting.get(x).shortString());
                        System.out.println();
                    }
                    else{
                        System.out.println(renting.get(x).shortString());
                    }
                }
            }
        }
    }
    public void getAllBooks(boolean fullString){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        if (fullString){
            for (Book b : books.values()){
                System.out.println(b.longString());
            }
        }
        else if (!fullString){
            for (Book b : books.values()){
                System.out.println(b.shortString());
            }
        }
        System.out.println();
    }
    public void getAuthors(){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        List<String> authors = new ArrayList<>();
        for (Book b : books.values()){
            authors.add(b.getAuthor());
        }
        //remove multiple occurences of author
        for (int p = 0; p < authors.size(); p++){
            int occur = 0;
            String target = authors.get(p);
            for (int o = 0; o < authors.size(); o++){
                if (target.equals(authors.get(o))){
                    occur++;
                }
            }
            if (occur > 1){
                while (occur > 1){
                    authors.remove(target);
                    occur -=1;
                }
            }
        }
        //sort alphabetically
        Collections.sort(authors);
        for (String s : authors){
            System.out.println(s);
        }
        System.out.println();
    }
    
    public void getAvailableBooks(boolean fullString){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        int unavailable = 0;
        List<Book> bList = new ArrayList<>();
        List<Book> bListAvail = new ArrayList<>();
        for (Book b : books.values()){
            bList.add(b);
        }
        for (int i = 0; i < bList.size(); i++){
            if (bList.get(i).isRented()){
                unavailable++;
            }
            else if (!bList.get(i).isRented()){
                bListAvail.add(bList.get(i));
            }
        }
        Collections.sort(bListAvail, bySerialNumber);
        if (unavailable == bList.size()){
            System.out.println("No books available.");
            System.out.println();
            return;
        }
        else{
            if (fullString){
                for (Book b : bListAvail){
                    System.out.println(b.longString());
                }

            }
            else if (!fullString){
                for (Book b: bListAvail){
                    System.out.println(b.shortString());
                }
            }

        }
        System.out.println();

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
    //broken
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
        //check member existence
        int none = 0;
        for (int w = 0; w < memberNumbers.length; w++){
            if (!members.containsKey(memberNumbers[w])){
                none++;
            }
        }
        //check for duplicate numbers
        int dup = 0;    
        for (int f = 0; f < memberNumbers.length; f++){
            int count = 0;
            String current = memberNumbers[f];
            for (int j = 0; j < memberNumbers.length; j++){
                if (memberNumbers[j].equals(current)){
                    count++;
                }
            }
            if (count > 1){
                dup++;
            }
        }
        if (none > 0){
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }

        else if (dup > 0){
            System.out.println("Duplicate members provided.");
            System.out.println();
            return;
        }

        List<Member> memberObj = new ArrayList<>();
        for (String s : memberNumbers){
            Member curr = members.get(s);
            memberObj.add(curr);
        }

        Member[] memberArray = new Member[memberObj.size()];
        memberArray = memberObj.toArray(memberArray);
        List<Book> common = Member.commonBooks(memberArray);
        if (common.size() == 0){
            System.out.println("No common books.");
            System.out.println();
            return;
        }
        else{
            for (int q = 0; q < common.size(); q++){
                if (q == common.size() - 1 ){
                    System.out.println(common.get(q).shortString());
                    System.out.println();
                }
                else{
                    System.out.println(common.get(q).shortString());
                }
            }
        }
    }


    public static void main(String[] args){
        Library l = new Library();
        l.addMember("Hal Mary");
        l.addMember("Marv Hall");
        l.addCollection("sample.csv");
        l.addCollection("sample1.csv");
        l.rentBook("100000", "111111");
        l.rentBook("100000", "111112");;
        l.relinquishAll("100000");
        l.rentBook("100001", "111111");
        l.relinquishBook("100001", "111111");
        l.bookHistory("999999");
        l.common(new String[]{"100000", "100001"});
        l.rentBook("100000", "111120");
        l.addBook("sample1.csv", "111128");
        l.getAllBooks(false);
        l.getAvailableBooks(false);
        //l.getAuthors();

        //String[] mem = new String[]{"100004", "100005"};
        //l.common(mem); broken atm

    }
    //switch statements for main method
    //print user prompt before hasNextLine() loop and at the end of each loop   
}