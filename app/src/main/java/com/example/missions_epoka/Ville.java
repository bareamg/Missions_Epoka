package com.example.missions_epoka;

public class Ville {
    private int id;
    private String nom;
    private String codePostal;

    public Ville(int id, String nom, String codePostal) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getCodePostal() { return codePostal; }

    @Override
    public String toString() {
        return nom + " " + codePostal;
    }
}
