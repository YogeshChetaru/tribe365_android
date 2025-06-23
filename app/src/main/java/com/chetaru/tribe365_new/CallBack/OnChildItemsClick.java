package com.chetaru.tribe365_new.CallBack;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;

public interface OnChildItemsClick {
    void onParentClick(int id, int position, HomeBelief belief);
    void onChildClick(int id, int position, Object object);
    void onSubChildClick(int id, int position, Object object);
    void onLongClick(int id, int position, HomeBelief belief);
}
