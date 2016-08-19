package com.capitalbio.oemv2.myapplication.Bean;

/**
 * Created by wxy on 16-2-29.
 */
public class Questionnaire {

    String familyHistoryDieases;
    String personalHistoryDieases;
    String personalNowDieases;
    HbloodPresureFator fator_hypertension;
    StrokeFator fator_stroke;
    DiabeteFactor fator_diabete;
    CHDFator fator_CHD;
    ObesityFator fator_obsity;
    CancerFator cancer;

    public CancerFator getCancer() {
        return cancer;
    }

    public void setCancer(CancerFator cancer) {
        this.cancer = cancer;
    }

    public ObesityFator getFator_obsity() {
        return fator_obsity;
    }

    public void setFator_obsity(ObesityFator fator_obsity) {
        this.fator_obsity = fator_obsity;
    }

    public String getFamilyHistoryDieases() {
        return familyHistoryDieases;
    }

    public void setFamilyHistoryDieases(String familyHistoryDieases) {
        this.familyHistoryDieases = familyHistoryDieases;
    }

    public CHDFator getFator_CHD() {
        return fator_CHD;
    }

    public void setFator_CHD(CHDFator fator_CHD) {
        this.fator_CHD = fator_CHD;
    }

    public DiabeteFactor getFator_diabete() {
        return fator_diabete;
    }

    public void setFator_diabete(DiabeteFactor fator_diabete) {
        this.fator_diabete = fator_diabete;
    }

    public HbloodPresureFator getFator_hypertension() {
        return fator_hypertension;
    }

    public void setFator_hypertension(HbloodPresureFator fator_hypertension) {
        this.fator_hypertension = fator_hypertension;
    }

    public StrokeFator getFator_stroke() {
        return fator_stroke;
    }

    public void setFator_stroke(StrokeFator fator_stroke) {
        this.fator_stroke = fator_stroke;
    }

    public String getPersonalHistoryDieases() {
        return personalHistoryDieases;
    }

    public void setPersonalHistoryDieases(String personalHistoryDieases) {
        this.personalHistoryDieases = personalHistoryDieases;
    }

    public String getPersonalNowDieases() {
        return personalNowDieases;
    }

    public void setPersonalNowDieases(String personalNowDieases) {
        this.personalNowDieases = personalNowDieases;
    }
}
