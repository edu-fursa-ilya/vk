package dev.android.fursa.vk.ui.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import dev.android.fursa.vk.ui.fragment.BaseFragment;

public class BaseActivity extends MvpAppCompatActivity {

    public void fragmentOnScreen(BaseFragment fragment) {
        setToolbarTitle(fragment.createToolbarTitle(this));
    }

    private void setToolbarTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
