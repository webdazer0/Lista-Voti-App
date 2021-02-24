package com.miguel.app.lessontre.model;

public class Student {

    private String nome;
    private String cognome;
    private int eta;

    private int id = 77;

    public Student() {}


    public Student(String nome, String cognome, int eta) {
        setNome(nome);
        setCognome(cognome);
        setEta(eta);
    }

    public Student setNome(String n) {
        nome = n;
        return this;
    }

    public Student setCognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public Student setEta(int eta) {
        this.eta = eta;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEta() {
        return eta;
    }

    public String getNominativo() {
        return getNome() + " " + getCognome();
    }

    public String getEtaString() {
        return String.valueOf(getEta());
    }

    public int getId() {
        return id;
    }
}
