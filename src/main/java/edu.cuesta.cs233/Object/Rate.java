class Rate {
    private double score;
    private String timestamp;
    Rate(double score) {
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