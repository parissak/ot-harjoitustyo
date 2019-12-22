# Käyttöohje

Lataa viimeisin ![release](https://github.com/parissak/ot-harjoitustyo/releases/tag/viikko6).

## Ohjelman käynnistäminen

Ohjelma käynnistetään seuraavalla komennolla: java -jar Budjetointisovellus.jar

## Tunnuksen luominen ja kirjautuminen

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Kirjautuminen.PNG)

Sovellukseen luodaan tunnus kirjoittamalla nimimerkki alempaan tekstikenttään ja painamalla nappia “Create new user”. Mikäli nimimerkki on jo luotu, luominen ei onnistu samalla nimimerkillä. 

Sovellukseen kirjaudutaan syöttämällä nimimerkki ylempään tekstikenttään ja painamalla “Login”.

## Budjetin luominen ja poistaminen ja uloskirjautuminen

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Budjettinakyma.PNG)

Budjetit luodaan kirjoittamalla nimi tekstikenttään ja painamalla "Add budget". Tämän jälkeen nimimerkin luoma budjetti listautuu näkymään. Näkymässä listautuu myös budjettiin liittyvä saldo.

Haluttu budjetti poistetaan painamalla "Remove budget".

Budjetin erien lisäämisen ja poistamisen näkymään siirrytään napilla “Edit entries”.

Nappi “Log out” kirjaa nimimerkin ulos ohjelmasta ja ohjaa kirjautumissivulle.

## Budjettiin liittyvien erien luominen ja poistaminen

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Eranakyma.PNG)

Eriä lisätään budjettiin täyttämällä ensimmäiseen teksikenttään haluttu nimi ja toiseen erän määrä. Tulo ilmaistaan ilman etumerkkiä ja menoerä negatiivisella etumerkillä. Lopuksi painetaan "Add entry".

"Remove" poistaa vastaavan erän budjetista.

Kirjautumissivulle siirrytään painamalla "Go back".

## Ohjelmasta poistuminen

Ohjelmasta poistutaan painamalla ohjelman oikeasta yläreunassa olevaa “x” -merkkiä.

## Tietokannan poistaminen

Ohjelma luo automaattisesti sovelluksen juureen tietokantatiedoston “database.mv.db”. Mikäli sovellukseen syötetyt tiedot halutaan poistaa pysyvästi, tulee tietokanta poistaa. Sovellus luo uuden samannimisen tietokantatiedoston seuraavan käynnistyksen yhteydessä.
