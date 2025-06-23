package com.chetaru.tribe365_new.Adapter;

import com.chetaru.tribe365_new.API.Models.BeliefValue;

/**
 * Created by Prakhar on 2/11/2017.
 */
public interface OnItemClickCustom {
    void onClick(int id, int position, Object object);

    public void dotValueLongClick(BeliefValue beliefValue);
}
