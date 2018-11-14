package othello.entities;

public class Account {
    private String username;
    private String password;
    private int id;
    private int wonGames;
    private double winRate;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account() {
    }

    public Account(int id, String username) {
        this.username = username;
        this.id = id;
    }

    public Account(String username, int wonGames) {
        this.username = username;
        this.wonGames = wonGames;
    }

    public Account(String username, double winRate) {
        this.username = username;
        this.winRate = winRate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }
}
