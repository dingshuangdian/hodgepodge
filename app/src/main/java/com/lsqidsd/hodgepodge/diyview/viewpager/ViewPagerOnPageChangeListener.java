package com.lsqidsd.hodgepodge.diyview.viewpager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.bean.NewsHot;

import java.util.List;

public class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private List<NewsHot.DataBean> newsHotList;
    private Context context;

    public ViewPagerOnPageChangeListener(List<NewsHot.DataBean> newsHotList, Context context) {
        this.newsHotList = newsHotList;
        this.context = context;
    }

    int currPosition = 0; // 当前滑动到了哪一页
    boolean canJump = false;
    boolean canLeft = true;

    boolean isObjAnmatitor = true;
    boolean isObjAnmatitor2 = false;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == (newsHotList.size() - 1)) {
            if (positionOffset > 0.35) {
                canJump = true;
                if (imageAdapter.arrowImage != null && imageAdapter.slideText != null) {
                    if (isObjAnmatitor) {
                        isObjAnmatitor = false;
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imageAdapter.arrowImage, "rotation", 0f, 180f);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                imageAdapter.slideText.setText("松开跳到详情");
                                isObjAnmatitor2 = true;
                            }
                        });
                        animator.setDuration(500).start();
                    }
                }
            } else if (positionOffset <= 0.35 && positionOffset > 0) {
                canJump = false;
                if (imageAdapter.arrowImage != null && imageAdapter.slideText != null) {
                    if (isObjAnmatitor2) {
                        isObjAnmatitor2 = false;
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imageAdapter.arrowImage, "rotation", 180f, 360f);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                imageAdapter.slideText.setText("继续滑动跳到详情");
                                isObjAnmatitor = true;
                            }
                        });
                        animator.setDuration(500).start();
                    }
                }
            }
            canLeft = false;
        } else {
            canLeft = true;
        }

    }

    @Override
    public void onPageSelected(int position) {
        currPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (currPosition == (images.length - 1) && !canLeft) {
            if (state == ViewPager.SCROLL_STATE_SETTLING) {

                if (canJump) {
                    Toast.makeText(MainActivity.this, "跳转啦", Toast.LENGTH_SHORT).show();
                }

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // 在handler里调用setCurrentItem才有效
                        viewPager.setCurrentItem(images.length - 1);
                    }
                });

            }
        }
    }
}
