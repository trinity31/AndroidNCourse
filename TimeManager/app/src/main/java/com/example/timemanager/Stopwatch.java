package com.example.timemanager;

/*
 *  Copyright 2006 Corey Goldberg (cgoldberg _at_ gmail.com)
 *
 *  This file is part of NetPlot.
 *
 *  NetPlot is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  NetPlot is distributed in the hope that it will be useful,
 *  but without any warranty; without even the implied warranty of
 *  merchantability or fitness for a particular purpose.  See the
 *  GNU General Public License for more details.
 */


import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;
	private long currentTime = 0;
    private boolean running = false;
    final static String TAG = "Stopwatch";

    public long start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
        return startTime;
    }

    public long stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
        return stopTime;
    }

    // elaspsed time in milliseconds
    public long getElapsedTime() {
        if (running) {
			this.currentTime = System.currentTimeMillis();
            return currentTime - startTime;
        }
        return stopTime - startTime;
    }

    // elaspsed time in seconds
    public long getElapsedTimeSecs() {
        if (running) {
            return ((System.currentTimeMillis() - startTime) / 1000);
        }
        return ((stopTime - startTime) / 1000);
    }

    // sample usage
    public static void main(String[] args) {
        Stopwatch s = new Stopwatch();
        s.start();
        // code you want to time goes here
        s.stop();
        System.out.println("elapsed time in milliseconds: " + s.getElapsedTime());
    }

}