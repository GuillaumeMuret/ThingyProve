/**
 * @file Timeouts.java
 * @brief Referers to this file to add / find some timeout delays
 *
 * @version 1.0
 * @date 29/04/16
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
package fr.prove.thingy.constants;

public class Timeouts {

    /**
     * Time waiting for a socket connection
     */
    public final static int TW_CONNECTION_SOCKET = 1000;

    /**
     * Time waiting for the creation of the communication
     */
    public final static int TW_CREATE_COMMUNICATION = 500;

    /**
     * Time waiting for displaying the splash screen (after the creation of the communicaiton)
     */
    public final static int TIMEOUT_SPLASH = 500;

    /**
     * Time waiting for a respond from the rasp. After this time -> network problem
     */
    public final static int TW_TIMER_WAIT_PROCESS_FROM_RASP = 10000;

    /**
     * This time is for the time to reset the connection (this time have to be greater or equal than 1 sec)
     */
    public final static int TW_CLOSE_SOCKET = 1000;

    /**
     * time to display the locked icon
     */
    public final static int TW_LOCKED_ICON = 500;

    /**
     * time to wait before the next update
     */
    public final static int TIMEOUT_UPDATING_TEST = 10000;

}
