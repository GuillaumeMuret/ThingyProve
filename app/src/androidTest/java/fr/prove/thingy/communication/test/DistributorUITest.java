/**
 * @file DistributorTest.java
 * @brief unit test for Distributor class
 * @version 1.0
 * @date 14/05/2016
 * @author Guillaume Muret
 * @see fr.prove.thingy.communication.Distributor
 * @copyright :
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
 */

package fr.prove.thingy.communication.test;

import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.prove.thingy.communication.Distributor;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.communication.DistributorUiObsolete;


@RunWith(AndroidJUnit4.class)
public class DistributorUITest extends InstrumentationTestCase{

    /**
     * Distributor use for tests.
     */
    private DistributorUiObsolete distributorTest;


    /**
     * Create the Distributor useful to the tests.
     *
     * Do before tests.
     *
     * @throws Exception all exception.
     *
     */
    @Override
    public void setUp() throws Exception
    {
        this.distributorTest  = new DistributorUiObsolete();
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
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Precondition to execute the test
     */
    @Test
    public void testPreconditions()
    {
        assertNotNull(this.distributorTest);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeReady() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\":\"setReady\",\"params\":{\"state\":0}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.READY_OK);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeOperatorAccess() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\":\"setOperatorAccess\",\"params\":{\"state\":0}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.ALLOW_OPERATOR_ACCESS);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeOperatorInfo() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\":\"setOperatorInfo\",\"params\":{\"infoTester\":{}}}";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.SET_OPERATOR_INFO);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeRecentHistory() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\" : \"setRecentHistory\",\"params\" :{\"history\":[{}]}}";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.SET_RECENT_HISTORY);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeCardHistory() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\":\"setCardHistory\",\"params\":{THIS:THIS}}";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.DISPLAY_CARD_HISTORY);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeCardExistance() throws Exception {
        // Creation of the parameters
        String messToTest= "{process:setCardExistance,params:{state:0}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.EXISTANT_CARD);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeNominalListTests() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\" : \"setNominalListTests\",\"params\" : {\"list\":[]}}";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.SET_NOMINAL_LIST_TEST);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeCardID() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\":\"setCardID\",\"params\":{\"cardID\":\"BG0101162155\"}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.SET_CARD_ID);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeStepTestEndResult() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\" : \"setStepTestEndResult\",\"params\" :{\"IDScenario\" : " +
                "\"THIS\",\"IDStepTest\" : \"THIS\",\"testResult\" : 1}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.UPDATE_TEST);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeStateSave() throws Exception {
        // Creation of the parameters
        String messToTest= "{process:setStateSave,params:{state:0}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.VALID_SAVE_VALIDATION_RESULT);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeEndTest() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\" : \"setEndTests\",\"params\" :{\"state\": 0}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.END_TEST);
    }

    /**
     * Test of the BusResponse received after call decode method
     * concerned method: public BusResponse decode(String receivedMessage)
     */
    @Test
    public void testToDecodeValidCampaign() throws Exception {
        // Creation of the parameters
        String messToTest= "{\"process\" : \"validCampaign\", \"params\" : {}}\r\n";

        // The different parameters of test
        BusResponse busResponse =  this.distributorTest.decode(messToTest);
        assertEquals(busResponse.getRequestType(),BusType.VALID_CAMPAIGN);
    }
}