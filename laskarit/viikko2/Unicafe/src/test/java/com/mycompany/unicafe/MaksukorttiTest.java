package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void tulostusOikein() {
        kortti.otaRahaa(4);
        assertEquals("saldo: 0.6", kortti.toString());
    }

    @Test
    public void saldonAlustaminen() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void saldonLataus() {
        kortti.lataaRahaa(5);
        assertEquals(15, kortti.saldo());
    }

    @Test
    public void saldonVähentäminen() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }

    @Test
    public void saldoRiittää() {
        assertEquals(true, kortti.otaRahaa(9));
    }

    @Test
    public void saldoEiRiitä() {
        assertEquals(false, kortti.otaRahaa(11));
    }

}
