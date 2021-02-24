package com.miguel.app.lessontre.model;

public class Vote {
    private int score;
    private int pk;
    private int fk;

    public Vote(int score, int pk) {
        this.score = score;
        this.pk = pk;
        this.fk = 0;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return String.valueOf(score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getFk() {
        return fk;
    }

    public void setFk(int fk) {
        this.fk = fk;
    }
}
