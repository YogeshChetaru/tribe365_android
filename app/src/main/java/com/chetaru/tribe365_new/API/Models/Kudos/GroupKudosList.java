package com.chetaru.tribe365_new.API.Models.Kudos;

import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;

import java.util.List;

public class GroupKudosList {
    String keyDescription;
    List<KudosAwardList> kudosAwardLists;

    public String getKeyDescription() {
        return keyDescription;
    }

    public void setKeyDescription(String keyDescription) {
        this.keyDescription = keyDescription;
    }

    public List<KudosAwardList> getKudosAwardLists() {
        return kudosAwardLists;
    }

    public void setKudosAwardLists(List<KudosAwardList> kudosAwardLists) {
        this.kudosAwardLists = kudosAwardLists;
    }
}
