/**
 * @file Number.java
 * @brief Refer the number used in this application
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

package fr.prove.thingy.constants;

public class Number {

    /**
     * Number of connection attempt
     */
    public final static int NB_MAX_ATTEMPT = 3;

    /**
     * nb of characters on the password
     */
    public final static int PASSWORD_NB_CHAR = 5;

    /**
     * Number of return code when finishing an activity
     */
    public static final int REQUEST_CODE_ACTIVITY_HOME = 200;
    public static final int REQUEST_CODE_ACTIVITY_LOGIN = 201;
    public static final int REQUEST_CODE_ACTIVITY_TEST = 202;
    public static final int REQUEST_CODE_ACTIVITY_CARD_HISTORY = 203;
    public static final int REQUEST_CODE_ACTIVITY_STAT = 204;

    public static final int FINISH_ACTIVITY_EXIT = 102;
    public static final int FINISH_ACTIVITY_ALARM_UI = 103;
    public static final int FINISH_LOGIN_AND_START_HOME = 104;
    public static final int FINISH_HOME_AND_START_LOGIN = 105;
    public static final int FINISH_CARD_HISTORY = 106;
    public static final int FINISH_TEST = 107;
    public static final int FINISH_STAT = 108;

    /**
     * Status when a test or history is valid
     */
    public static final int STATUS_VALIDATION = 0;


}
