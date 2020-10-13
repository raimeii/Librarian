import java.util.*;

public class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private int memberSerialNumber = 100000;
    //Comparator as a class variable common to all objects (specifically the static methods)
    //Comparator that compares Book objects based off their serial numbers using a Book method reference
    private static Comparator<Book> bySerialNumber = Comparator.comparing(Book::getSerialNumber);
    private static Comparator<Book> byLexicographical = Comparator.comparing(Book::shortString);    

    public void addBook(String bookFile, String serialNumber){
        if (bookFile == null){
            System.out.println("No such file.");
            System.out.println();
            return;
        }
        if (books.containsKey(serialNumber)){
            System.out.println("Book already exists in system.");
            System.out.println();
            return;
        }
        Book newBook = Book.readBook(bookFile, serialNumber);
        if (newBook == null){
            return;
        }
        else{
            books.put(newBook.getSerialNumber(), newBook);
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
    public void getMember(String memberNumber){
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
            System.out.println(members.get(memberNumber).getMemberNumber() + ": " + members.get(memberNumber).getName());
            System.out.println();
        } 
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
        if (members.size() == 0){
            System.out.println("No members in system.");
            System.out.println();
            return;
        }
        else if (!members.containsKey(memberNumber)){
            System.out.println("Something's wrong...");
            System.out.println("No such member in system.");
            System.out.println();
            return;
        }
        else{
            Member target = members.get(memberNumber);
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
    public void memberRentalHistory(String memberNumber){
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
            Member target = members.get(memberNumber);
            List<Book> history = target.history();
            if (history.size() == 0){
                System.out.println("No rental history for member.");
                System.out.println();
                return;
            }
            else{
                for (int x = 0; x < history.size(); x++){
                    if (x == history.size() - 1){
                        System.out.println(history.get(x).shortString());
                        System.out.println();
                    }
                    else{
                        System.out.println(history.get(x).shortString());
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
    public void getBook(String serialNumber, boolean fullString){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        if (!books.containsKey(serialNumber)){
            System.out.println("No such book in system.");
            System.out.println();
            return;
        }
        if (fullString){
            System.out.println(books.get(serialNumber).longString());
            System.out.println();
        }
        else if (!fullString){
            System.out.println(books.get(serialNumber).shortString());
            System.out.println();
        }
    }
    
    public void getBooksByAuthor(String author){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        else{
            List<Book> bookList = new ArrayList<>();
            for (Book b : books.values()){
                bookList.add(b);
            }
            List<Book> byAuthor = Book.filterAuthor(bookList, author);
            if (byAuthor.size() == 0){
                System.out.println("No books by " + author + ".");
                System.out.println();
                return;
            }
            else{
                for (Book b : byAuthor){
                    System.out.println(b.shortString());
                }
            }
            System.out.println();
        }
    }
    public void getBooksByGenre(String genre){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        else{
            List<Book> bookList = new ArrayList<>();
            for (Book b : books.values()){
                bookList.add(b);
            }
            List<Book> byGenre = Book.filterGenre(bookList, genre);
            if (byGenre.size() == 0){
                System.out.println("No books with genre " + genre + ".");
                System.out.println();
                return;
            }
            else{
                for (Book b :byGenre){
                    System.out.println(b.shortString());
                }
            }
            System.out.println();
        }
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
    public void getCopies(){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        else{
            List<Book> allBooks = new ArrayList<>();
            List<Book> seenBooks = new ArrayList<>();
            List<String> seenShortString = new ArrayList<>();
            for (Book b : books.values()){
                allBooks.add(b);
            }
            Collections.sort(allBooks, byLexicographical);
            for (int i = 0; i < allBooks.size(); i++){
                Book target = allBooks.get(i);
                int count = 0;
                //if book and shortString has not been encountered yet, count all occurences of the shortString from different Books in the list.
                if (!seenBooks.contains(target) && !seenShortString.contains(target.shortString())){
                    for (int k = 0; k < allBooks.size(); k++){
                        if (target.shortString().equals(allBooks.get(k).shortString())){
                            count++;
                        }
                    }
                    //add the book and the shortstring to seen lists
                    System.out.println(target.shortString() + ": " + count);
                    seenBooks.add(target);
                    seenShortString.add(target.shortString());
                }
                //for the case of a different Book object with the same shortString, continue, as the above if statement already accounts for different objects with the same shortString
                else if (!seenBooks.contains(target) && seenShortString.contains(target.shortString())){
                    continue;
                }
            }
            System.out.println();
        }
    }
    public void getGenres(){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        List<String> genres = new ArrayList<>();
        for (Book b : books.values()){
            genres.add(b.getGenre());
        }
        //remove multiple occurences of author
        for (int p = 0; p < genres.size(); p++){
            int occur = 0;
            String target = genres.get(p);
            for (int o = 0; o < genres.size(); o++){
                if (target.equals(genres.get(o))){
                    occur++;
                }
            }
            if (occur > 1){
                while (occur > 1){
                    genres.remove(target);
                    occur -=1;
                }
            }
        }
        //sort alphabetically
        Collections.sort(genres);
        for (String s : genres){
            System.out.println(s);
        }
        System.out.println();
    }

    public void addCollection(String filename){
        List<Book> collection = Book.readBookCollection(filename);
        if (filename == null || collection == null){
            System.out.println("No such collection.");
            System.out.println();
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
    public void saveCollection(String filename){
        if (books.size() == 0){
            System.out.println("No books in system.");
            System.out.println();
            return;
        }
        else{
            Collection<Book> libBooks = new ArrayList<>();
            for (Book book : books.values()){
                libBooks.add(book);
            }
            Book.saveBookCollection(filename, libBooks);
            System.out.println("Success.");
            System.out.println();
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
            return;
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
    public void run(){
        Scanner input = new Scanner(System.in);
        System.out.print("user: ");
        try{
            while(input.hasNextLine()){
                String command = input.nextLine();
                //no splits (LIST, NUM OF COPIES, LIST GENRES, LIST AUTHORS, END, COMMANDS)
                if (command.toLowerCase().equals("exit")){
                    return;
                }
                else if (command.toLowerCase().equals("list all long")){
                    this.getAllBooks(true);
                }
                else if (command.toLowerCase().equals("list all short")){
                    this.getAllBooks(false);
                }
                else if (command.toLowerCase().equals("list available long")){
                    this.getAvailableBooks(true);
                }
                else if (command.toLowerCase().equals("list available short")){
                    this.getAvailableBooks(false);
                }
                else if (command.toLowerCase().equals("number copies")){
                    this.getCopies();
                }
                else if (command.toLowerCase().toLowerCase().equals("list genres")){
                    this.getGenres();
                }
                else if (command.toLowerCase().equals("list authors")){
                    this.getAuthors();
                }
                // splits limited by 2 (GENRE, AUTHOR)
                else if (command.toLowerCase().split(" ", 2)[0].equals("genre")){
                    this.getBooksByGenre(command.split(" ", 2)[1]);
                }
                else if (command.toLowerCase().split(" ", 2)[0].equals("author")){
                    this.getBooksByAuthor(command.split(" ", 2)[1]);
                }
                //splits limited to 3 (BOOK)
                else if (command.toLowerCase().split(" ", 3)[0].equals("book")){
                    if (command.toLowerCase().split(" ", 3)[2].equals("long")){
                        this.getBook(command.split(" ", 3)[1], true);
                    }
                    else if (command.toLowerCase().split(" ", 3)[2].equals("short")){
                        this.getBook(command.split(" ", 3)[1], false);
                    }
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("book") && command.toLowerCase().split(" ", 3)[1].equals("history")){
                    // broken
                    this.bookHistory(command.split(" ", 3)[2]);
                }
                //splits limited to 2,3,3 (MEMBER)
                else if (command.toLowerCase().split(" ", 2)[0].equals("member")){
                    this.getMember(command.split(" ", 2)[1]);
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("member") && (command.toLowerCase().split(" ", 3)[1].equals("books"))){
                    //broke
                    this.getMemberBooks(command.split(" ")[2]);
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("member") && (command.toLowerCase().split(" ", 3)[1].equals("history"))){
                    //broke
                    this.memberRentalHistory(command.split(" ", 3)[2]);
                }
                //splits of 3 (RENT/RELINQUISH)
                else if (command.toLowerCase().split(" ", 3)[0].equals("rent")){
                    this.rentBook(command.split(" ", 3)[1], command.split(" ", 3)[2]);
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("relinquish")){
                    this.relinquishBook(command.split(" ", 3)[1], command.split(" ", 3)[2]);
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("relinquish") && command.toLowerCase().split(" ", 3)[1].equals("all")){
                    //broken
                    this.relinquishAll(command.split(" ", 3)[2]);
                }
                //splits of 3,4 (ADD)
                else if (command.toLowerCase().split(" ", 3)[0].equals("add") && command.toLowerCase().split(" ", 3)[1].equals("member")){
                    this.addMember(command.split(" ", 3)[2]);
                }
                else if (command.toLowerCase().split(" ", 4)[0].equals("add") && command.toLowerCase().split(" ", 4)[1].equals("book")){
                    this.addBook(command.split(" ", 3)[2], command.split(" ", 3)[3]);
                }
                //splits of 3 (COLLECTION)
                else if (command.toLowerCase().split(" ", 3)[0].equals("add") && command.toLowerCase().split(" ", 3)[1].equals("collection")){
                    this.addCollection(command.split(" ", 3)[2]);
                }
                else if (command.toLowerCase().split(" ", 3)[0].equals("save") && command.toLowerCase().split(" ", 3)[1].equals("collection")){
                    this.saveCollection(command.split(" ", 3)[2]);
                }

                System.out.print("user: ");
            }
        }
        catch (Exception e){
            ;
        }
    }
    


    public static void main(String[] args){
        Library l = new Library();
        // l.addMember("Joe Sullivan");
        // l.addCollection("sample3.csv");
        // l.rentBook("100000", "111111");
        // l.rentBook("100000", "111112");
        // l.getMemberBooks("100000");
        // l.relinquishAll("100000");
        // l.getMemberBooks("100000");
        // l.getMemberBooks("100500");
        l.run();

        //String[] mem = new String[]{"100004", "100005"};
        //l.common(mem); broken atm

    }
    //switch statements for main method
    //print user prompt before hasNextLine() loop and at the end of each loop   
}