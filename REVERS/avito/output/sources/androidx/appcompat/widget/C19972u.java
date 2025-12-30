package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import l.C43521a;

/* compiled from: AppCompatEmojiTextHelper.java */
/* renamed from: androidx.appcompat.widget.u, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19972u {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final TextView f22627a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final androidx.emoji2.viewsintegration.f f22628b;

    public C19972u(@j.N TextView textView) {
        this.f22627a = textView;
        this.f22628b = new androidx.emoji2.viewsintegration.f(textView);
    }

    public final void a(@j.P AttributeSet attributeSet, int i12) {
        TypedArray typedArrayObtainStyledAttributes = this.f22627a.getContext().obtainStyledAttributes(attributeSet, C43521a.m.f413360i, i12, 0);
        try {
            boolean z12 = typedArrayObtainStyledAttributes.hasValue(14) ? typedArrayObtainStyledAttributes.getBoolean(14, true) : true;
            typedArrayObtainStyledAttributes.recycle();
            c(z12);
        } catch (Throwable th2) {
            typedArrayObtainStyledAttributes.recycle();
            throw th2;
        }
    }

    public final void b(boolean z12) {
        this.f22628b.c(z12);
    }

    public final void c(boolean z12) {
        this.f22628b.d(z12);
    }
}
