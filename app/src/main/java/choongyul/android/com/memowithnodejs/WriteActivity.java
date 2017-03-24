package choongyul.android.com.memowithnodejs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import choongyul.android.com.memowithnodejs.domain.Data;
import choongyul.android.com.memowithnodejs.domain.Qna;
import io.reactivex.Observable;

import static choongyul.android.com.memowithnodejs.MainActivity.SITE_URL;

public class WriteActivity extends AppCompatActivity {

    EditText etTitle, etName, etContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContent = (EditText) findViewById(R.id.etContent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
                    // 입력값 , 진행상태, 결과값
            AsyncTask<String, Void, String> networkTask = new AsyncTask<String, Void, String>(){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    String title = params[0];
                    String name = params[1];
                    String content = params[2];

                    Qna qna = new Qna();
                    qna.setTitle(title);
                    qna.setName(name);
                    qna.setContent(content);

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(qna);

                    String result = Remote.postJson(SITE_URL+"post", jsonString);

                    // 아래 이프문은 데이터가 변경되었을때 listactivity에서 업데이트하려고 작성해놓은것이다.
                    if("SUCCESS".equals(result)) {
                        // 성공적으로 등록하면 내가 쓴 글 목록에 더해준다.
                        DataStore dataStore = DataStore.getInstance();
                        dataStore.addData(qna);
                    }

                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
//                    Snackbar.make(view, result, Snackbar.LENGTH_LONG).show();
                    Toast.makeText(WriteActivity.this, "result", Toast.LENGTH_SHORT).show();
//                    ResetData.getData();
                    finish();
                }
            };

            networkTask.execute(
                    etTitle.getText() + ""
                    ,etName.getText() + ""
                    ,etContent.getText() + "");
        });
    }

}
