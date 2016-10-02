/**
 * @file BusType.java
 * @brief Describes the request type we can use for event bus
 *
 * @version 1.0
 * @date 02/05/16
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

public enum BusType {
    /**
     * LEGEND :
     *      -> Bus event for the binder
     *      <- Bus event for the activity
     */

    /**
     * Bus of the SplashActivity
     */
    BUS_CLOSE_SOCKET,               // -> Ask the service to reset the connexion (close socket)
    NEW_COMMUNICATION,              // -> Failed to connect and create new Communication and new readThread
    SET_UP_CONNEXION,               // -> Create socket and ask ready to the rasp
    READY_OK,                       // <- Inform the activities that the rasp is ready
    NOT_READY_KO,                   // <- Inform the activities that the rasp is not ready

    START_TIMER_TIMEOUT_SPLASH,     // -> Ask the binder to start a timer (display splash)
    DISPLAY_LOGIN_ACTIVITY,         // <- Ask the SplashActivity to display the login activity

    /**
     * Bus of the LoginActivity
     */
    CONNECT_OPERATOR,               // -> Ask the service to check the login
    ALLOW_OPERATOR_ACCESS,          // <- Tells that the login is successful
    BAN_OPERATOR_ACCESS,            // <- The username or the password is incorrect

    START_TIMER_TIMEOUT_LOGIN,      // -> Start the timer. If no response come, ALARM_UI

    SET_OPERATOR_INFO,              // <- The operator's info has been received

    ASK_RECENT_HISTORY,             // -> ask the recent history
    SET_RECENT_HISTORY,             // <- The recent history has been received

    /**
     * Bus of HomeActivity
     */
    ASK_EXISTANT_CARD,              // -> Ask to the service to check if the card exist
    EXISTANT_CARD,                  // <- The card exist on the data base or has been created
    NO_CARD,                        // <- The card doesn't exist on the data base

    NEW_CARD,                       // -> Ask to the service to ask a new card
    SET_CARD_ID,                    // <- Answer the new CardID generate by the rasp

    ASK_NOMINAL_LIST_TEST,          // -> Ask to the service to send this request on the rasp
    SET_NOMINAL_LIST_TEST,          // <- The nominal list has been received : Home -> Test

    ASK_SAVE_VALIDATION_RESULT,     // -> Ask to save the validation of the test
    VALID_SAVE_VALIDATION_RESULT,   // <- Answer of the validation of the test

    ASK_CARD_HISTORY,               // -> Ask to the binder to ask the card history

    /**
     * Bus of TestActivity
     */
    ASK_SAVE_CAMPAIGN,              // -> Ask to save the test campaign
    VALID_CAMPAIGN,                 // <- Receive the save validation of the campaign
    START_TIMER_TIMEOUT_UPDATING_TEST, // -> start a timer if a network problem occurs

    RUN_TESTS,                      // -> Run the test list !
    UPDATE_TEST,                    // <- A test has just finished, we've the result
    END_TEST,                       // <- Test list has finished

    SET_STOP,                       // -> Interruption of the test

    /**
     * Bus of the CardHistoryActivity
     */
    ERROR_CARD_HISTORY,             // <- Error : the history of this card is empty

    DISPLAY_CARD_HISTORY,           // <- The history of the card has been received very well !
    SHOW_DETAILS,                   // <- Show details of the card history

    /**
     * Bus for all the Activity
     */
    ALARM_UI,                       // <- Network Problem
    NOTHING,                        // <- Do Nothing

}
