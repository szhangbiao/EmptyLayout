package com.custom.emptylayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhangbiao on 2018/2/23.
 */

public class FrameEmptyActivity extends AppCompatActivity {
    private FrameEmptyLayout emptyLayout;
    private List<Integer> skipId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_frame);
        emptyLayout=findViewById(R.id.fel_parent);
        emptyLayout.setRetryListener(new FrameEmptyLayout.OnRetryClickListener() {
            @Override
            public void onClick() {
                emptyLayout.showContent();
            }
        });
        skipId=new ArrayList<>();
        skipId.add(R.id.tv_header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frame, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
         * method stub
         */
        switch(item.getItemId()) //得到被点击的item的itemId
        {
            case  R.id.menu_loading:
                emptyLayout.showLoading();
                break;
            case  R.id.menu_normal:
                emptyLayout.showContent();
                break;
            case  R.id.menu_empty:
                emptyLayout.showEmpty(R.drawable.ic_launcher_foreground,"暂无数据！");
                break;
            case  R.id.menu_error:
                emptyLayout.showError(R.drawable.net_error,"不知道什么原因加载出错了！","点击重试");
                break;
            case  R.id.menu_net_error:
                emptyLayout.showError(R.drawable.net_error,"不知道什么原因加载出错了！",null);
                break;
        }
        return true;
    }
}
