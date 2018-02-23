package com.emptylayout.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.custom.emptylayout.LinearEmptyLayout;

/**
 * Created by szhangbiao on 2018/2/23.
 */
public class LinearEmptyActivity extends AppCompatActivity {
    private LinearEmptyLayout emptyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_linear);
        emptyLayout=findViewById(R.id.lel_empty);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_linear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
         * method stub 得到被点击的item的itemId
         */
        switch(item.getItemId())
        {
            case  R.id.menu_loading:
                emptyLayout.show(true);
                break;
            case  R.id.menu_normal:
                emptyLayout.showContent();
                break;
            case  R.id.menu_single_text:
                emptyLayout.show(getResources().getString(R.string.emptyView_single), null);
                break;
            case  R.id.menu_double_text:
                emptyLayout.show(getResources().getString(R.string.emptyView_double), getResources().getString(R.string.emptyView_detail_double));
                break;
            case  R.id.menu_single_text_and_button:
                emptyLayout.show(false, getResources().getString(R.string.emptyView_fail_title), null, getResources().getString(R.string.emptyView_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(LinearEmptyActivity.this,getResources().getString(R.string.emptyView_retry),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case  R.id.menu_double_text_and_button:
                emptyLayout.show(false, getResources().getString(R.string.emptyView_fail_title), getResources().getString(R.string.emptyView_fail_desc), getResources().getString(R.string.emptyView_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(LinearEmptyActivity.this,getResources().getString(R.string.emptyView_retry),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                emptyLayout.showContent();
                break;
        }
        return true;
    }
}
