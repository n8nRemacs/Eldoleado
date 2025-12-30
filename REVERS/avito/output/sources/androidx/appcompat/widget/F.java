package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import com.avito.android.R;

/* compiled from: AppCompatRatingBar.java */
/* loaded from: classes.dex */
public class F extends RatingBar {

    /* renamed from: b, reason: collision with root package name */
    public final C f22129b;

    public F(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.ratingBarStyle);
        y0.a(getContext(), this);
        C c12 = new C(this);
        this.f22129b = c12;
        c12.a(attributeSet, R.attr.ratingBarStyle);
    }

    @Override // android.widget.RatingBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onMeasure(int i12, int i13) {
        super.onMeasure(i12, i13);
        Bitmap bitmap = this.f22129b.f22104b;
        if (bitmap != null) {
            setMeasuredDimension(View.resolveSizeAndState(bitmap.getWidth() * getNumStars(), i12, 0), getMeasuredHeight());
        }
    }
}
