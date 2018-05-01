package com.example.priyamittal.customfileexplorerdialog;

import android.app.Dialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonOpenDialog;
    Button buttonUp;
    TextView textFolder;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;

    ListView dialog_ListView;

    File root;
    File curFolder;

    private List<String> fileList = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOpenDialog = (Button)findViewById(R.id.opendialog);
        buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                showDialog(CUSTOM_DIALOG_ID);
            }});

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        curFolder = root;

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(android.R.layout.select_dialog_item);
                dialog.setTitle("Custom Dialog");

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView)dialog.findViewById(R.id.folder);

                buttonUp = (Button)dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
                        ListDir(curFolder.getParentFile());
                    }});

//Prepare ListView in dialog
                dialog_ListView = (ListView)dialog.findViewById(R.id.dialoglist);

                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()){
                            ListDir(selected);
                        }else {
                            Toast.makeText(MainActivity.this,selected.toString() + " selected",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }

                    }});

                break;
        }

        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
// TODO Auto-generated method stub
        super.onPrepareDialog(id, dialog, bundle);

        switch(id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }

    }

    void ListDir(File f){

        if(f.equals(root)){
            buttonUp.setEnabled(false);
        }else{
            buttonUp.setEnabled(true);
        }

        curFolder = f;
        textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
    }
    }

