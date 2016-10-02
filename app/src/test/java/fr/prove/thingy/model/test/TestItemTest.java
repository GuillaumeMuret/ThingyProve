package fr.prove.thingy.model.test;

import junit.framework.TestCase;

import org.junit.Test;

import fr.prove.thingy.model.TestItem;

/**
 * Created by François L. on 02/05/16.
 * Comprend les tests à effectuer pour la classe TestItem
 *
 * Clarisse Girault : ajout de TestToString()
 */
public class TestItemTest extends TestCase {

    /**
     * Notre objet qui servira aux tests
     */
    private TestItem testItem;

    /**
     * Nos constantes de création de l'objet
     */
    private static final String TEST_ITEM_NAME = "Test";
    private static final String TEST_ITEM_DESCRIPTION = "Description";
    private static final int TEST_ITEM_STATUS = TestItem.TEST_STATUS_DONE;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Création de notre objet avec les variables par défaut
        testItem = new TestItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_STATUS);
    }

    @Test
    public void testConstructor () {
        assertEquals(testItem.getName(), TEST_ITEM_NAME);
        assertEquals(testItem.getDescription(), TEST_ITEM_DESCRIPTION);
        assertEquals(testItem.getStatus(), TEST_ITEM_STATUS);
    }

    @Test
    public void testmarkAsEdit () {
        testItem.makeAsEdit();
        assertEquals(testItem.getStatus(), TestItem.TEST_STATUS_EDIT_MAKE);
    }

    @Test
    public void testmakeAsTestWaiting () {
        testItem.makeAsTestWaiting();
        assertEquals(testItem.getStatus(), TestItem.TEST_STATUS_PENDING);
    }

    @Test
    public void testmakeAsSkip () {
        testItem.makeAsSkip();
        assertEquals(testItem.getStatus(), TestItem.TEST_STATUS_EDIT_SKIP);
    }

    @Test
    public void testmakeAsSuccess () {
        testItem.makeAsSuccess();
        assertEquals(testItem.getStatus(), TestItem.TEST_STATUS_DONE);
    }

    @Test
    public void testmakeAsRunning () {
        testItem.makeAsRunning();
        assertEquals(testItem.getStatus(), TestItem.TEST_STATUS_RUNNING);
    }


    @Test
    public void testToString(){
        String aTester;
        aTester=testItem.toString();
        assertEquals("TestItem{" +
                "name='Test\'" +
                ", description='Description\'" +
                ", status=1" +
                "}",aTester);

    }
}
