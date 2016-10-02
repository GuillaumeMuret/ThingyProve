/**
 * @file ProcessOut.java
 * @brief Enum of all the process that could be called on the raspberry
 *
 * @version 1.0
 * @date 14/04/2016
 * @author Guillaume Muret
 * @copyright
 *	Copyright (c) 2016, PRØVE
 * 	All rights reserved.
 * 	Redistribution and use in source and binary forms, with or without
 * 	modification, are permitted provided that the following conditions are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of PRØVE, Angers nor the
 * 	  names of its contributors may be used to endorse or promote products
 *   	derived from this software without specific prior written permission.
 *
 * 	THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * 	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * 	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * 	DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * 	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * 	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * 	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * 	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * 	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * 	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package fr.prove.thingy.communication.test;

import junit.framework.TestCase;

import org.junit.Test;

import fr.prove.thingy.communication.protocole.ProcessOut;

public class ProcessOutTest extends TestCase {

    /**
     * the process to test
     */
    ProcessOut processOutTest;

    /**
     * Do nothing.
     *
     * Do before tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test the process askReady
     */
    @Test
    public void testAskReady()
    {
        processOutTest=ProcessOut.BRAIN_ASK_READY;
        assertEquals(processOutTest.process,"askReady");
        assertEquals(processOutTest.numOwner,1);
        assertEquals(processOutTest.owner,"Brain");
    }

    /**
     * Test the process stopTC
     */
    @Test
    public void testStopTC()
    {
        processOutTest=ProcessOut.BRAIN_STOP_TC;
        assertEquals(processOutTest.process,"stopTC");
        assertEquals(processOutTest.numOwner,1);
        assertEquals(processOutTest.owner,"Brain");
    }

    /**
     * Test the process askRecentHistory
     */
    @Test
    public void testAskHistory()
    {
        processOutTest=ProcessOut.HISTORIAN_ASK_RECENT_HISTORY;
        assertEquals(processOutTest.process,"askRecentHistory");
        assertEquals(processOutTest.numOwner,2);
        assertEquals(processOutTest.owner,"Historian");
    }

    /**
     * Test the process askCardHistory
     */
    @Test
    public void testCardHistory()
    {
        processOutTest=ProcessOut.HISTORIAN_ASK_CARD_HISTORY;
        assertEquals(processOutTest.process,"askCardHistory");
        assertEquals(processOutTest.numOwner,2);
        assertEquals(processOutTest.owner,"Historian");
    }

    /**
     * Test the process askValidSave
     */
    @Test
    public void testValidSave()
    {
        processOutTest=ProcessOut.HISTORIAN_ASK_VALID_SAVE;
        assertEquals(processOutTest.process,"askValidSave");
        assertEquals(processOutTest.numOwner,2);
        assertEquals(processOutTest.owner,"Historian");
    }

    /**
     * Test the process askCheckCardID
     */
    @Test
    public void testAskCheckCardID()
    {
        processOutTest=ProcessOut.CARD_GUARD_ASK_CHECK_CARD_ID;
        assertEquals(processOutTest.process,"askCheckCardID");
        assertEquals(processOutTest.numOwner,3);
        assertEquals(processOutTest.owner,"CardGuard");
    }

    /**
     * Test the process newCard
     */
    @Test
    public void testNewCard()
    {
        processOutTest=ProcessOut.CARD_GUARD_NEW_CARD;
        assertEquals(processOutTest.process,"newCard");
        assertEquals(processOutTest.numOwner,3);
        assertEquals(processOutTest.owner,"CardGuard");
    }

    /**
     * Test the process askNominalListTests
     */
    @Test
    public void testAskNominalListTests()
    {
        processOutTest=ProcessOut.SCHOLAR_TEST_ASK_NOMINAL_LIST_TEST;
        assertEquals(processOutTest.process,"askNominalListTests");
        assertEquals(processOutTest.numOwner,4);
        assertEquals(processOutTest.owner,"ScholarTest");
    }

    /**
     * Test the process askCheckLogTester
     */
    @Test
    public void testAskCheckLogTester()
    {
        processOutTest=ProcessOut.TESTER_GUARD_ASK_CHECK_LOG_TESTER;
        assertEquals(processOutTest.process,"askCheckLogTester");
        assertEquals(processOutTest.numOwner,5);
        assertEquals(processOutTest.owner,"TesterGuard");
    }

    /**
     * Test the process runTests
     */
    @Test
    public void testRunTests()
    {
        processOutTest=ProcessOut.TESTER_RUN_TESTS;
        assertEquals(processOutTest.process,"runTests");
        assertEquals(processOutTest.numOwner,6);
        assertEquals(processOutTest.owner,"Tester");
    }

    /**
     * Test the process setCampaign
     */
    @Test
    public void testSetCampaign()
    {
        processOutTest=ProcessOut.TEST_MANAGER_SET_CAMPAIGN;
        assertEquals(processOutTest.process,"setCampaign");
        assertEquals(processOutTest.numOwner,7);
        assertEquals(processOutTest.owner,"TestManager");
    }

    /**
     * Test the process setStop
     */
    @Test
    public void testSetStop()
    {
        processOutTest=ProcessOut.TEST_MANAGER_SET_STOP;
        assertEquals(processOutTest.process,"setStop");
        assertEquals(processOutTest.numOwner,7);
        assertEquals(processOutTest.owner,"TestManager");
    }

    /**
     * No implanted.
     *
     * Do after the tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}