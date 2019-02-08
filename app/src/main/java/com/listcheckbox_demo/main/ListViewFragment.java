package com.listcheckbox_demo.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.listcheckbox_demo.R;
import com.listcheckbox_demo.adapter.GridListAdapter;

import java.util.ArrayList;

/**
 * Created by sonu on 08/02/17.
 */
public class ListViewFragment extends Fragment {
    private Context context;
    private GridListAdapter adapter;
    private ArrayList<String> arrayList;
    private Button selectButton;

    public ListViewFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectButton = (Button) view.findViewById(R.id.select_button);
        loadListView(view);
        onClickEvent(view);
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        for (int i = 1; i <= 50; i++)
            arrayList.add("ListView Items " + i);//Adding items to recycler view

        adapter = new GridListAdapter(context, arrayList, true);
        listView.setAdapter(adapter);
    }

    private void onClickEvent(View view) {
        view.findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            String selectedRowLabel = arrayList.get(selectedRows.keyAt(i));

                            //append the row label text
                            stringBuilder.append(selectedRowLabel + "\n");
                        }
                    }
                    Toast.makeText(context, "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //remove the checked item
                            arrayList.remove(selectedRows.keyAt(i));
                        }
                    }

                    //notify the adapter and remove all checked selection
                    adapter.removeSelection();
                }
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check the current text of Select Button
                if (selectButton.getText().toString().equals(getResources().getString(R.string.select_all))) {

                    //If Text is Select All then loop to all array List items and check all of them
                    for (int i = 0; i < arrayList.size(); i++)
                        adapter.checkCheckBox(i, true);

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.deselect_all));
                } else {
                    //If button text is Deselect All remove check from all items
                    adapter.removeSelection();

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.select_all));
                }


            }
        });
    }
}
