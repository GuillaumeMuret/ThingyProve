/**
 * @file ExpandableTestAdapter.java
 * @brief Adapter for Test view and model conversion
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
package fr.prove.thingy.adapter.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;

import fr.prove.thingy.R;
import fr.prove.thingy.model.TestGrouper;

public class ExpandableTestAdapter extends ExpandableRecyclerAdapter <TestHeaderHolder.ViewHolder, TestItemHolder.ViewHolder> {

    /**
     * inflater of the layout
     */
    private LayoutInflater mInflator;

    /**
     * Activity context
     */
    private Context context;

    /**
     * Constructor of the test adapter : expandable test adapter
     * @param context : the activity context
     * @param testHeaderHolders the test header holder
     */
    public ExpandableTestAdapter(Context context, ArrayList<TestHeaderHolder> testHeaderHolders) {
        super(testHeaderHolders);
        this.context = context;
        mInflator = LayoutInflater.from(context);

        for (TestHeaderHolder holder : testHeaderHolders) {

            for (int i=0;i<holder.getChildItemList().size();i++) {
                ((TestItemHolder)holder.getChildItemList().get(i)).setAdapter(this);
            }
        }

    }

    /**
     * Create parent view (header)
     * @param parentViewGroup : the parent view group
     * @return the new view of test header holder
     */
    @Override
    public TestHeaderHolder.ViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        return new TestHeaderHolder.ViewHolder(mInflator.inflate(R.layout.item_header, parentViewGroup, false));
    }

    /**
     * Create child view (item)
     * @param childViewGroup : the child view group
     * @return the new view of test item holder
     */
    @Override
    public TestItemHolder.ViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        return new TestItemHolder.ViewHolder(mInflator.inflate(R.layout.item_doubleline, childViewGroup, false));
    }

    /**
     * Bind of the parent view (header)
     * @param parentViewHolder : the parent view holder
     * @param position : the parent view position (in the list)
     * @param parentListItem : the parent view list
     */
    @Override
    public void onBindParentViewHolder(TestHeaderHolder.ViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        TestHeaderHolder hh = (TestHeaderHolder) parentListItem;
        hh.bind(parentViewHolder);
    }

    /**
     * Bind of the child view (item)
     * @param childViewHolder : the child view holder
     * @param position : the parent view position (in the list)
     * @param childListItem : the child list item
     */
    @Override
    public void onBindChildViewHolder(TestItemHolder.ViewHolder childViewHolder, int position, Object childListItem) {
        TestItemHolder ih = (TestItemHolder) childListItem;
        ih.bind(childViewHolder);
    }

    /**
     * Convert model into view
     */
    public static ArrayList<TestHeaderHolder> convertModel (ArrayList< TestGrouper > testList, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter,Context context) {

        ArrayList<TestHeaderHolder> convert = new ArrayList<>();

        for (TestGrouper grouper : testList) {
            convert.add(new TestHeaderHolder(grouper, adapter,context));
        }

        return convert;
    }

    /**
     * Getter of the context of the activity
     * @return the activity context
     */
    public Context getContext() {
        return context;
    }
}
