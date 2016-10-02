package fr.prove.thingy.model.test;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by François L. on 02/05/16.
 * Comprend les tests à effectuer pour la classe HistoryItem
 */
public class HistoryItemTest extends TestCase {

    /**
     * Notre objet qui servira aux tests
     */
    //private HistoryItem historyItem;

    /**
     * Nos constantes de création de l'objet
     */
    /*private static final String HISTORY_TYPE = "Type";
    private static final String HISTORY_IDENTIFIER = "Identifier";
    private static final String HISTORY_TESTER_NAME = "Guy Plantier";
    private static final boolean HISTORY_SUCCED = true;
    private static final int HISTORY_ELAPSED = 1234;*/

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Création de notre objet avec les variables par défaut
        //historyItem = new HistoryItem(HISTORY_TYPE, HISTORY_IDENTIFIER, HISTORY_TESTER_NAME, HISTORY_SUCCED, HISTORY_ELAPSED);
    }

    @Test
    public void testConstructor () {
        /*assertEquals(historyItem.getType(), HISTORY_TYPE);
        assertEquals(historyItem.getIdentifier(), HISTORY_IDENTIFIER);
        assertEquals(historyItem.getTesterName(), HISTORY_TESTER_NAME);
        assertEquals(historyItem.isHasSucced(), HISTORY_SUCCED);
        assertEquals(historyItem.getElapsedTimeInMinutes(), HISTORY_ELAPSED);*/
    }
}
