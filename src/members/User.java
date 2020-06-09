package members;

 public class User {
    private String name;
    private String address;
    private String userID;
    private String email;
    private String password;
    private String accountNumber;
    private long eWallet;
    protected int role;

    public User(){}

    public User(String userID, String password){
       this.userID = userID;
       this.password = password;
       this.role = Administrator.ADMIN;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setAddress(String address) { this.address = address; }

    public String getID() { return userID; }
    public void setID(String userID) { this.userID = userID; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountNumber() { return accountNumber; }

    public void editFunds(long funds) { this.eWallet += funds; }
    public long getFunds() { return eWallet; }

    public int getRole() { return role; }
}