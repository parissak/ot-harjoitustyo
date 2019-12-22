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
![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Budjetin%20luominen.png)

### Budjetin luominen

