package app.fooddelivery.object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserRating {
    private double score;
    private String timestamp;
    private User user;
    private Driver driver;
    UserRating(User from, Driver to, double score) {
        this.score = score;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp = now.format(formatter);
    }
    public double getScore() {
        return score;
    }
    public String getTimeStamp() {
        return timestamp;
    }
    @Override
    public String toString() {
        return ("Score: " + score + "::" + "Time: " + timestamp);
    }
}