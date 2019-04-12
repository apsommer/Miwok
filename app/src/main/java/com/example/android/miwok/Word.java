package com.example.android.miwok;

// Custom object holding two string translations
public class Word {

    // String for Miwok translation
    private String mDefaultTranslation;

    // String for default translation
    private String mMiwokTranslation;

    // int for image resource id
    // the adapter imageview will be hidden if no image specified in constructor
    // resource id's are positive integers > 0, therefore -1 is always outside this range
    private int mImageResourceId = -1;

    // int for audio resource
    private int mAudioResourceId;

    // used in a boolean comparison to determine if the Word has an associated image
    private static final int NO_IMAGE_PROVIDED = -1;

    // constructor without image, used in PhrasesFragment
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    // constructor with image
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    // gets default translation
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // gets Miwok translation
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    // gets image resource ID
    public int getImageResourceId() {
        return mImageResourceId;
    }

    // returns presence of image on this Word
    public boolean hasImage() {

        // true for any defined image resource
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    // gets audio resource ID
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
