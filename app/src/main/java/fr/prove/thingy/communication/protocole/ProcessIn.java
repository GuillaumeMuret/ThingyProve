/**
 * @file ProcessIn.java
 * @brief Refer the different process that could be called on the UI
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

public class ProcessIn {

    /**
     * The different process called on the UI
     */
    public static final String ERROR =                      "ERROR";
    public static final String SET_RECENT_HISTORY =         "setRecentHistory";
    public static final String SET_CARD_HISTORY =           "setCardHistory";
    public static final String SET_READY =                  "setReady";
    public static final String SET_CARD_EXISTANCE =         "setCardExistance";
    public static final String SET_NOMINAL_LIST_TESTS =     "setNominalListTests";
    public static final String SET_CARD_ID =                "setCardID";
    public static final String SET_OPERATOR_ACCESS =        "setOperatorAccess";
    public static final String SET_OPERATOR_INFO =          "setOperatorInfo";
    public static final String SET_STEP_TEST_END_RESULT =   "setStepTestEndResult";
    public static final String ALARM_UI =                   "alarmUI";
    public static final String SET_STATE_SAVE =             "setStateSave";
    public static final String SET_END_TEST =               "setEndTests";
    public static final String VALID_CAMPAIGN =             "validCampaign";
}
