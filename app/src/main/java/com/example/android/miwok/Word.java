package com.example.android.miwok;

// Custom object holding two string translations
public class Word {

    // String for Miwok translation
    private String mDefaultTranslation;

    // String for default translation
    private String mMiwokTranslation;

    // constructor
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    // gets default translation
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // gets Miwok translation
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
}
