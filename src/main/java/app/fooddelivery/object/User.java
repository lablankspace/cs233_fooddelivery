package app.fooddelivery.object;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public abstract class User {
    private String name;
    private UUID id;
    private final byte[] passwordHash;

    public User(String name, String password) {
        this(name, UUID.randomUUID(), password);
    }

    protected User(String name, UUID id, String password) {
        this.name = name;
        this.id = id;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            passwordHash = digest.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected User(String name, UUID id, byte[] passwordHash) {
        this.name = name;
        this.id = id;
        this.passwordHash = passwordHash;
    }

    protected byte[] getPasswordHash() {
        return Arrays.copyOf(passwordHash, passwordHash.length);
    }

    protected String getPasswordHashHex() {
        StringBuilder hexBuilder = new StringBuilder(passwordHash.length * 2);
        for (byte b : passwordHash)
            hexBuilder.append(String.format("%02x", b));
        return hexBuilder.toString();
    }

    public boolean checkPassword(String inputPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Arrays.equals(digest.digest(inputPassword.getBytes()), passwordHash);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public abstract String getRole();

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        return id.equals(other.id);
    }
}