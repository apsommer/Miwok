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
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // initialize audio manager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // add Word objects to the ArrayList
        words.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // custom adapter set on ListView
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
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
