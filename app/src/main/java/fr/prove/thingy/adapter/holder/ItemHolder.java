/**
 * @file ItemHolder.java
 * @brief Abstract model of Item holder (child)
 *
 * @version 1.0
 * @date 05/05/16
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
package fr.prove.thingy.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import fr.prove.thingy.R;

public abstract class ItemHolder {

    /**
     * the item title
     */
    private String textTitle;

    /**
     * The description of the item
     */
    private String textDescription;

    /**
     * Boolean uses to know if the item has divider
     */
    private boolean hasDivider;

    /**
     * The adapter
     */
    protected RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    /**
     * Item constructor without adapter
     * @param textTitle the item title
     * @param textDescription the item description
     */
    public ItemHolder(String textTitle, String textDescription) {
        this.textTitle = textTitle;
        this.textDescription = textDescription;
        this.hasDivider = true;
    }

    /**
     * Item constructor with adapter
     */
    public ItemHolder(String textTitle, String textDescription, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.textTitle = textTitle;
        this.textDescription = textDescription;
        this.hasDivider = true;
        this.adapter = adapter;
    }

    /**
     * Setter of the adapter
     * @param adapter : the adapter
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
    }

    /**
     * Setter of the divider's item
     */
    public void removeDivider() {
        this.hasDivider = false;
    }

    /**
     * Getter of the item title
     * @return item title
     */
    public String getTextTitle() {
        return textTitle;
    }

    /**
     * Getter of the item description
     * @return item description
     */
    public String getTextDescription() {
        return textDescription;
    }

    /**
     * Getter of the item's divider
     * @return true if the item has divider. Else -> false
     */
    public boolean isHasDivider() {
        return hasDivider;
    }

    /**
     * Bind of the item view
     * @param vh : the item view
     */
    public void bind(ViewHolder vh) {
        vh.tvItemName.setText(textTitle);
        vh.tvItemDesc.setText(textDescription);
        vh.vwDivider.setVisibility(hasDivider ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * nested class of the item view
     */
    public static class ViewHolder extends ChildViewHolder {

        /**
         * The item name
         */
        protected TextView tvItemName;

        /**
         * The item description
         */
        protected TextView tvItemDesc;

        /**
         * The divider view
         */
        protected View vwDivider;

        /**
         * The circle view
         */
        protected View vwCircle;

        /**
         * The icon view (in the circle view)
         */
        protected ImageView imgIco;

        /**
         * The relative layout of the item
         */
        protected RelativeLayout layBackItem;

        /**
         * Constructor of the view holder
         * @param v : the view holder
         */
        public ViewHolder(View v) {
            super(v);

            tvItemName = (TextView) v.findViewById(R.id.tvGenericFirstLine);
            tvItemDesc = (TextView) v.findViewById(R.id.tvGenericSecondLine);
            vwDivider = v.findViewById(R.id.viewGenericDivider);
            imgIco = (ImageView) itemView.findViewById(R.id.imgGenericIco);
            vwCircle = itemView.findViewById(R.id.viewGenericCircle);

            // Listener
            layBackItem = (RelativeLayout) itemView.findViewById(R.id.layBackItem);
        }

        /**
         * Getter of the relative layout
         * @return the relative layout
         */
        public RelativeLayout getBackViewListener() {
            return layBackItem;
        }

        /**
         * Getter of the icon view (in the circle view)
         * @return the icon view
         */
        public ImageView getImgIco() {
            return imgIco;
        }

        /**
         * Getter of the circle view
         * @return the circle view
         */
        public View getVwCircle() {
            return vwCircle;
        }
    }
}
