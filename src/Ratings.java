import java.util.LinkedList;
import java.util.Queue;

public class Ratings {
    private static final int MAX_SIZE = 10;
    private Queuue<Integer> ratings;
    private int sum;
    public Ratings() {
        ratings = new LinkedList<>();
        sum = 0;
    }
    public void addRating(int newRating){
        if (newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("Rating between 1 and 5");
        }
        if(ratings.size() == MAX_SIZE) {
            int oldest = ratings.poll();
            sum -= oldest;
        }
        ratings.offer(newRating);
    }
    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return (double) sum / ratings.size();
    }
    public Queue<Integer> getRatings() {
        return new LinkedList<>(ratings);
    }
}
