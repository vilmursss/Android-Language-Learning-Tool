package com.example.android.wordgameandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Word> {


    Context context;
    int layoutResourceId;
    ArrayList<Word> data = new ArrayList<Word>();

    public ListAdapter(Context context, int layoutResourceId, ArrayList<Word> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    // Function for making a customized list item

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        CustomerHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CustomerHolder();
            holder.firstWord = (TextView) row.findViewById(R.id.first_word);
            holder.secondWord = (TextView) row.findViewById(R.id.second_word);
            holder.wordList = (TextView) row.findViewById(R.id.word_list);
            holder.btnEdit = (Button) row.findViewById(R.id.edit_button);
            row.setTag(holder);

        } else {

            holder = (CustomerHolder) row.getTag();

        }
        Word wordObject = data.get(position);
        holder.firstWord.setText(wordObject.getFirstWord());
        holder.secondWord.setText(wordObject.getSecondWord());
        holder.wordList.setText(wordObject.getWordList());

        final String btnSendId = String.valueOf(wordObject.getId());
        final String btnSendFirstWord = wordObject.getFirstWord();
        final String btnSendSecondWord = wordObject.getSecondWord();
        final String btnSendWordList = wordObject.getWordList();

        // Edit button, take list item details and open new activity

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WordEdit.class);
                intent.putExtra("id_edit_extra", btnSendId);
                intent.putExtra("first_word_edit_extra", btnSendFirstWord);
                intent.putExtra("second_word_edit_extra", btnSendSecondWord);
                intent.putExtra("word_list", btnSendWordList);
                context.startActivity(intent);
            }
        });

        return row;

    }

    // CustomHolder fields

    static class CustomerHolder {

        TextView firstWord;
        TextView secondWord;
        TextView wordList;
        Button btnEdit;

    }
}
