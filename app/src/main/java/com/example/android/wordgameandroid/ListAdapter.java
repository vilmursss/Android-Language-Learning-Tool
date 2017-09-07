package com.example.android.wordgameandroid;

import android.app.Activity;
import android.content.Context;
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
            row.setTag(holder);

        } else {

            holder = (CustomerHolder) row.getTag();

        }
        Word wordObject = data.get(position);
        holder.firstWord.setText(String.valueOf(wordObject.getFirstWord()));
        holder.secondWord.setText(wordObject.getSecondWord());
/*
        final String getName = user.getName();
        final String getAddress = user.getAddress();
        final String getPhoneNum = user.getPhoneNumber();

        // Edit painiketta painaessa otettaan tämän tietyn kentät datat ylös ja ne lähetetään luokkaan, jossa asiakkaan tietoja voi muokata

        holder.btnEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, modifyCustomer.class);
                intent.putExtra("name", getName);
                intent.putExtra("address", getAddress);
                intent.putExtra("pnum", getPhoneNum);
                intent.putExtra("id", getID);

                context.startActivity(intent);
            }
        });

        // Delete painiketta painamalla varmistetaan alertikkunoin, että käyttäjä on varma tämän tietyn asiakkaan poistosta

        holder.btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Customer delete");
                builder.setMessage("Are you sure you wanna delete this " + getName + " customer ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dBHandler db = new dBHandler(context);
                        db.deleteCustomer(getID);
                        dialog.dismiss();

                        // Luodaan toinen dialogi, jossa ilmaistaan tieto että asiakas on poistettu

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Customer deleted");
                        alertDialog.setMessage("Customer deleted!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                        String currentClass = context.getClass().getSimpleName().toString();

                                        if (currentClass.equals("displayAllCustomers")) {
                                            Intent intent = new Intent(context, displayAllCustomers.class);
                                            context.startActivity(intent);
                                        }

                                        if (currentClass.equals("DisplaySearchResults")) {
                                            Intent intent = new Intent(context, search.class);
                                            context.startActivity(intent);
                                        }
                                    }
                                });

                        alertDialog.show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
*/
        return row;

    }

    // Kentät ja painikkeet, jotka kustomoidulle riville tulevat näkyviin

    static class CustomerHolder {

        TextView firstWord;
        TextView secondWord;

    }
}
