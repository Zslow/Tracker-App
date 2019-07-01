package com.example.tracker;

import java.util.List;

public interface DownloadCallback {
    void onSuccess(List itemList);
    void onFailure(Exception exception);
}
