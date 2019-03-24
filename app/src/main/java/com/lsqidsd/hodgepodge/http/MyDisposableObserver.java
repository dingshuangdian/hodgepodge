package com.lsqidsd.hodgepodge.http;
import android.app.ProgressDialog;
import android.content.Context;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;
/**
 * 显示ProgressDialog的封装
 *
 * @param <T>
 */
public class MyDisposableObserver<T> extends DisposableObserver<T> {
    private ProgressDialog progressDialog;
    private HttpOnNextListener listener;
    private Context context;
    private RefreshLayout refreshLayout;

    public MyDisposableObserver(RefreshLayout refreshLayout, Context context, boolean showProgress, HttpOnNextListener onNextListener) {
        this.context = context;
        this.listener = onNextListener;
        this.refreshLayout = refreshLayout;
        showProgressDialog(showProgress);
    }

    public MyDisposableObserver(HttpOnNextListener onNextListener) {
        this.listener = onNextListener;
    }


    private void showProgressDialog(boolean showProgress) {
        if (showProgress) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
            }
            progressDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        if (listener != null) {
            listener.onSuccess(t);
            dismissProgressDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        String error = null;
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                error = "网络连接超时";
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                error = "安全证书异常";
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    error = "网络异常，请检查您的网络状态";
                } else if (code == 404) {
                    error = "请求的地址不存在";
                } else {
                    error = "请求失败";
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                error = "域名解析失败";
            } else {
                error = "error:" + e.getMessage();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            dismissProgressDialog();
            progressDialog = null;
        }
        if (listener != null) {
            listener.onFail(error);
            dismissProgressDialog();
        }
        if (refreshLayout != null) {
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }
}
