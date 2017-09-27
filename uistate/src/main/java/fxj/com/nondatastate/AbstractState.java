package fxj.com.nondatastate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaojian.fu on 2017/9/26.
 */

public abstract class AbstractState implements State {

    private View stateView;

    protected abstract View onCreateView(LayoutInflater inflater,ViewGroup parent);

    @Override
    public void show(ViewGroup parent) {
        if(stateView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            stateView = onCreateView(inflater,parent);
        }
        parent.addView(stateView);
    }
}
