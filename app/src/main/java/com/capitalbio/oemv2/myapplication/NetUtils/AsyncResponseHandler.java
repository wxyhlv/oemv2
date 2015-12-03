package com.capitalbio.oemv2.myapplication.NetUtils;

import com.loopj.android.http.ResponseHandlerInterface;

import java.io.IOException;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * Created by chengkun on 15-11-19.
 */
public class AsyncResponseHandler implements ResponseHandlerInterface {
    @Override
    public void sendResponseMessage(HttpResponse response) throws IOException {

    }

    @Override
    public void sendStartMessage() {

    }

    @Override
    public void sendFinishMessage() {

    }

    @Override
    public void sendProgressMessage(long bytesWritten, long bytesTotal) {

    }

    @Override
    public void sendCancelMessage() {

    }

    @Override
    public void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBody) {

    }

    @Override
    public void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }

    @Override
    public void sendRetryMessage(int retryNo) {

    }

    @Override
    public URI getRequestURI() {
        return null;
    }

    @Override
    public void setRequestURI(URI requestURI) {

    }

    @Override
    public Header[] getRequestHeaders() {
        return new Header[0];
    }

    @Override
    public void setRequestHeaders(Header[] requestHeaders) {

    }

    @Override
    public boolean getUseSynchronousMode() {
        return false;
    }

    @Override
    public void setUseSynchronousMode(boolean useSynchronousMode) {

    }

    @Override
    public boolean getUsePoolThread() {
        return false;
    }

    @Override
    public void setUsePoolThread(boolean usePoolThread) {

    }

    @Override
    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

    }

    @Override
    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

    }

    @Override
    public Object getTag() {
        return null;
    }

    @Override
    public void setTag(Object TAG) {

    }
}
