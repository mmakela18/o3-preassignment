# O3_ChatServer
## Ohjelmointi 3 esitehtävä

Ei mitään kaunista katseltavaa, mutta täyttää kait esitehtävän vaatimukset.
Sonarlint huutaa ja poikkeustenkäsittely on vielä hakusessa, mutta pitäisi sentään kääntyä. :D

## Miten käännän ja suoritan?
rakenna.bat sisältää komennot, joilla tämä kasa nykyisellään rakentuu Windowsilla.
Linux/Mac pitänee vain muuttaa polku toiselta riviltä, tallentaa .sh ja antaa suoritusoikeus chmod +x. Asia vielä testaamatta.
juokse.bat sisältää komennon, jolla lopputuotoksen voi suorittaa Windowsilla. Varmaan sama muutos Linux/Mac kuin ylempänä.
Päätin sisällyttää nämä filut, koska pelkään niiden yhä muuttuvan homman edetessä.

## Mitä ohjelma tekee?
Ensimmäisellä pyöräytyksellä, tai jos ohjelman tietokanta on poistettu, luodaan käypä tietokanta nimellä csdb.db. Kanta sisältää kaksi taulukkoa: 'user' käyttäjätiedoille ja 'message' viesteille.
Käyttäjänimen voi valita vapaasti, kunhan se ei ole jo tietokannassa. Toistaiseksi käyttäjänimi toimii myös nimimerkkinä.
Käyttäjänimen valinnan jälkeen voi kirjoittaa vapaasti viestejä tietokantaan. Mutta ainoa tapa hakea viestejä kannasta on hakea ne kaikki.