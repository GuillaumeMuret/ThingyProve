/**
 * @file BusResponse.java
 * @brief Describes an object for transporting a response on the event bus
 * cf opposite : BusRespone
 *
 * @version 1.0
 * @date 02/05/16
 * @author François LEPAROUX
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

import android.os.Bundle;

public class BusResponse {

    /**
     * Request identifier
     */
    private BusType requestType;

    /**
     * Bundle data to transport
     */
    private Bundle data;

    /**
     * Main constructor
     * @param requestType the request identifier
     */
    public BusResponse(BusType requestType) {
        this.requestType = requestType;
        this.data = new Bundle();
    }

    /**
     * Returns the request type (identifier)
     * @return the request id
     */
    public BusType getRequestType() {
        return requestType;
    }

    /**
     * Sets the data to transport
     * @param data the bundled data
     */
    public void setData(Bundle data) {
        this.data = data;
    }

    /**
     * Returns the data to transport
     * @return the bundled data
     */
    public Bundle getData() {
        return data;
    }
}
