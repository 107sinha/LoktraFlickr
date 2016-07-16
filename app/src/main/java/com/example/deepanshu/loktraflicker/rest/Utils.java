package com.example.deepanshu.loktraflicker.rest;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by deepanshu on 15/7/16.
 */
public final class Utils {
    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }
}
