# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria. Koodin pakkausrakenne on seuraava:




## Sovelluslogiikka
Sovelluksen loogisen datamallin muodostavat luokat Budget ja Tranasction, jotka kuvaavat budjetteja ja niiden eriä.

Toiminnallisista kokonaisuuksista vastaa luokan BudgetService olio. Luokka tarjoaa kaikille käyttöliittymän toiminnoille oman metodin. Näitä ovat budjettien lisääminen sekä poistaminen, budjetin erien lisääminen sekä poistaminen ja budjettien sekä erien listaaminen. 

BudgetService pääsee käsiksi budjetteihin ja niiden eriin BudgetDao -rajapinnan toteuttavan luokan kautta.

BudgetServicen ja ohjelman muiden osien suhdetta kuvaava luokka/pakkauskaavio:

![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakettikaavio.jpg)


## Tietojen pysyväistallennus
Pakkauksen budget.dao luokka FileBudgetDao huolehtii tietojen tallettamisesta tiedostoon ja vastaavasti tiedon hakemisesta ohjelman tietorakenteisiin.

Luokka noudattaa Data Access Object -suunnittelumallia ja se on tarvittaessa mahdollista korvata uudella toteutuksella. LUokka on eristetty rajapinnan BudgetDao taakse ja sovelluslogiikka ei käytä luokkaa suoraan.


## Päätoiminnallisuudet
![alt text](https://github.com/parissak/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Budjetin%20luominen.png)
