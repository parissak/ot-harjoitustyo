# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria. Koodin pakkausrakenne on seuraava:

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rakenne.png)


## Käyttöliittymä

Käyttöliittymä on pyritty eristämään sovelluslogiikasta ja se kutsuu sopivin parametrein sovelluslogiikan metodeja. Se sisältää kolme erillistä näkymää: kirjautumiseen, budjetteihin ja budjettien eriin liittyvät näkymät. Budjetteihin ja budjettien eriin liittyvät listaukset voivat vaihtua käyttäjän poistaessa tai lisätessä niitä. Näkymän päivityksestä vastaavat erilliset metodit, jotka päivittävät tiedon sovelluslogiikalta saamansa tiedon perusteella.


## Sovelluslogiikka
Sovelluksen loogisen datamallin muodostavat luokat User, Budget ja Transaction, mitkä kuvaavat käyttäjiä, budjetteja ja niiden eriä. Niiden välisiä suhteita voi kuvata seuraavasti: käyttäjällä voi olla useita budjetteja ja budjetilla vastaavasti useita eriä, mutta yksittäinen budjetti ei voi kuulua usealla käyttäjälle eikä yksittäinen erä voi sisältyä useaan eri budjettiin.

BudgetService -olio toimii edellä mainittujen luokkien välissä koordinoiden toimintaa. Luokka tarjoaa kaikille käyttöliittymän toiminnoille oman metodin. Näitä ovat budjettien ja erien lisääminen, poistaminen ja listaaminen ja käyttäjien lisääminen. Käsittely tapahtuu eri rajapintojen kautta. BudgetServicen ja ohjelman muiden osien suhdetta kuvaa seuraava luokkakaavio:

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.png)


## Tietojen pysyväistallennus
Luokat DBUserDao, DBBudgetDao ja DBTransactionDao huolehtivat tietojen tallentamisesta tietokantaan ja sieltä hakemisesta. 

Sovelluksen käynnistyksen yhteydessä sen yhteyteen muodostuu tietokantatiedosto "database.db", joka sisältää siis sovelluksen muokkaamat tiedot. Sovellus luo aina uuden tietokantatiedoston jos sellaista ei ole valmiina.


## Päätoiminnallisuudet

### Käyttäjän kirjautuminen 
![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kirjautuminen.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä ohjaa tarkistamaan syötteet ja niiden ollessa oikeanlaiset kutsuu sovelluslogiikan metodia "login" antaen parametriksi kirjautuneen käyttäjätunnuksen. Sovelluslogiikka selvittää userDao:n avulla onko käyttäjätunnus olemassa. Jos on, niin käyttöliittymä vaihtaa näkymäksi käyttäjän budjetteihin liittyvän näkymän.

### Budjetin luominen
![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Budjetin_luominen.png)


Painikkeen painamiseen reagoiva tapahtumankäsittelijä ohjaa tarkistamaan syötteet ja niiden ollessa oikeanlaiset kutsuu sovelluslogiikan metodia "createUser" antaen parametriksi syötetyn nimen. Sovelluslogiikka luo uuden budjetti-olion annetulla nimellä ja kirjautuneella käyttäjällä ja ohjaa kontrollin rajapinnalle. Rajapinta tallentaa kantaan budjetin tiedot, jos saman nimistä budjettia ei ole jo olemassa. Jos toimi onnistuu, palauttaa sovelluslogiikka käyttöliittymälle toden. 

### Erän luonti budjetille
![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/eran_luonti.png)

Painikkeen painamiseen reagoiva tapahtumankäsittelijä ohjaa tarkistamaan syötteet ja niiden ollessa oikeanlaiset kutsuu sovelluslogiikan metodia "createTransaction" antaen parametriksi syötetyn nimen ja kokonaisluvun. Lisäksi käyttöliittymä ohjaa parametrina käsittellyn budjetti-olion. Sovelluslogiikka luo uuden erä-olion budjetilla, nimellä ja summalla. Lisäksi sovelluslogiikka liittä erän budjettiin, jotta summa päivittyy käyttöliittymässä. Rajapinta tallentaa kantaan erän tiedot. Jos toimi onnistuu, palauttaa sovelluslogiikka käyttöliittymälle toden. Tämän jälkeen käyttöliittymä päivittää eriin liittyvät tiedot ja budjetin summan. 
