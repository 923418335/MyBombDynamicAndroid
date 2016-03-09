package madan.com.test.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import madan.com.test.R;
import madan.com.test.project.base.BaseActivity;

public class EditUserMessageActivity extends BaseActivity {
    private EditText mEditUserMessageInput;
    String request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_message);
        initToolBar();
        mEditUserMessageInput = (EditText) findViewById(R.id.mEditUserMessageInput);
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        request = intent.getStringExtra("request");
        getSupportActionBar().setTitle(request);
        toolbar.setNavigationIcon(R.drawable.navigation_back_arrow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //返回
                finish();
                break;
            case R.id.complete:
                //提交
                String result = mEditUserMessageInput.getText().toString();
                if(result.isEmpty()){
                    if(request.equals("用户昵称")) {
                        toast("昵称不许为空");
                        return true;
                    }else{
                        result = "";
                    }
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("data", result);
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
