package com.example.administrator.weishi.chapter02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.administrator.weishi.R;

/**
 * Created by Administrator on 2019/4/2.
 */

public abstract class BaseSetUpActivity extends Activity {
    public SharedPreferences sp;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        //GestureDetector监听手势发生
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
         //e1表示手指第一次触摸屏幕的事件，e2代表的是手指离开屏幕的事件
         //velocityX:水平方向的速度，单位是pix/s，velocityY垂直方向
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //1秒内移动范围小于200dp
                if (Math.abs(velocityX)<200){
                   Toast.makeText(getApplicationContext(),"移速过慢，动作无效",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if((e2.getRawX()-e1.getRawX())>200){
                    //从左到右滑动屏幕，显示上一个屏幕
                    showPre();
                    //overridePendingTransition动画切换函数
                    overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
                    return true;
                }
                if((e1.getRawX()-e2.getRawX())>200){
                    showNext();
                    overridePendingTransition(R.anim.next_in,R.anim.next_out);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    public abstract void showPre();
    public abstract void showNext();

    //用手势识别器识别事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //开启新的Activity且关闭自己
   public void startActivityAndFinishSelf(Class<?>cls){
       Intent intent = new Intent(this,cls);
       startActivity(intent);
       finish();
   }
}
