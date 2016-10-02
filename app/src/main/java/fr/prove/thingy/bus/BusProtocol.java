/**
 * @file BusProtocol.java
 * @brief Refers the different bus keys used in the program
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
package fr.prove.thingy.bus;

public class BusProtocol {

    /**
     * Bundle : data on the bus event
     */

    // TestManager keys
    public static final String BUS_DATA_TEST_MANAGER_STOP = "busevent.data.test_manager.stop";

    public static final String BUS_DATA_TEST_MANAGER_ID_SCENARIOS = "busevent.data.test_manager.id_scenarios";
    public static final String BUS_DATA_TEST_MANAGER_SHOULD_RUN = "busevent.data.test_manager.should_run";

    // Historian keys
    public static final String BUS_DATA_HISTORIAN_IDRESEARCHCARD = "busevent.data.historian.idresearchcard";
    public static final String BUS_DATA_HISTORIAN_VALIDATION = "busevent.data.historian.validation";

    // CardGuard keys
    public static final String BUS_DATA_CARDGUARD_CARDID = "busevent.data.cardguard.cardid";

    // TesterGuard keys
    public static final String BUS_DATA_TESTERGUARD_USERID = "busevent.data.tester.guard.user.id";
    public static final String BUS_DATA_TESTERGUARD_USERPASS = "busevent.data.tester.guard.user.pass";

    // Tester info (name, date)
    public static final String BUS_DATA_UI_TESTERINFO = "busevent.data.ui.tester.info";
    // Recent History
    public static final String BUS_DATA_UI_RECENT_HISTORY = "busevent.data.ui.recent.history";
    // Nominal list test
    public static final String BUS_DATA_UI_NOMINAL_LIST_TEST = "busevent.data.ui.nominal.list.test";
    // Step test result
    public static final String BUS_DATA_UI_STEP_TEST_RESULT = "busevent.data.ui.step.test.result";
    // The card ID of the created card
    public static final String BUS_DATA_UI_CARDID = "busevent.data.ui.cardid";
    // The data of the card history
    public static final String BUS_DATA_UI_CARDHISTORY = "busevent.data.ui.card.history";
    public static final String BUS_DATA_UI_CARDHISTORY_DETAILS = "busevent.data.ui.card.history.detail";
    // The data of the step test
    public static final String BUS_DATA_UI_ID_SCENARIO = "busevent.data.ui.id.scenario";
    public static final String BUS_DATA_UI_ID_STEP_TEST = "busevent.data.ui.step.test";
    public static final String BUS_DATA_UI_TEST_RESULT = "busevent.data.ui.test.result";

    /**
     * The key of the message receive
     */
    public static final String BINDER_MAN_RECEIVE_MESSAGE = "binder.man.receive.message";
}
