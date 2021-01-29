package com.mikkomakela;

import java.sql.*;

public class OmaDBHandler {

    final String dburl; // En tiedä vielä tarviiko enää constructorin ulkopuolla
    private Connection conn;
    private Statement stmn;

    public OmaDBHandler(final String dburl) throws SQLException {
        // Luodaan tietokantayhteys. HOX! Jos tietokantaa ei ole vielä dburl:ssä, tämä luo uuden
        this.dburl = dburl;
        conn = DriverManager.getConnection(dburl);
        // Onnistui: printataan vähän tietoja
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("Database: " + meta.getURL() + ", Driver: " + meta.getDriverName()
        + ", Driverversion: " + meta.getDriverVersion());
        
        // Katsotaan löytyykö tietokannasta halutut taulut
        stmn = conn.createStatement();
       
        ResultSet rslt = stmn.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'user';");
        // Löytyikö taulu 'user'
        if (!rslt.next()) {
            System.out.println("Luodaan tietokantaan taulu user.");
            stmn.executeUpdate("CREATE TABLE user(username varchar(32) PRIMARY KEY, nickname varchar(32) NOT NULL UNIQUE);");
        }
        // Sama taululle 'message'
        rslt = stmn.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'message'");
        if (!rslt.next()) {
            System.out.println("Luodaan tietokantaan taulu message.");
            // HOX! SQLitessa lisätään automaattisesti inkrementoituva rowid, jota voidaan tässä käyttää pääavaimena
            stmn.executeUpdate("CREATE TABLE message(username varchar(32) REFERENCES user(username), nickname varchar(32), timestamp text, message text);");
        }
        stmn.close();
    }
    // Lisää käyttäjän username user-tauluun. Sama myös nicknameen
    public void insertUser(String username) throws SQLException {
        //Statement stmn = conn.createStatement();
        stmn.executeUpdate("INSERT INTO user(username, nickname) VALUES('" + username + "','" + username +"');");
        stmn.close();
    }
    // Lisää käyttäjän username user-tauluun nimimerkillä nickname
    public void insertUser(String username, String nickname) throws SQLException {
        //Statement stmn = conn.createStatement();
        stmn.executeUpdate("INSERT INTO user(username, nickname) VALUES('" + username + "','" + nickname + "');");
        stmn.close();
    }
    // Lisää viestin tietokantaan
    public void insertMessage(String username, String nickname, String message) throws SQLException {
        //Statement stmn = conn.createStatement();
        Timestamp tmsp = new Timestamp(System.currentTimeMillis());
        stmn.executeUpdate("INSERT INTO message(username, nickname, timestamp, message) VALUES('" + username +
            "','" + nickname + "','" + tmsp.toString() + "','" + message + "');");
    }
    public ResultSet getAllMessages() throws SQLException {
        //YStatement stmn = conn.createStatement();
        return stmn.executeQuery("SELECT * FROM message;");
    }
}
