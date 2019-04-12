package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    // initialize background to arbitrary value
    private int mBackgroundColor = -1;

    // custom constructor
    public WordAdapter(Context context, ArrayList<Word> words, int backgroundColor) {

        // calls superclass ArrayAdapter constructor
        // second argument for populating single TextView (the default for ArrayAdapter)
        // since our custom layout is inflated below this argument is arbitrary
        super(context, 0, words);

        // background color of inflated list item
        mBackgroundColor = backgroundColor;

    }

    // This method must be overwritten to provide the custom layout (anything other than the default single TextView expected by ArrayAdapter)
    // ViewGroup parent is the master ListView in word_list.xml
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate a new view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the object located at this position in the list
        Word currentWord = getItem(position); // "this." can added or removed before getItem()

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Get the miwok translation from the current Word object and set it to miwokTextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Get the default translation from the current Word object and set it to defaultTextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

        // check if an image exists in the Word object
        if (currentWord.hasImage()) {

            // Get the image resource ID from the current Word object and set the image to iconView
            iconView.setImageResource(currentWord.getImageResourceId());

            // explicitly ensure the View is visible in the layout
            iconView.setVisibility(View.VISIBLE);

        } else {

            // the word object has no image, so effectively remove the View from the layout
            // VISIBLE = normal behavior
            // INVISIBLE = layout still takes up space but there is no content
            // GONE = effectively remove the view from layout as if it was gone (View does still exist)
            iconView.setVisibility(View.GONE);
        }

        // get the RelativeLayout containing both translations
        RelativeLayout translationsRelativeLayout = (RelativeLayout) listItemView.findViewById(R.id.translations_relative_layout);

        int color = ContextCompat.getColor(getContext(), mBackgroundColor);

        // Get the default translation from the current Word object and set it to defaultTextView
        translationsRelativeLayout.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // this becomes a child of the ListView
        return listItemView;

    }

}
