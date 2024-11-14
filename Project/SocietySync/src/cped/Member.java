package cped;
public class Member {
    private int id;
    private String name;
    private String email;
    private String category;
    private String joiningDate;

    public Member(String name, String email, String category, String joiningDate) {
        this.name = name;
        this.email = email;
        this.category = category;
        this.joiningDate = joiningDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCategory() { return category; }
    public String getJoiningDate() { return joiningDate; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCategory(String category) { this.category = category; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }
}
