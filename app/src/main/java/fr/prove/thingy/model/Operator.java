/**
 * @file Operator.java
 * @brief Describes an operator (people with tools in da pocket)
 *
 * @version 1.0
 * @date 10/05/16
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
package fr.prove.thingy.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import fr.prove.thingy.utils.DateUtils;

public class Operator {

    /**
     * name of the operator
     */
    private String name;

    /**
     * last connection of the operator
     */
    private String rawLastConnect;

    /**
     * french date of the last connection
     */
    private String frenchDate;

    /**
     * The date of the last connection
     */
    private Date date;

    /**
     * Constructor of the Operator
     * @param strJSON : the json params received
     */
    public Operator(String strJSON) {
        try {
            JSONObject obj = new JSONObject(strJSON);
            this.name = obj.getString("name");
            try{
                this.rawLastConnect = obj.getString("date");
            } catch (JSONException e) {
                e.printStackTrace();
                this.rawLastConnect = "2005-01-01 00:00:01";
            }
            // parse the date (get Date object from string)
            this.date = DateUtils.parseStringToDate(rawLastConnect);

            // parse the date again (get french String from Date)
            this.frenchDate = DateUtils.parseDateToFrenchString(this.date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the french date
     * @return : the french date
     */
    public String getFrenchDate() {
        return frenchDate;
    }
}
