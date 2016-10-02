/**
 * @file ProtocoleVocabulary.java
 * @brief Refer the keys used on the JSON object
 *
 * @version 1.0
 * @date 06/05/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
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
package fr.prove.thingy.communication.protocole;

public class ProtocoleVocabulary {

    /**
     * Keys of the json message
     */
    public static final String JSON_PROCESS = "process";
    public static final String JSON_NUMOWNER = "numOwner";
    public static final String JSON_OWNER = "owner";
    public static final String JSON_PARAMS = "params";
    public static final String JSON_PARAMS_DATE = "date";
    public static final String JSON_PARAMS_CREATION = "creation";
    public static final String JSON_PARAMS_HISTORY = "history";
    public static final String JSON_PARAMS_CARD_TYPE = "cardType";

    public static final String JSON_PARAMS_ID_SCENARIO = "IDScenario";
    public static final String JSON_PARAMS_ID_STEP_TEST = "IDStepTest";
    public static final String JSON_PARAMS_TEST_RESULT = "testResult";

    public static final String JSON_PARAMS_ID = "ID";

    public static final String JSON_PARAMS_OBLIGATION = "obligation";
    public static final String JSON_PARAMS_RUN = "run";
    public static final String JSON_PARAMS_ITEMS = "items";

    public static final String JSON_PARAMS_STATE = "state";
    public static final String JSON_PARAMS_CARD_ID = "cardID";
    public static final String JSON_PARAMS_VALIDATION ="validation";
    public static final String JSON_PARAMS_NUM_TEST ="numTest";
    public static final String JSON_PARAMS_NAME_TESTER ="nameTester";
    public static final String JSON_PARAMS_NAME = "name";
    public static final String JSON_PARAMS_DESCRIPTION ="description";
    public static final String JSON_PARAMS_MIN_TIME ="minTime";
    public static final String JSON_PARAMS_RESULT ="result";
    public static final String JSON_PARAMS_OPERATOR_ID = "operatorID";
    public static final String JSON_PARAMS_OPERATOR_PASS = "operatorPass";
    public static final String JSON_PARAMS_INFO_TESTER = "infoTester";

    // TEST ONLY
    public static final String JSON_PARAMS_TEST_COMMUNICATION = "testCommunication";
    public static final String JSON_PARAMS_TEST_MEMORY = "testMemory";
    public static final String JSON_PARAMS_TEST_ANALOG_INPUT = "testAnalogInput";
    public static final String JSON_PARAMS_TEST_DIGITAL_INPUT = "testDigitalInput";
    public static final String JSON_PARAMS_TEST_RELAY = "testRelay";

    // Distributor key for nothing
    public static final String JSON_PARAMS_NOTHING = "{}";
}
