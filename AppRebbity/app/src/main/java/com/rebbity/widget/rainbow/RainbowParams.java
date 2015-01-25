package com.rebbity.widget.rainbow;

import android.os.SystemClock;

/**
 * Created by Tyler on 15/1/2.
 */
public class RainbowParams {
    public long time; // have passed time.
    public int timePieceI; // cal now is in which time piece.
    public long timeDelta; // delta between now time and time piece start.
    public float rate; // time rate.

    public static void getParams(RainbowParams params, long start, long offset){
        params.time = SystemClock.uptimeMillis() - start;
        params.timePieceI = (int)Math.floor((double)params.time / (double)offset);
        params.timeDelta = params.time - offset * (long)params.timePieceI;
        params.rate = (float)params.timeDelta / (float)offset;
    }
}
