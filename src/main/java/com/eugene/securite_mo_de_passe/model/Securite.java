package com.eugene.securite_mo_de_passe.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for hash word cracker
 *
 * @author ETOUNDI II Eugene Sebastien Cedric P.
 */
public class Securite {

    private final StringProperty hash;
    private final StringProperty pwd;

    /**
     * Default constructor.
     */
    public Securite() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param hash
     * @param pwd
     */
    public Securite(String hash, String pwd) {
        this.hash = new SimpleStringProperty(hash);
        this.pwd = new SimpleStringProperty(pwd);
    }

    public String getHash() {
        return hash.get();
    }

    public StringProperty hashProperty() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash.set(hash);
    }

    public String getPwd() {
        return pwd.get();
    }

    public StringProperty pwdProperty() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd.set(pwd);
    }
}