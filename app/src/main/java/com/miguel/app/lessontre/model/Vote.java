package com.miguel.app.lessontre.model;

public class Vote {
    private int score;
    private int pk;
    private int fk;

    public Vote(int score, int pk, int fk) {
        this.score = score;
        this.pk = pk;
        this.fk = fk;
    }

    public int getScore() {
        return score;
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
