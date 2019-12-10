package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileBudgetDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File testFile;
    BudgetDao dao;

    @Before
    public void setUp() throws Exception {
        testFile = testFolder.newFile("test.txt");

        try (FileWriter file = new FileWriter(testFile.getAbsolutePath())) {
            file.write("Budget;Event;0\n");
        }

        dao = new FileBudgetDao(testFile.getAbsolutePath());
    }
//
//    @Test
//    public void test() {
//        System.out.println(dao.getBudgets());
//
//        assertEquals(1, 2);
//    }

}
