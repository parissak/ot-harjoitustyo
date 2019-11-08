package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti saldoaOn;
    Maksukortti saldoaEi;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        saldoaOn = new Maksukortti(1000);
        saldoaEi = new Maksukortti(0);
    }

    @Test
    public void alustusKassassaRahaa() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void alustusEdullistenLounaidenMaara() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void alustusMaukkaidenLounaidenMaara() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void riittavaMaksuEdullisestiKassa() {
        int raha = kassapaate.syoEdullisesti(480);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }

    @Test
    public void riittavaMaksuEdullisestiVaihtoRaha() {
        int raha = kassapaate.syoEdullisesti(480);
        assertEquals(240, raha);
    }

    @Test
    public void riittavaMaksuMaukkaastiKassa() {
        int raha = kassapaate.syoMaukkaasti(800);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }

    @Test
    public void riittavaMaksuMaukkaastiVaihtoRaha() {
        int raha = kassapaate.syoMaukkaasti(800);
        assertEquals(400, raha);
    }

    @Test
    public void riittavaMaksuEdullisetLounaat() {
        int raha = kassapaate.syoEdullisesti(400);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void riittavaMaksuMaukkaatLounaat() {
        int raha = kassapaate.syoMaukkaasti(800);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaMaksuEdullisestiKassa() {
        kassapaate.syoEdullisesti(230);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void eiRiittavaMaksuEdullisestiVaihtoRaha() {
        assertEquals(230, kassapaate.syoEdullisesti(230));
    }

    @Test
    public void eiRiittavaMaksuEdullisestiLounaidenMäärä() {
        kassapaate.syoEdullisesti(230);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void eiRiittavaMaksuMaukkaastiKassa() {
        kassapaate.syoMaukkaasti(390);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void eiRiittavaMaksuMaukkaastiVaihtoRaha() {
        assertEquals(390, kassapaate.syoMaukkaasti(390));
    }

    @Test
    public void eiRiittavaMaksuMaukkaastiLounaidenMäärä() {
        kassapaate.syoEdullisesti(230);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void toimivaKorttimaksuEdullisesti() {
        assertEquals(true, kassapaate.syoEdullisesti(saldoaOn));
    }

    @Test
    public void toimivaKorttimaksuMaukkaasti() {
        assertEquals(true, kassapaate.syoMaukkaasti(saldoaOn));
    }

    @Test
    public void toimivaKorttimaksuEdullistenLounaidenMäärä() {
        kassapaate.syoEdullisesti(saldoaOn);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void toimivaKorttimaksuMaukkaidenLounaidenMäärä() {
        kassapaate.syoMaukkaasti(saldoaOn);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void eiToimivaKorttimaksuEdullistenLounaidenMäärä() {
        kassapaate.syoEdullisesti(saldoaEi);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void eiToimivaKorttimaksuMaukkaidenLounaidenMäärä() {
        kassapaate.syoMaukkaasti(saldoaEi);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassaEiMuutuKortillaOstettaessa() {
        kassapaate.syoEdullisesti(saldoaOn);
        kassapaate.syoMaukkaasti(saldoaOn);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void kortinLatausSaldonMuutos() {
        kassapaate.lataaRahaaKortille(saldoaEi, 100);
        assertEquals(100, saldoaEi.saldo());
    }

    @Test
    public void kortinLatausNegatiivisellaSummallaSaldonMuutos() {
        kassapaate.lataaRahaaKortille(saldoaOn, -100);
        assertEquals(1000, saldoaOn.saldo());
    }

    @Test
    public void kortinLatausKassanMuutos() {
        kassapaate.lataaRahaaKortille(saldoaEi, 100);
        assertEquals(100100, kassapaate.kassassaRahaa());
    }

}
