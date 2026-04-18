package app.fooddelivery.object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UserRating {
    private double score;
    private String timestamp;
    private UUID creatorId;
    protected UserRating(UUID creatorId, double score, String timestamp) {
        this.score = score;
        this.creatorId = creatorId;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp = now.format(formatter);
    }
    protected UserRating(UUID creatorId, double score) {
        this(creatorId, score, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    protected UserRating(Customer from, double score) {
        this(from.getId(), score);
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

    public UUID getCreatorId() {
        return this.creatorId;
    }
}