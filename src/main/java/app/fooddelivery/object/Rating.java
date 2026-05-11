package app.fooddelivery.object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Rating {
    private int score;
    private String timestamp;
    private UUID creatorId;

    protected Rating(UUID creatorId, int score, String timestamp) {
        this.score = score;
        this.creatorId = creatorId;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp = now.format(formatter);
    }

    protected Rating(UUID creatorId, int score) {
        this(creatorId, score, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public Rating(Customer from, int score) {
        this(from.getId(), score);
    }

    public int getScore() {
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