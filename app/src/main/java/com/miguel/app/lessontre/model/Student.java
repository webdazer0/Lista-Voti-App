package com.miguel.app.lessontre.model;

public class Student {

    private String nome;
    private String cognome;
    private double media;

    private int id = 77;

    public Student() {}


    public Student(String nome, String cognome, double media, int id) {
        setNome(nome);
        setCognome(cognome);
        setMedia(media);
        setId(id);
    }

    public Student setNome(String n) {
        nome = n;
        return this;
    }

    public Student setCognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public Student setMedia(double media) {
        this.media = media;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public double getMedia() {
        return media;
    }

    public String getNominativo() {
        return getNome() + " " + getCognome();
    }

    public String getMediaString() {
        return String.valueOf(getMedia());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
