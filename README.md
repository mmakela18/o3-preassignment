# O3_ChatServer

Mikko Mäkelä
2581271
mmakela18@student.oulu.fi

## Ohjelmointi 3 esitehtävä

Ei mitään kaunista katseltavaa, mutta täyttää kait esitehtävän vaatimukset.
Sonarlint huutaa ja poikkeustenkäsittely on vielä hakusessa, mutta pitäisi sentään kääntyä. :D

## Miten käännän ja suoritan?
Slackistä löytyikin ratkaisu "mvn package" ongelmaan. Miksei aiemmin siellä käynyt? Päivitetty rakenna.bat (vanha vaati "mvn clean compile assembly:single").
Käännön pitäisi siis windowsilla sujua ihan vaan "mvn package" ja suoritus "java -jar target\chatserver-1.0-SNAPSHOT-jar-with-dependencies.jar". Linux/mac varmaan samoin, mutta polku erilain.

## Mitä ohjelma tekee?
Ensimmäisellä pyöräytyksellä, tai jos ohjelman tietokanta on poistettu, luodaan käypä tietokanta nimellä csdb.db. Kanta sisältää kaksi taulukkoa: 'user' käyttäjätiedoille ja 'message' viesteille.
Käyttäjänimen voi valita vapaasti, kunhan se ei ole jo tietokannassa. Toistaiseksi käyttäjänimi toimii myös nimimerkkinä.
Käyttäjänimen valinnan jälkeen voi kirjoittaa vapaasti viestejä tietokantaan. Mutta ainoa tapa hakea viestejä kannasta on hakea ne kaikki.