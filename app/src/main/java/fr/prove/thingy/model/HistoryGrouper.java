/**
 * @file RecentHistoryGrouper.java
 * @brief The recent history group by date
 *
 * @version 1.0
 * @date 12/05/16
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

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import fr.prove.thingy.utils.DateUtils;

public class HistoryGrouper {

    /**
     * the type of history (recent or card)
     */
    private int typeOfHistory;

    /**
     * The date of the test(s)
     */
    private Date date;

    /**
     * The french date of the test(s)
     */
    private String frenchDate;

    /**
     * The recent history items of a day
     */
    public ArrayList<RecentHistoryItem> recentHistoryItems;

    /**
     * The card history of a day
     */
    public ArrayList<CardHistoryItem> cardHistoryItems;

    /**
     * Constructor of the RecentHistoryGrouper : get the date and create the items of the group.
     * @param date : date of the test
     * @param strHistoryItems : string of the history items
     */
    public HistoryGrouper(String date, String strHistoryItems,int type) {
        this.date = DateUtils.parseStringToDate(date);
        typeOfHistory=type;
        if(type==History.TYPE_RECENT_HISTORY) {
            recentHistoryItems = new ArrayList<>();
            setRecentHistoryGrouper(strHistoryItems);
        }else if(type==History.TYPE_CARD_HISTORY){
            cardHistoryItems = new ArrayList<>();
            setCardHistoryGrouper(strHistoryItems);
        }
    }

    /**
     * Create the items of the recent history group
     * @param strRecentHistoryItems : string of the RecentHistoryItems
     */
    public void setRecentHistoryGrouper(String strRecentHistoryItems){
        recentHistoryItems.add(new RecentHistoryItem(strRecentHistoryItems));
    }

    /**
     * Create the items of the card history group
     * @param strCardHistoryItems : string of the CardHistoryItems
     */
    public void setCardHistoryGrouper(String strCardHistoryItems){
        cardHistoryItems.add(new CardHistoryItem(strCardHistoryItems));
    }

    /**
     * Create a french date with the context
     * @param context : context of the activity
     */
    public void parseDateWithContext (Context context) {
        this.frenchDate = DateUtils.parseDateToFriendlyFrenchString(context, this.date);
    }

    /**
     * Getter of the date
     * @return date
     */
    public Date getDateAsDate() {
        return this.date;
    }

    /**
     * Getter of the french date
     * @return frenchDate
     */
    public String getDateAsString() {
        return frenchDate;
    }

    /**
     * Getter of this history group.
     * @return recentHistoryItems
     */
    public ArrayList<RecentHistoryItem> getRecentHistoryItems() {
        return recentHistoryItems;
    }

    /**
     * Getter of this history group.
     * @return cardHistoryItems
     */
    public ArrayList<CardHistoryItem> getCardHistoryItems() {
        return cardHistoryItems;
    }

    /**
     * Getter of the type of history
     * @return typeOfHistory
     */
    public int getTypeOfHistory(){
        return typeOfHistory;
    }

}
