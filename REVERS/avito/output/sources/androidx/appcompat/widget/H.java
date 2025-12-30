package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.avito.android.R;

/* compiled from: AppCompatSeekBar.java */
/* loaded from: classes.dex */
public class H extends SeekBar {

    /* renamed from: b, reason: collision with root package name */
    public final I f22133b;

    public H(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.seekBarStyle);
        y0.a(getContext(), this);
        I i12 = new I(this);
        this.f22133b = i12;
        i12.a(attributeSet, R.attr.seekBarStyle);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        I i12 = this.f22133b;
        Drawable drawable = i12.f22156e;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        H h12 = i12.f22155d;
        if (drawable.setState(h12.getDrawableState())) {
            h12.invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f22133b.f22156e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.f22133b.d(canvas);
    }
}
