package com.example.donghyunlee.project2w;

import com.squareup.otto.Bus;

/**
 * Created by DONGHYUNLEE on 2017-07-15.
 */

public final class BusProvider {

    private static final Bus BUS = new Bus();
    private BusProvider(){}
    public static Bus getInstance(){ return BUS;}
}
