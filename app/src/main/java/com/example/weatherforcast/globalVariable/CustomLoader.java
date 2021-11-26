package com.example.weatherforcast.globalVariable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.weatherforcast.R;

public class CustomLoader {
    private static final String TAG = CustomLoader.class.getSimpleName();
    public static CustomLoader customLoader;
    private static Context context;
    private static Dialog m_Dialog;

    public CustomLoader(Context context) {
        CustomLoader.context = context;
    }

    public CustomLoader(Activity context) {
        CustomLoader.context = context;
    }

    public void showProgress() {
        try {
            m_Dialog = new Dialog(context, R.style.BottomDialog);
            m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = View.inflate(context, R.layout.loader, null);
//            ImageView imageView = view.findViewById(R.id.loadingImageView);
//            Glide.with(context).load(R.drawable.loading_gif).into(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            m_Dialog.getWindow().setWindowAnimations(R.style.LoaderAnimation);
            m_Dialog.addContentView(view, params);
            m_Dialog.setCancelable(false);
            m_Dialog.setCanceledOnTouchOutside(false);
            m_Dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            if (m_Dialog != null) {
                if (m_Dialog.isShowing()) {
                    m_Dialog.dismiss();
                } else {
                    try {
                        m_Dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
