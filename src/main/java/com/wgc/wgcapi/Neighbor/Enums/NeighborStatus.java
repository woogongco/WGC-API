package com.wgc.wgcapi.Neighbor.Enums;
/*
Created on 2023/06/26 11:14 PM In Intelli J IDEA 
by jeon-wangi
*/

public enum NeighborStatus {

    ACCEPT('Y'), REFUSE('N'), DELETE('D'), HOLD('R');

    private final Character value;

    NeighborStatus(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
