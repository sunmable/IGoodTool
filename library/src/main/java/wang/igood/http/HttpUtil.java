package cn.chaboshi.cbslibrary.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.chaboshi.cbslibrary.common.DialogUtil;

public class HttpUtil {

    //1：变量声明区域----------------------------------------------------------------------------------------------
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static String cookie;
    private static final int REQUESTSUCCESS = 1;
    private static final int REQUESTFAIL = 0;
    //2：视图生命周期----------------------------------------------------------------------------------------------
    static {
        SSLContext sslContext;
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted( X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted( X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    //3：视图逻辑-------------------------------------------------------------------------------------------------
    //4：业务逻辑-------------------------------------------------------------------------------------------------
    /**<a>初始化请求时间及SSL</a>*/
    /**<a>Post请求</a>**/
    public static boolean post(String url, HashMap<String,String> args, final HttpResponse response,final Context context){
        final Activity activity = (Activity) context;
        Dialog loaddingDialog = null;
        if(activity != null){
            loaddingDialog = DialogUtil.loadingDialog(activity);
        }
        final Dialog finalLoaddingDialog = loaddingDialog;
        Callback callback = new Callback() {
            @Override
            public void onFailure(final Request request_, final IOException e) {
                if(activity == null){
                    return;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalLoaddingDialog != null)
                            finalLoaddingDialog.dismiss();
                        Toast.makeText(activity,"当前网络不稳定，请检测网络。",Toast.LENGTH_LONG).show();
                        response.onFailure(request_,e.getMessage());
                    }
                });
            }
            @Override
            public void onResponse(final Response response_) throws IOException {
                final String body = response_.body().string();
                if(activity == null)
                    return ;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(body)){
                            response.onFailure(null,"返回数据为空");
                        }else{
                            response.onResponse(response_,body);
                        }
                        try{
                            if(finalLoaddingDialog != null)
                                finalLoaddingDialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        };

        FormEncodingBuilder builder = new FormEncodingBuilder();
        if(args != null){
            Iterator iter = args.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = entry.getValue().toString();
                builder.add(key,val);
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        initHttpHead(requestBuilder,8,activity);
        Request requestHttp = requestBuilder.url(url).post(builder.build()).build();
        mOkHttpClient.newCall(requestHttp).enqueue(callback);
        if(finalLoaddingDialog != null){
            finalLoaddingDialog.show();
        }

        return true;
    }

    /**获取Cookie*/
    public static String getCookie(){
        return cookie;
    }

    /**设置Cookie*/
    public static void setCookie(String cookie){
        HttpUtil.cookie = cookie;
    }

    /**<a>Get请求</a>**/
    public static boolean get(String url, HashMap<String,String> args, final HttpResponse response_,final Context activity){

        Dialog loaddingDialog = null;
        if(activity != null){
            loaddingDialog = DialogUtil.loadingDialog(activity);
        }
        final Dialog finalLoaddingDialog = loaddingDialog;

        if(args != null && args.size() > 0){
            StringBuffer argBuffer = new StringBuffer();
            Iterator iter = args.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = entry.getValue().toString();
                argBuffer.append(key + " = " + val + "&");
            }
            String param = argBuffer.subSequence(0,argBuffer.length() - 1).toString();
            url = url.contains("?")?("&"+param):("?"+param);
        }

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                response_.onFailure(request,e.getMessage());
                if(finalLoaddingDialog != null)
                    finalLoaddingDialog.dismiss();
                Toast.makeText(activity,"当前网络不稳定，请检测网络。",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                if(!TextUtils.isEmpty(body)){
                    response_.onResponse(response,body);
                }else{
                    response_.onFailure(null,"返回数据为空");
                }
                if(finalLoaddingDialog != null)
                    finalLoaddingDialog.dismiss();
            }
        };


        Request.Builder requestBuilder = new Request.Builder();
        initHttpHead(requestBuilder,8,activity);
        Request request = requestBuilder.url(url).get().build();
        mOkHttpClient.newCall(request).enqueue(callback);
        if(finalLoaddingDialog != null)
            finalLoaddingDialog.show();

        return true;
    }

    /**批量上传图片**/
    public static boolean uploadPics(String url, HashMap<String,Object> args, final HttpResponse response_,Context context){

        Activity activity = (Activity) context;
        Dialog loaddingDialog = null;
        if(activity != null){
            loaddingDialog = DialogUtil.loadingDialog(activity);
        }
        final Dialog finalLoaddingDialog = loaddingDialog;

        Callback callback = new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                response_.onFailure(request,e.getMessage());
                if(finalLoaddingDialog != null)
                    finalLoaddingDialog.dismiss();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                if(!TextUtils.isEmpty(body)){
                    response_.onResponse(response,body);
                }else{
                    response_.onFailure(null,"返回数据为空");
                }

                if(finalLoaddingDialog != null)
                    finalLoaddingDialog.dismiss();
            }
        };

        MultipartBuilder builder = new MultipartBuilder();
        builder.type(MultipartBuilder.FORM);
        for (String key : args.keySet()) {
            Object object = args.get(key);
            if (!(object instanceof File)) {
                builder.addFormDataPart(key, object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart("file", file.getName(), RequestBody.create(null, file));
            }
        }
        RequestBody body = builder.build();

        Request.Builder requestBuilder = new Request.Builder();
        initHttpHead(requestBuilder,200,context);
        Request request = requestBuilder.url(url).post(body).build();
        mOkHttpClient.newCall(request).enqueue(callback);

        if(finalLoaddingDialog != null)
            finalLoaddingDialog.show();

        return true;
    }
    //5：内部声明-------------------------------------------------------------------------------------------------
    /**初始化请求头**/
    private static void initHttpHead(Request.Builder builder,int time,Context context){
        builder.addHeader("User-Agent", "chaboshiAPP");
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        try{
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = TelephonyMgr.getDeviceId()+"0987654321123456";
            String szImei = deviceId.substring(0,16);
            String token = AESUtil.aesEncryptString(""+System.currentTimeMillis(),szImei) ;
            builder.addHeader("TOKEN",szImei+token);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!TextUtils.isEmpty(cookie)){
            builder.addHeader("Set-Cookie",cookie);
        }
        mOkHttpClient.setConnectTimeout(time,TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(time,TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(time,TimeUnit.SECONDS);
    }
    //6：内部类---------------------------------------------------------------------------------------------------

    public interface HttpResponse{
        void onFailure(Request request, String failMessage);
        void onResponse(Response response, String data);
    }
}
