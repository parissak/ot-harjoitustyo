package budjetointisovellus.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

    Transaction transaction;

    @Before
    public void setUp() {
        this.transaction = new Transaction("Test", 0);
    }

    @Test
    public void setNameGetName() {
        this.transaction.setName("New");
        assertEquals("New", this.transaction.getName());
    }

    @Test
    public void getCorrectAmount() {
        this.transaction.setAmount(100);
        assertEquals(100, this.transaction.getAmount());
    }

    @Test
    public void equalWhenSameName() {
        Transaction b = new Transaction("Test", 0);
        assertTrue(this.transaction.equals(b));
    }
    
    @Test
    public void notEqualDifferentName() {
        Transaction b = new Transaction("notTest", 0);
        assertFalse(this.transaction.equals(b));
    }
        
    @Test
    public void correctOutput() {
        assertEquals("Test, 0", this.transaction.toString());
    }

}
