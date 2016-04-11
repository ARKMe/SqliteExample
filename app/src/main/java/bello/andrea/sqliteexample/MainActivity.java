package bello.andrea.sqliteexample;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;

    ArrayAdapter<Comment> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                values
        );
        setListAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] comments = new String[]{"Cool", "Very nice", "Top"};
                int nextInt = new Random().nextInt(3);
                Comment comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
            }
        });

    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Comment comment = (Comment) getListAdapter().getItem(position);
        datasource.deleteComment(comment);
        adapter.remove(comment);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}

