/**
 * Copyright Murex S.A.S., 2003-2013. All Rights Reserved.
 * <p/>
 * This software program is proprietary and confidential to Murex S.A.S and its affiliates ("Murex") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 * express prior written consent of Murex and subject to the applicable Murex licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package org.globsframework.utils;

public class NanoChrono {
    private long start;

    public NanoChrono() {
        start = System.nanoTime();
    }

    public static NanoChrono start() {
        return new NanoChrono();
    }

    public void reset() {
        start = System.nanoTime();
    }

    public double getElapsedTimeInMS() {
        return ((int) ((System.nanoTime() - start) / 1000.)) / 1000.;
    }
}
