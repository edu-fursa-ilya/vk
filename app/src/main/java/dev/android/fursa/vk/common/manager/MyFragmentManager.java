package dev.android.fursa.vk.common.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;
import dev.android.fursa.vk.ui.activity.BaseActivity;
import dev.android.fursa.vk.ui.fragment.BaseFragment;

public class MyFragmentManager {
    public static final int EMPTY_FRAGMENT_STACK_SIZE = 1;

    private Stack<BaseFragment> mFragmentStack = new Stack<>();
    private BaseFragment mCurrentFragment;

    public void setFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if(activity != null && !activity.isFinishing() && !isAlreadyExists(fragment)) {
            FragmentTransaction transaction = createAddTransaction(activity, fragment, false);
            transaction.replace(containerId, fragment);
            commitAddTransaction(activity, fragment, transaction, false);
        }
    }

    public void addFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if(activity != null && !activity.isFinishing() && !isAlreadyExists(fragment)) {
            FragmentTransaction transaction = createAddTransaction(activity, fragment, true);
            transaction.add(containerId, fragment);
            commitAddTransaction(activity, fragment, transaction, true); //here can be mistake
        }
    }

    public boolean removeCurrentFragment(BaseActivity activity) {
        return removeFragment(activity, mCurrentFragment);
    }

    public boolean removeFragment(BaseActivity activity, BaseFragment fragment) {
        boolean isReadyToRemove = fragment != null && mFragmentStack.size() > EMPTY_FRAGMENT_STACK_SIZE;

        if(isReadyToRemove) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            mFragmentStack.pop();
            mCurrentFragment = mFragmentStack.lastElement();
            transaction.remove(fragment);
            commitTransaction(activity, transaction);
        }

        return isReadyToRemove;
    }

    private FragmentTransaction createAddTransaction(BaseActivity activity, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if(addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }

        return fragmentTransaction;
    }

    private void commitAddTransaction(BaseActivity activity, BaseFragment fragment, FragmentTransaction fragmentTransaction, boolean addToBackStack) {
        if(fragmentTransaction != null) {
            mCurrentFragment = fragment;

            if(!addToBackStack) {
                mFragmentStack = new Stack<>();
            }

            mFragmentStack.add(fragment);
            commitTransaction(activity, fragmentTransaction);
        }
    }

    private void commitTransaction(BaseActivity activity, FragmentTransaction transaction) {
        transaction.commit();
        activity.fragmentOnScreen(mCurrentFragment);
    }

    public boolean isAlreadyExists(BaseFragment fragment) {
        if(fragment != null) {
            return false;
        }

        return mCurrentFragment != null && mCurrentFragment
                .getClass()
                .getName()
                .equals(fragment.getClass().getName());
    }
}
