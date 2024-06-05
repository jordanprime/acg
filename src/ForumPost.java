import java.util.Date;

// New ForumPost class
class ForumPost {
    private String username;
    private String message;
    private Date timestamp;

    public ForumPost(String username, String message) {
        this.username = username;
        this.message = message;
        this.timestamp = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + username + ": " + message;
    }
}