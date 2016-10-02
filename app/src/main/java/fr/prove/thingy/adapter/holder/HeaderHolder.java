/**
 * @file HeaderHolder.java
 * @brief Abstract model of Header holder (parent)
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
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import fr.prove.thingy.R;

public abstract class HeaderHolder implements ParentListItem {

    /**
     * The text name of the header
     */
    private String textName;

    /**
     * The adapter
     */
    protected RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    /**
     * Constructor of the header holder (with the adapter)
     * @param textName : the name of the header
     * @param adapter : the adapter used
     */
    public HeaderHolder(String textName, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.textName = textName;
        this.adapter=adapter;
    }

    /**
     * Constructor  of the header holder (without the adapter)
     * @param textName : the name of the header
     */
    public HeaderHolder(String textName) {
        this.textName = textName;
    }

    /**
     * set the adapter of the header
     * @param adapter the adapter used
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
    }

    /**
     * The bind of the view
     * @param vh : the view holder
     */
    public void bind(ViewHolder vh) {
        vh.tvHeaderName.setText(textName);
    }

    /**
     * Getter of the initial expand
     * @return the initial expand
     */
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    /**
     * nested class of the header view
     */
    public static class ViewHolder extends ParentViewHolder {

        /**
         * The initial position of the arrow
         */
        private static final float INITIAL_POSITION = 0.0f;

        /**
         * The rotated position of the arrow
         */
        private static final float ROTATED_POSITION = 180f;

        /**
         * The arrow (on the left) of the header
         */
        private ImageView mArrowExpandImageView;

        /**
         * The header name to display
         */
        private TextView tvHeaderName;

        /**
         * The circle view (when edit mode)
         */
        protected View vwCircle;

        /**
         * The image view (when edit mode)
         */
        protected ImageView imgIco;

        /**
         * Constructor of the view holder
         * @param v : the view
         */
        public ViewHolder(View v) {
            super(v);

            tvHeaderName = (TextView) v.findViewById(R.id.tvGenericHeader);
            mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);

            imgIco = (ImageView) v.findViewById(R.id.imgGenericIco);
            vwCircle = v.findViewById(R.id.viewGenericCircle);

        }

        /**
         * Called when the user tap on the header
         * @param expanded : state of the list. true -> expanded / false -> not expanded
         */
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (expanded) {
                mArrowExpandImageView.setRotation(ROTATED_POSITION);
            } else {
                mArrowExpandImageView.setRotation(INITIAL_POSITION);
            }
        }

        /**
         * Called when the user tap on the header (manage the rotation)
         * @param expanded : state of the arrow
         */
        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mArrowExpandImageView.startAnimation(rotateAnimation);
        }

        /**
         * Getter of the arrow view (on the left)
         * @return the arrow
         */
        public ImageView getArrow() {
            return mArrowExpandImageView;
        }

        /**
         * Getter of the image view (in the circle view)
         * @return the image icon
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

        /**
         * Getter of the image (arrow) on the left of the header
         * @return the arrow view
         */
        public ImageView getmArrowExpandImageView(){
            return mArrowExpandImageView;
        }
    }
}
