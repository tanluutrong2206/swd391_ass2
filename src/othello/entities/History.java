package othello.entities;

import java.util.Date;

public class History {
    private Account winAcc;
    private Account loseAcc;
    private Date playedDate;

    public History(Account winAcc, Account loseAcc, Date playedDate) {
        this.winAcc = winAcc;
        this.loseAcc = loseAcc;
        this.playedDate = playedDate;
    }

    public Date getPlayedDate() {
        return playedDate;
    }

    public void setPlayedDate(Date playedDate) {
        this.playedDate = playedDate;
    }

    public Account getWinAcc() {
        return winAcc;
    }

    public void setWinAcc(Account winAcc) {
        this.winAcc = winAcc;
    }

    public Account getLoseAcc() {
        return loseAcc;
    }

    public void setLoseAcc(Account loseAcc) {
        this.loseAcc = loseAcc;
    }
}
