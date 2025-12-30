package android.support.v4.media;

import android.annotation.SuppressLint;
import android.media.Rating;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import j.InterfaceC42164u;
import j.X;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>() { // from class: android.support.v4.media.RatingCompat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RatingCompat[] newArray(int i12) {
            return new RatingCompat[i12];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    @X
    public static class Api19Impl {
        private Api19Impl() {
        }

        @InterfaceC42164u
        public static float getPercentRating(Rating rating) {
            return rating.getPercentRating();
        }

        @InterfaceC42164u
        public static int getRatingStyle(Rating rating) {
            return rating.getRatingStyle();
        }

        @InterfaceC42164u
        public static float getStarRating(Rating rating) {
            return rating.getStarRating();
        }

        @InterfaceC42164u
        public static boolean hasHeart(Rating rating) {
            return rating.hasHeart();
        }

        @InterfaceC42164u
        public static boolean isRated(Rating rating) {
            return rating.isRated();
        }

        @InterfaceC42164u
        public static boolean isThumbUp(Rating rating) {
            return rating.isThumbUp();
        }

        @InterfaceC42164u
        public static Rating newHeartRating(boolean z12) {
            return Rating.newHeartRating(z12);
        }

        @InterfaceC42164u
        public static Rating newPercentageRating(float f12) {
            return Rating.newPercentageRating(f12);
        }

        @InterfaceC42164u
        public static Rating newStarRating(int i12, float f12) {
            return Rating.newStarRating(i12, f12);
        }

        @InterfaceC42164u
        public static Rating newThumbRating(boolean z12) {
            return Rating.newThumbRating(z12);
        }

        @InterfaceC42164u
        public static Rating newUnratedRating(int i12) {
            return Rating.newUnratedRating(i12);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface Style {
    }

    public RatingCompat(int i12, float f12) {
        this.mRatingStyle = i12;
        this.mRatingValue = f12;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat ratingCompatNewUnratedRating = null;
        if (obj != null) {
            Rating rating = (Rating) obj;
            int ratingStyle = Api19Impl.getRatingStyle(rating);
            if (Api19Impl.isRated(rating)) {
                switch (ratingStyle) {
                    case 1:
                        ratingCompatNewUnratedRating = newHeartRating(Api19Impl.hasHeart(rating));
                        break;
                    case 2:
                        ratingCompatNewUnratedRating = newThumbRating(Api19Impl.isThumbUp(rating));
                        break;
                    case 3:
                    case 4:
                    case 5:
                        ratingCompatNewUnratedRating = newStarRating(ratingStyle, Api19Impl.getStarRating(rating));
                        break;
                    case 6:
                        ratingCompatNewUnratedRating = newPercentageRating(Api19Impl.getPercentRating(rating));
                        break;
                    default:
                        return null;
                }
            } else {
                ratingCompatNewUnratedRating = newUnratedRating(ratingStyle);
            }
            ratingCompatNewUnratedRating.mRatingObj = obj;
        }
        return ratingCompatNewUnratedRating;
    }

    public static RatingCompat newHeartRating(boolean z12) {
        return new RatingCompat(1, z12 ? 1.0f : 0.0f);
    }

    public static RatingCompat newPercentageRating(float f12) {
        if (f12 < 0.0f || f12 > 100.0f) {
            return null;
        }
        return new RatingCompat(6, f12);
    }

    public static RatingCompat newStarRating(int i12, float f12) {
        float f13;
        if (i12 == 3) {
            f13 = 3.0f;
        } else if (i12 == 4) {
            f13 = 4.0f;
        } else {
            if (i12 != 5) {
                return null;
            }
            f13 = 5.0f;
        }
        if (f12 < 0.0f || f12 > f13) {
            return null;
        }
        return new RatingCompat(i12, f12);
    }

    public static RatingCompat newThumbRating(boolean z12) {
        return new RatingCompat(2, z12 ? 1.0f : 0.0f);
    }

    public static RatingCompat newUnratedRating(int i12) {
        switch (i12) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat(i12, RATING_NOT_RATED);
            default:
                return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mRatingStyle;
    }

    public float getPercentRating() {
        return (this.mRatingStyle == 6 && isRated()) ? this.mRatingValue : RATING_NOT_RATED;
    }

    public Object getRating() {
        if (this.mRatingObj == null) {
            if (isRated()) {
                int i12 = this.mRatingStyle;
                switch (i12) {
                    case 1:
                        this.mRatingObj = Api19Impl.newHeartRating(hasHeart());
                        break;
                    case 2:
                        this.mRatingObj = Api19Impl.newThumbRating(isThumbUp());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        this.mRatingObj = Api19Impl.newStarRating(i12, getStarRating());
                        break;
                    case 6:
                        this.mRatingObj = Api19Impl.newPercentageRating(getPercentRating());
                        break;
                    default:
                        return null;
                }
            } else {
                this.mRatingObj = Api19Impl.newUnratedRating(this.mRatingStyle);
            }
        }
        return this.mRatingObj;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public float getStarRating() {
        int i12 = this.mRatingStyle;
        return ((i12 == 3 || i12 == 4 || i12 == 5) && isRated()) ? this.mRatingValue : RATING_NOT_RATED;
    }

    public boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    public String toString() {
        StringBuilder sb2 = new StringBuilder("Rating:style=");
        sb2.append(this.mRatingStyle);
        sb2.append(" rating=");
        float f12 = this.mRatingValue;
        sb2.append(f12 < 0.0f ? "unrated" : String.valueOf(f12));
        return sb2.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i12) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }
}
