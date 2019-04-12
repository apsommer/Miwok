package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private ArrayList<Word> words = new ArrayList<Word>(); // create ArrayList of Word objects, ArrayList has flexible length
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer(); // reset the MediaPlayer clearing all associated memory resources
        }

    };

    private AudioManager mAudioManager;
    //mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

    // handle audio focus states
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        // callback method
        public void onAudioFocusChange(int focusChange) {

            // pause (and restart for this app) as focus will be regained shortly
            // OR statement used here because even if the new app with focus can duck, it is not helpful in this case
            // we want the user to hear the full pronunciation of the word
            // same logic for using seekTo(0), restart the whole translation when the focus returns
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

                // resume playing, this will always be from start of current translation
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();

                // stop and release resources, focus is gone permanently
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // initialize audio manager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // add Word objects to the ArrayList
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyissa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        // bind custom adapter to ListView
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // set an item click listener on ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int audioResourceId = words.get(position).getAudioResourceId(); // get audio resource id from selected word
                releaseMediaPlayer(); // reset the MediaPlayer clearing all associated memory resources

                // request transient audio focus using music stream, only need transient because audio files are short
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // start playing
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(getActivity(), audioResourceId); // create new MediaPlayer
                    mMediaPlayer.start(); // play the audio clip
                    mMediaPlayer.setOnCompletionListener(mCompletionListener); // asynchronous callback method

                }
            }

        });

        return rootView;

    }

    // same method name and return type for previously used NumbersActivity.java
    // however the access modifier for the activity method was protected, while the modifier on the fragment is public
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Helper method provide by Udacity
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {

        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {

            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // regardless of being granted audio focus, abandon it
            // this also unregisters the audio focus state change listener to prevent any callbacks
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
