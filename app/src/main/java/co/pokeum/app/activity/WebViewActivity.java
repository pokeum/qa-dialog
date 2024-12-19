package co.pokeum.app.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import co.pokeum.inputs.dialog.InputsDialog;
import co.pokeum.inputs.dialog.InputsDialogInterface;
import co.pokeum.inputs.dialog.model.InputModel;
import co.pokeum.inputs.dialog.model.InputModelBuilder;
import co.pokeum.inputs.dialog.model.InputModelType;
import co.pokeum.inputs.dialog.model.ResultModel;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);

        //webView.setWebChromeClient(new WebChromeClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {
                final String key = "JsPrompt";
                List<InputModel> inputModels = new ArrayList<InputModel>() {{
                    add(new InputModelBuilder(key, InputModelType.KeyValue)
                            .hideLabel()
                            .setDefaultValue(defaultValue)
                            .build());
                }};
                Dialog inputsDialog = new InputsDialog.Builder(view.getContext())
                        .setTitle(message)
                        .setInputs(inputModels)
                        .setListener(new InputsDialogInterface.OnClickListener() {

                            @Override
                            public void onPositiveButtonClick(DialogInterface dialog, int id, @NonNull String result) {
                                jsPromptResult.confirm(ResultModel.getValue(result, key));
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick(DialogInterface dialog, int id) {
                                jsPromptResult.cancel();
                                dialog.dismiss();
                            }
                        })
                        .build();
                inputsDialog.setOnCancelListener(dialog -> jsPromptResult.cancel());
                inputsDialog.show();

                return true;
            }
        });

        String unencodedHtml = readFileFromAssets("web_application.html");
        if (unencodedHtml != null) {
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(), Base64.NO_PADDING);
            webView.loadData(encodedHtml, "text/html", "base64");
        }
    }

    private String readFileFromAssets(String filename) {
        try {
            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, WebViewActivity.class);
    }
}