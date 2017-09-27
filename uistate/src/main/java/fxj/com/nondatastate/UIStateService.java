package fxj.com.nondatastate;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import junit.framework.Assert;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaojian.fu on 2017/9/26.
 */

public class UIStateService {
    private Map<String,State> stateMaps = new HashMap<>();
    private View contentView;
    private ViewGroup nonDataStateContainerView;
    private Context context;

    private UIStateService(Builder builder){
        stateMaps.putAll(builder.stateMaps);
    }

    public View bind(View target){
        Assert.assertNotNull("target 不能为null",target);
        contentView = target;
        context = target.getContext();
        ViewParent parent = target.getParent();
        nonDataStateContainerView = new FrameLayout(context);
        nonDataStateContainerView.setLayoutParams(contentView.getLayoutParams());
        if(parent!=null){
            ViewGroup parentView = (ViewGroup) parent;
            int targetIndex = parentView.indexOfChild(target);
            parentView.removeView(contentView);
            parentView.addView(nonDataStateContainerView,targetIndex);
        }
        return nonDataStateContainerView;
    }

    public void showStateSafe(final String stateName){
        if(Looper.myLooper()==Looper.getMainLooper()){
            showState(stateName);
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                     showState(stateName);
                }
            });
        }
    }

    public void recoverToNormalStateSafe(){
        if(Looper.myLooper()==Looper.getMainLooper()){
            recoverToNormalState();
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    recoverToNormalState();
                }
            });
        }
    }

    private void recoverToNormalState() {
        if(null != nonDataStateContainerView){
            nonDataStateContainerView.removeAllViews();
            nonDataStateContainerView.addView(contentView);
            contentView.setVisibility(View.VISIBLE);
        }
    }

    private void showState(String stateName) {
        State state = stateMaps.get(stateName);
        if(state!=null){
            nonDataStateContainerView.removeAllViews();
            nonDataStateContainerView.addView(contentView);
            contentView.setVisibility(View.GONE);
            state.show(nonDataStateContainerView);
        }else{
            throw new IllegalArgumentException("没有对应的stateName");
        }
    }

    public static class Builder{
        private Map<String,State> stateMaps = new HashMap<>();

        public Builder injectState(String stateName,State state){
            Assert.assertNotNull(stateName);
            Assert.assertNotNull(state);
            stateMaps.put(stateName,state);
            return this;
        }

        public UIStateService build(){
            return new UIStateService(this);
        }
    }
}
