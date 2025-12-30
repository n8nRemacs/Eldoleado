package android.support.v4.media;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import androidx.annotation.RestrictTo;
import j.InterfaceC42164u;
import j.P;
import j.X;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class MediaDescriptionCompat implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>() { // from class: android.support.v4.media.MediaDescriptionCompat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            return MediaDescriptionCompat.fromMediaDescription(MediaDescription.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaDescriptionCompat[] newArray(int i12) {
            return new MediaDescriptionCompat[i12];
        }
    };

    @RestrictTo
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";

    @RestrictTo
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
    public static final long STATUS_DOWNLOADED = 2;
    public static final long STATUS_DOWNLOADING = 1;
    public static final long STATUS_NOT_DOWNLOADED = 0;
    private static final String TAG = "MediaDescriptionCompat";
    private final CharSequence mDescription;
    private MediaDescription mDescriptionFwk;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    @X
    public static class Api21Impl {
        private Api21Impl() {
        }

        @InterfaceC42164u
        public static MediaDescription build(MediaDescription.Builder builder) {
            return builder.build();
        }

        @InterfaceC42164u
        public static MediaDescription.Builder createBuilder() {
            return new MediaDescription.Builder();
        }

        @InterfaceC42164u
        @P
        public static CharSequence getDescription(MediaDescription mediaDescription) {
            return mediaDescription.getDescription();
        }

        @InterfaceC42164u
        @P
        public static Bundle getExtras(MediaDescription mediaDescription) {
            return mediaDescription.getExtras();
        }

        @InterfaceC42164u
        @P
        public static Bitmap getIconBitmap(MediaDescription mediaDescription) {
            return mediaDescription.getIconBitmap();
        }

        @InterfaceC42164u
        @P
        public static Uri getIconUri(MediaDescription mediaDescription) {
            return mediaDescription.getIconUri();
        }

        @InterfaceC42164u
        @P
        public static String getMediaId(MediaDescription mediaDescription) {
            return mediaDescription.getMediaId();
        }

        @InterfaceC42164u
        @P
        public static CharSequence getSubtitle(MediaDescription mediaDescription) {
            return mediaDescription.getSubtitle();
        }

        @InterfaceC42164u
        @P
        public static CharSequence getTitle(MediaDescription mediaDescription) {
            return mediaDescription.getTitle();
        }

        @InterfaceC42164u
        public static void setDescription(MediaDescription.Builder builder, @P CharSequence charSequence) {
            builder.setDescription(charSequence);
        }

        @InterfaceC42164u
        public static void setExtras(MediaDescription.Builder builder, @P Bundle bundle) {
            builder.setExtras(bundle);
        }

        @InterfaceC42164u
        public static void setIconBitmap(MediaDescription.Builder builder, @P Bitmap bitmap) {
            builder.setIconBitmap(bitmap);
        }

        @InterfaceC42164u
        public static void setIconUri(MediaDescription.Builder builder, @P Uri uri) {
            builder.setIconUri(uri);
        }

        @InterfaceC42164u
        public static void setMediaId(MediaDescription.Builder builder, @P String str) {
            builder.setMediaId(str);
        }

        @InterfaceC42164u
        public static void setSubtitle(MediaDescription.Builder builder, @P CharSequence charSequence) {
            builder.setSubtitle(charSequence);
        }

        @InterfaceC42164u
        public static void setTitle(MediaDescription.Builder builder, @P CharSequence charSequence) {
            builder.setTitle(charSequence);
        }
    }

    @X
    public static class Api23Impl {
        private Api23Impl() {
        }

        @InterfaceC42164u
        @P
        public static Uri getMediaUri(MediaDescription mediaDescription) {
            return mediaDescription.getMediaUri();
        }

        @InterfaceC42164u
        public static void setMediaUri(MediaDescription.Builder builder, @P Uri uri) {
            builder.setMediaUri(uri);
        }
    }

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }

        public Builder setDescription(@P CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setExtras(@P Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setIconBitmap(@P Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(@P Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setMediaId(@P String str) {
            this.mMediaId = str;
            return this;
        }

        public Builder setMediaUri(@P Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public Builder setSubtitle(@P CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setTitle(@P CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }

    public MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object obj) {
        Bundle bundle = null;
        if (obj == null) {
            return null;
        }
        Builder builder = new Builder();
        MediaDescription mediaDescription = (MediaDescription) obj;
        builder.setMediaId(Api21Impl.getMediaId(mediaDescription));
        builder.setTitle(Api21Impl.getTitle(mediaDescription));
        builder.setSubtitle(Api21Impl.getSubtitle(mediaDescription));
        builder.setDescription(Api21Impl.getDescription(mediaDescription));
        builder.setIconBitmap(Api21Impl.getIconBitmap(mediaDescription));
        builder.setIconUri(Api21Impl.getIconUri(mediaDescription));
        Bundle extras = Api21Impl.getExtras(mediaDescription);
        if (extras != null) {
            extras = MediaSessionCompat.unparcelWithClassLoader(extras);
        }
        Uri uri = extras != null ? (Uri) extras.getParcelable(DESCRIPTION_KEY_MEDIA_URI) : null;
        if (uri == null) {
            bundle = extras;
        } else if (!extras.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) || extras.size() != 2) {
            extras.remove(DESCRIPTION_KEY_MEDIA_URI);
            extras.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
            bundle = extras;
        }
        builder.setExtras(bundle);
        if (uri != null) {
            builder.setMediaUri(uri);
        } else {
            builder.setMediaUri(Api23Impl.getMediaUri(mediaDescription));
        }
        MediaDescriptionCompat mediaDescriptionCompatBuild = builder.build();
        mediaDescriptionCompatBuild.mDescriptionFwk = mediaDescription;
        return mediaDescriptionCompatBuild;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @P
    public CharSequence getDescription() {
        return this.mDescription;
    }

    @P
    public Bundle getExtras() {
        return this.mExtras;
    }

    @P
    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @P
    public Uri getIconUri() {
        return this.mIconUri;
    }

    public Object getMediaDescription() {
        MediaDescription mediaDescription = this.mDescriptionFwk;
        if (mediaDescription != null) {
            return mediaDescription;
        }
        MediaDescription.Builder builderCreateBuilder = Api21Impl.createBuilder();
        Api21Impl.setMediaId(builderCreateBuilder, this.mMediaId);
        Api21Impl.setTitle(builderCreateBuilder, this.mTitle);
        Api21Impl.setSubtitle(builderCreateBuilder, this.mSubtitle);
        Api21Impl.setDescription(builderCreateBuilder, this.mDescription);
        Api21Impl.setIconBitmap(builderCreateBuilder, this.mIcon);
        Api21Impl.setIconUri(builderCreateBuilder, this.mIconUri);
        Api21Impl.setExtras(builderCreateBuilder, this.mExtras);
        Api23Impl.setMediaUri(builderCreateBuilder, this.mMediaUri);
        MediaDescription mediaDescriptionBuild = Api21Impl.build(builderCreateBuilder);
        this.mDescriptionFwk = mediaDescriptionBuild;
        return mediaDescriptionBuild;
    }

    @P
    public String getMediaId() {
        return this.mMediaId;
    }

    @P
    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    @P
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @P
    public CharSequence getTitle() {
        return this.mTitle;
    }

    public String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.mSubtitle) + ", " + ((Object) this.mDescription);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i12) {
        ((MediaDescription) getMediaDescription()).writeToParcel(parcel, i12);
    }

    public MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
        this.mTitle = (CharSequence) creator.createFromParcel(parcel);
        this.mSubtitle = (CharSequence) creator.createFromParcel(parcel);
        this.mDescription = (CharSequence) creator.createFromParcel(parcel);
        ClassLoader classLoader = MediaDescriptionCompat.class.getClassLoader();
        this.mIcon = (Bitmap) parcel.readParcelable(classLoader);
        this.mIconUri = (Uri) parcel.readParcelable(classLoader);
        this.mExtras = parcel.readBundle(classLoader);
        this.mMediaUri = (Uri) parcel.readParcelable(classLoader);
    }
}
