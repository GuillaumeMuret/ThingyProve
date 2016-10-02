/**
 * @file ExpandableHistoryAdapter.java
 * @brief Adapter for History view and model conversion
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
package fr.prove.thingy.adapter.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;

import fr.prove.thingy.R;
import fr.prove.thingy.model.HistoryGrouper;

public class ExpandableHistoryAdapter extends ExpandableRecyclerAdapter <HistoryHeaderHolder.ViewHolder, HistoryItemHolder.ViewHolder> {

    /**
     * View inflation
     */
    private LayoutInflater mInflator;

    /**
     * Activity context
     */
    private Context context;

    /**
     * Constructor of the history list
     * @param context : the activity context
     * @param historyHeaderHolders the history header holder
     */
    public ExpandableHistoryAdapter(Context context, ArrayList<HistoryHeaderHolder> historyHeaderHolders) {
        super(historyHeaderHolders);
        this.context = context;
        mInflator = LayoutInflater.from(context);

        for (HistoryHeaderHolder holder : historyHeaderHolders) {

            for (int i=0;i<holder.getChildItemList().size();i++) {
                ((HistoryItemHolder)holder.getChildItemList().get(i)).setAdapter(this);
            }
        }
    }

    /**
     * Creation of the parent view
     * @param parentViewGroup the parent view group
     * @return a new history view of header holder
     */
    @Override
    public HistoryHeaderHolder.ViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        return new HistoryHeaderHolder.ViewHolder(mInflator.inflate(R.layout.item_header, parentViewGroup, false));
    }

    /**
     * Creation of the child view
     * @param childViewGroup the child view group
     * @return a new history view of item holder
     */
    @Override
    public HistoryItemHolder.ViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        return new HistoryItemHolder.ViewHolder(mInflator.inflate(R.layout.item_doubleline, childViewGroup, false));
    }

    /**
     * Bind of the parent view
     * @param parentViewHolder : the parent view
     * @param position : the parent view position (on the list)
     * @param parentListItem : the parent list item
     */
    @Override
    public void onBindParentViewHolder(HistoryHeaderHolder.ViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        HistoryHeaderHolder rhhh = (HistoryHeaderHolder) parentListItem;
        rhhh.bind(parentViewHolder);
    }

    /**
     * Bind of the child view
     * @param childViewHolder the child view
     * @param position : the child view position (on the list)
     * @param childListItem : the child list item
     */
    @Override
    public void onBindChildViewHolder(HistoryItemHolder.ViewHolder childViewHolder, int position, Object childListItem) {
        HistoryItemHolder hih = (HistoryItemHolder) childListItem;
        hih.bind(childViewHolder);
    }


    /**
     * Convert a model data into a view
     * @param historyList the list of history
     * @param adapter the adapter
     * @param context : Activity context
     * @return the converted model
     */
    public static ArrayList<HistoryHeaderHolder> convertModel (ArrayList<HistoryGrouper> historyList, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, Context context) {

        ArrayList<HistoryHeaderHolder> convert = new ArrayList<>();

        for (HistoryGrouper grouper : historyList) {
            convert.add(new HistoryHeaderHolder(grouper, adapter, context));
        }

        return convert;
    }

    /**
     * Getter of the context activity
     * @return the activity context
     */
    public Context getContext() {
        return context;
    }
}
