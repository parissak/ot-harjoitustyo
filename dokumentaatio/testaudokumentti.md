# Testausdokumentti

Ohjelmaa on testattu yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti järjestelmätason testeillä.

## Yksikkö- ja integraatiotestaus

Sovelluslogiikkaa on testattu yhdellä luokalla, eli BudgetServiceTestillä. Testaus simuloi käyttöliittymän kautta annettuja syötteitä. Sovelluslogiikkakerroksen yksittäisille luokille Budget, Transaction ja User ei ole määritelty erikseen testejä. Osaa niiden metodeista, pääosin equals- ja hashcode- metodeita, ei ole testattu täydellisesti, mutta ne on testattu riittävällä tavalla ja käytännöllisillä skenaarioilla. Sovelluslogiikan testauksessa on käytetty hyödyksi valekomponentteja, jotka käyttävät testaukseen liittyvää tietokantaa. 

### DAO-luokat

DAO-luokkien toiminnallisuus on testattu normaalin käyttöön liittyvää tietokantaa käyttäen. Testauksen jälkeen testiluokat poistavat normaalin tietokannan, mikä aiheuttaa ongelmia, jos testausta tehdään normaalin käytön välissä.

### Testauskattavuus

Käyttöliittymäkerrosta ei ole testattu. Ilman tätä testauksen rivikattavuus on 82% ja haarautumakattavuus 71%.

## Järjestelmätestaus

Järjestelmätestaus on suoritettu manuaalisesti sovelluksen eri kehitysvaiheissa. Tähän on kuulunut esimerkiksi tietokannan tietojen pysyvyyden, eri olioden poistamisen ja virheellisten syötteiden antamisen testaus. 

## Sovellukseen jääneet ongelmat

Sovelluksessa ei ole erillistä tietokantaan ja sen yhteyksiin keskittyvää luokkaa. Tämän avulla DAO-luokkien testauksessa voisi käyttää erillistä tietokantaa ja samalla sovelluksen rakenneja koodi selkeytyisi.

Nykyisessä versiossa kahden eri käyttäjän ei ole mahdollista luoda samannimistä budjettia. Tämä ongelma jäi selvittämättä, sillä virhetilannetta ja sen ratkaisua koskevaa tietoa ei juurikaan löytynyt. 
