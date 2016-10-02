/**
 * @file NameProcessOut.java
 * @brief Refer all the name process that could be called on the raspberry
 *
 * @version 1.0
 * @date 06/05/16
 * @author Guillaume MURET
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

public class NameProcessOut {

    public static final String BRAIN_ASK_READY =                 "askReady";
    public static final String BRAIN_STOP_TC =                   "stopTC";
    public static final String HISTORIAN_ASK_RECENT_HISTORY =    "askRecentHistory";
    public static final String HISTORIAN_ASK_CARD_HISTORY =      "askCardHistory";
    public static final String HISTORIAN_ASK_VALID_SAVE =        "askValidSave";
    public static final String CARD_GUARD_ASK_CHECK_CARD_ID  =    "askCheckCardID";
    public static final String CARD_GUARD_NEW_CARD  =             "newCard";
    public static final String SCHOLAR_TEST_ASK_NOMINAL_LIST_TEST="askNominalListTests";
    public static final String TESTER_GUARD_ASK_CHECK_LOG_TESTER= "askCheckLogTester";
    public static final String TESTER_RUN_TESTS          =       "runTests";
    public static final String TEST_MANAGER_SET_CAMPAIGN    =     "setCampaign";
    public static final String TEST_MANAGER_SET_STOP         =    "setStop";
}
