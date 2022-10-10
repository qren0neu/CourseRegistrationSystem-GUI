package com.qiren.project.ui.interfaces;

import com.qiren.project.ui.core.Intend;

public interface PageEventListener {
    void onBackToMain(Intend intend);

    void onChangePage(Intend intend);

    void onGoBack(Intend intend);

    void onPageRefresh(Intend intend);
}
