package com.wgc.wgcapi.Neighbor.Enums;
/*
Created on 2023/06/26 11:14 PM In Intelli J IDEA 
by jeon-wangi
*/

public enum NeighborStatus {

    ACCEPT('Y'),
    REFUSE('N'),
    HOLD('R');

    private final Character vale;

    NeighborStatus(Character vale) {
        this.vale = vale;
    }

    public Character vale() {
        return vale;
    }
}
