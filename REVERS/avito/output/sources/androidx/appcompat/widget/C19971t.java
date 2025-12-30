package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;
import l.C43521a;

/* compiled from: AppCompatEmojiEditTextHelper.java */
/* renamed from: androidx.appcompat.widget.t, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19971t {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final EditText f22625a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final androidx.emoji2.viewsintegration.a f22626b;

    public C19971t(@j.N EditText editText) {
        this.f22625a = editText;
        this.f22626b = new androidx.emoji2.viewsintegration.a(editText);
    }

    @j.P
    public final KeyListener a(@j.P KeyListener keyListener) {
        return !(keyListener instanceof NumberKeyListener) ? this.f22626b.a(keyListener) : keyListener;
    }

    public final void b(@j.P AttributeSet attributeSet, int i12) {
        TypedArray typedArrayObtainStyledAttributes = this.f22625a.getContext().obtainStyledAttributes(attributeSet, C43521a.m.f413360i, i12, 0);
        try {
            boolean z12 = typedArrayObtainStyledAttributes.hasValue(14) ? typedArrayObtainStyledAttributes.getBoolean(14, true) : true;
            typedArrayObtainStyledAttributes.recycle();
            this.f22626b.c(z12);
        } catch (Throwable th2) {
            typedArrayObtainStyledAttributes.recycle();
            throw th2;
        }
    }
}
