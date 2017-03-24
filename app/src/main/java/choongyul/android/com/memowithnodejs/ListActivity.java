package choongyul.android.com.memowithnodejs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import choongyul.android.com.memowithnodejs.domain.Data;
import choongyul.android.com.memowithnodejs.domain.Qna;

public class ListActivity extends AppCompatActivity {

    private static final int RETURNCODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, WriteActivity.class);
//            startActivityForResult(intent,RETURNCODE);
            startActivity(intent);
        });

        setList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    ListView listView;
    CustomAdapter adapter;
    List<Qna> datas;

    private void setList() {
        listView = (ListView) findViewById(R.id.listView);
        DataStore dataStore = DataStore.getInstance();
        datas = dataStore.getDatas();
        adapter = new CustomAdapter(this, datas);
        listView.setAdapter(adapter);
    }

}
class CustomAdapter extends BaseAdapter {
    List<Qna> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context , List<Qna> datas) {
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        Qna qna = datas.get(position);
        TextView tvTitle, tvName;
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvName = (TextView) convertView.findViewById(R.id.tvName);

        tvTitle.setText(qna.getTitle());
        tvName.setText(qna.getName());

        return convertView;
    }
}
