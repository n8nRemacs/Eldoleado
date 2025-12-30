package androidx.appcompat.widget;

import android.graphics.Typeface;
import android.widget.TextView;

/* compiled from: AppCompatTextHelper.java */
/* loaded from: classes.dex */
class N implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ TextView f22220b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Typeface f22221c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f22222d;

    public N(TextView textView, Typeface typeface, int i12) {
        this.f22220b = textView;
        this.f22221c = typeface;
        this.f22222d = i12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f22220b.setTypeface(this.f22221c, this.f22222d);
    }
}
