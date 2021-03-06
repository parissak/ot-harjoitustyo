# Budjetointisovellus
Sovelluksen avulla käyttäjän on mahdollista luoda haluamalleen asialle budjetti ja seurata budjetteja.

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md)

[Työaikakirjanpito](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/ty%C3%B6aikakirjanpito.md)

[Arkkitehtuuri](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testausdokumentti](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/testaudokumentti.md)



## Releaset
[Viikko 7](https://github.com/parissak/ot-harjoitustyo/releases/tag/Viikko7)

## Komentorivitoiminnot
### Testaus
* Testit suoritetaan komennolla: mvn test
* Testikattavuusraportti luodaan komennolla: mvn jacoco:report

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html.

### Suoritettavan jarin generointi

* Komento: mvn package

Komento generoi hakemistoon target suoritettavan jar-tiedoston Budjetointisovellus-1.0-SNAPSHOT.jar.

### JavaDoc

* Komento: mvn javadoc:javadoc

JavaDoc:ia voi tarkastella avaamalla selaimella tiedosto target/site/apidocs/index.html.


### Checkstyle

* Komento: mvn jxr:jxr checkstyle:checkstyle

Komento tekee tiedostoon checkstyle.xml:n määrittelemät tarkistukset. Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html.
