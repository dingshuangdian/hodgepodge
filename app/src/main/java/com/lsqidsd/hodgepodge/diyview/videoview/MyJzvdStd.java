package com.lsqidsd.hodgepodge.diyview.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import com.lsqidsd.hodgepodge.R;
public class MyJzvdStd extends JzvdStd {
    public MyJzvdStd(Context context) {
        super(context);
    }
    public static boolean AUTOPLAY = false;
    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
                    Toast.makeText(getContext(), getResources().getString(R.string.nourl), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentState == CURRENT_STATE_NORMAL) {
                    startVideo();
                    onEvent(JZUserAction.ON_CLICK_START_ICON);
                    AUTOPLAY = true;
                } else if (currentState == CURRENT_STATE_PLAYING) {
                    onEvent(JZUserAction.ON_CLICK_PAUSE);
                    JZMediaManager.pause();
                    onStatePause();
                    AUTOPLAY = false;
                } else if (currentState == CURRENT_STATE_PAUSE) {
                    onEvent(JZUserAction.ON_CLICK_RESUME);
                    JZMediaManager.start();
                    onStatePlaying();
                    AUTOPLAY = true;
                } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                    onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
                    startVideo();
                }
                break;
            default:
                super.onClick(v);
                break;
        }

    }
}
