package com.example.missions_epoka;

public class Mission {
    private int misNoSalarie;
    private int misNoVille;
    private String misDateDebut;
    private String misDateFin;
    private boolean misValidee;
    private boolean misRemboursee;

    public Mission(int misNoSalarie, int misNoVille, String misDateDebut, String misDateFin) {
        this.misNoSalarie = misNoSalarie;
        this.misNoVille = misNoVille;
        this.misDateDebut = misDateDebut;
        this.misDateFin = misDateFin;
        this.misValidee = false;
        this.misRemboursee = false;
    }

    public int getMisNoSalarie() {
        return misNoSalarie;
    }

    public void setMisNoSalarie(int misNoSalarie) {
        this.misNoSalarie = misNoSalarie;
    }

    public int getMisNoVille() {
        return misNoVille;
    }

    public void setMisNoVille(int misNoVille) {
        this.misNoVille = misNoVille;
    }

    public String getMisDateDebut() {
        return misDateDebut;
    }

    public void setMisDateDebut(String misDateDebut) {
        this.misDateDebut = misDateDebut;
    }

    public String getMisDateFin() {
        return misDateFin;
    }

    public void setMisDateFin(String misDateFin) {
        this.misDateFin = misDateFin;
    }

    public boolean isMisValidee() {
        return misValidee;
    }

    public void setMisValidee(boolean misValidee) {
        this.misValidee = misValidee;
    }

    public boolean isMisRemboursee() {
        return misRemboursee;
    }

    public void setMisRemboursee(boolean misRemboursee) {
        this.misRemboursee = misRemboursee;
    }
}
