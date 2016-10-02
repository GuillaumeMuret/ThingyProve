/**
 * @file HistoryHeaderHolder.java
 * @brief Model of History Header Holder (parent)
 *
 * @version 1.0
 * @date 05/05/16
 * @author François LEPAROUX
 * @collab Guillaume Muret
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
package fr.prove.thingy.adapter.history;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.prove.thingy.adapter.holder.HeaderHolder;
import fr.prove.thingy.model.CardHistoryItem;
import fr.prove.thingy.model.History;
import fr.prove.thingy.model.HistoryGrouper;
import fr.prove.thingy.model.RecentHistoryItem;

public class HistoryHeaderHolder extends HeaderHolder{

    /**
     * Define the type of history (recent or card history)
     */
    private int typeOfHistory;

    /**
     * The Group of history object
     */
    private HistoryGrouper historyGrouper;

    /**
     * the child items of the recent history object
     */
    private List<HistoryItemHolder> childHistoryItems;

    /**
     * Constructor of the Header holder : Create recent or card history child items from model items
     * @param historyGrouper the history group
     * @param adapter the adapter
     * @param context the activity context
     */
    public HistoryHeaderHolder(HistoryGrouper historyGrouper, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, Context context) {
        super(historyGrouper.getDateAsString());
        this.historyGrouper = historyGrouper;
        typeOfHistory=historyGrouper.getTypeOfHistory();

        // Create the list
        childHistoryItems = new ArrayList<>();

        if(typeOfHistory== History.TYPE_RECENT_HISTORY) {
            // Create recent history child items from model items
            if (historyGrouper.getRecentHistoryItems() != null) {
                for (RecentHistoryItem recentHistoryItem : historyGrouper.getRecentHistoryItems()) {
                    childHistoryItems.add(new HistoryItemHolder(recentHistoryItem, adapter, context));
                }
            }
        }else if(typeOfHistory == History.TYPE_CARD_HISTORY){
            // Create card history child items from model items
            if (historyGrouper.getCardHistoryItems() != null) {
                for (CardHistoryItem cardHistoryItem : historyGrouper.getCardHistoryItems()) {
                    childHistoryItems.add(new HistoryItemHolder(cardHistoryItem, adapter, context));
                }
            }
        }
        // Remove divider of last item
        if (childHistoryItems.size() > 0)
            childHistoryItems.get(childHistoryItems.size() - 1).removeDivider();
    }

    /**
     * bind of the header holder
     * @param vh : return the bind of the header holder
     */
    @Override
    public void bind(HeaderHolder.ViewHolder vh) {
        super.bind(vh);
        vh.getImgIco().setVisibility(View.INVISIBLE);
        vh.getVwCircle().setVisibility(View.INVISIBLE);
    }

    /**
     * Getter of the child items
     * @return the child history items
     */
    @Override
    public List getChildItemList() {
        return childHistoryItems;
    }
}