package com.mikkomakela;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Ask preferred username before writing messages
 * Read user input in a console
 * Return previous user input when requested
 * Format following: (timestamp_of_msg) <Username> user_message
 */
public class ChatServer 
{
    // Tietokannan sijainti
    static final String DBURL = "jdbc:sqlite:csdb.db";
    // Viestittelijän käyttäjänimi
    static String username;
    // Olio tietokannan käsittelyyn
    static OmaDBHandler dbHandler;
    // Olio syötteelle
    static Scanner skanneri;
    public static void main( String[] args )
    {
        // Avataan tietokantayhteys
        try {
            dbHandler = new OmaDBHandler(DBURL);
        }
        catch (SQLException e) {
            System.out.println("Tietokantayhteyttä ei saatu.");
            System.out.println(e.getMessage());
            // Voidaan yhtä hyvin lähteä lipettiin.
            System.exit(-1);
        }
        // Skanneri valmiiksi
        skanneri = new Scanner(System.in);
        skanneri.useDelimiter(System.lineSeparator());
        // Vaatimus #1: käyttäjänimen kysyminen ennen viestien kirjoittamista
        username = loginFromConsole();
        // Vaatimus #2: lue käyttäjän viestejä
        // Luetaan kunnes saadaan poistumissignaalina false
        while (Boolean.TRUE.equals(readFromConsole()));
        skanneri.close();
    }
    // Kysyy käyttäjänimeä kunnes validi löytyy, ja palauttaa sen
    private static String loginFromConsole() {
        Boolean madeit = false; // Onnistuiko käyttäjänimen asetus
        String username = null;
        // Painakoot Ctrl + C jos haluavat veks :D
        while (Boolean.FALSE.equals(madeit)) {
            System.out.print("Anna toivomasi käyttäjänimi (max 32 merkkiä): ");
            // Luetaan ja trimmataan käyttäjänimi
            username = skanneri.next();
            username = username.trim();
            if (username.length() > 32) {
                System.out.println("Liian pitkä nimi.");
            } else { // Yritetään lisätä tietokantaan.
                try {
                    dbHandler.insertUser(username);
                    madeit = true;
                }
                catch (SQLException e) {
                    // virhekoodi 19 -> UNIQUE constraint failed
                    if (e.getErrorCode() == 19) {
                        System.out.println("Käyttäjänimi on jo käytössä.");
                    } else { // Muutoin joku muu pielessä.
                        System.out.println(e.getMessage() + ", ERRCODE: " + e.getErrorCode());
                    }
                }
            }
        }
        return username;
    }
    // Lukee rivejä konsolista ja yrittää parseta ja tulkita niitä
    // "/" alkuiset tulkitaan komennoiksi
    private static Boolean readFromConsole() {
        // Annetaan ensin käyttöohjeet
        System.out.println("Voit nyt lähettää viestejä. \"/\" alkuiset tulkitaan komennoiksi.");
        System.out.print("Listan komennoista saat komennolla /help.\n > ");
        // Luetaan rivi
        String rivi = skanneri.next();
        // Ylimääräinen whitespace veks
        rivi = rivi.trim();
        // Onko komento?
        if (rivi.charAt(0) == '/') {
            // Jos täältä tulee false (/exit tai /quit), mainin silmukka loppuu.
            return interpretCommand(rivi);
        } else {
          // Ei ollut komento -> tulkitaan viestiksi.
          try {
              dbHandler.insertMessage(username, username, rivi);
          }
          catch (SQLException e) {
              System.out.println("Viestin " + rivi + " lisäys nimellä " + username + " epäonnistui.");
              System.out.println(e.getMessage() + ", ERRCODE: " + e.getErrorCode());
          }
        }
        return true;
    }
    // Palauttaa true, jos /quit tai /exit.
    // /help tulostaa komennot. /dump_messages tulostaa kaikki viestit
    private static Boolean interpretCommand(String rivi) {
        switch (rivi) {
            case "/help":
                printHelp();
                break;
            case "/exit":
                return false;
            case "/quit":
                return false;
            case "/dump_messages":
                dumpMessageTable();
                break;
            default:
                System.out.println("Tunnistamaton komento.n/help kertoo saatavilla olevat komennot.");
        }
        return true;
    }
    // Printtaa saatavilla olevat komennot
    private static void printHelp() {
        System.out.println("/exit > poistu ohjelmasta\n/quit > poistu ohjelmasta\n/dump_messages >"+
        " hae viestit tietokannasta\n/help > näytä tämä viesti");
    }
    // Vaatimus #3: Dumppaa message-taulukon tietokannasta konsoliin
    // Taulukon sarakkeet: (1) username, (2) nickname, (3) timestamp, (4) message
    private static void dumpMessageTable() {
        try {
            ResultSet messages = dbHandler.getAllMessages();
            // Vaatimus #4: Format following: (timestamp_of_msg) <Username> user_message
            while (messages.next()) {
                System.out.println("(" + messages.getString(3) + ") <" + messages.getString(1)
                + "> " + messages.getString(4));
        }
        }
        catch (SQLException e) {
            System.out.println("Viestien haku epäonnistui.");
            System.out.println(e.getMessage() + " ERRCODE: " + e.getErrorCode());
        }
    }
}
