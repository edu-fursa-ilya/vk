package dev.android.fursa.vk.ui.fragment;

import android.content.Context;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class BaseFragment extends MvpAppCompatFragment {

    public String createToolbarTitle(Context context) {
        return context.getString(onCreateToolbarTitle());
    }

    @StringRes
    public abstract int onCreateToolbarTitle();
}
