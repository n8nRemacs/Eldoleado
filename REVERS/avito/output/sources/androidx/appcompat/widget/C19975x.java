package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.RestrictTo;
import androidx.core.view.C22823h0;
import l.C43521a;
import m.C43852a;

/* compiled from: AppCompatImageHelper.java */
@RestrictTo
/* renamed from: androidx.appcompat.widget.x, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19975x {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final ImageView f22658a;

    /* renamed from: b, reason: collision with root package name */
    public B0 f22659b;

    /* renamed from: c, reason: collision with root package name */
    public int f22660c = 0;

    public C19975x(@j.N ImageView imageView) {
        this.f22658a = imageView;
    }

    public final void a() {
        B0 b02;
        ImageView imageView = this.f22658a;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            V.a(drawable);
        }
        if (drawable == null || (b02 = this.f22659b) == null) {
            return;
        }
        r.e(drawable, b02, imageView.getDrawableState());
    }

    public final void b(AttributeSet attributeSet, int i12) {
        int resourceId;
        ImageView imageView = this.f22658a;
        Context context = imageView.getContext();
        int[] iArr = C43521a.m.f413357f;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        C22823h0.B(imageView, imageView.getContext(), iArr, attributeSet, d0E.f22121b, i12);
        try {
            Drawable drawable = imageView.getDrawable();
            TypedArray typedArray = d0E.f22121b;
            if (drawable == null && (resourceId = typedArray.getResourceId(1, -1)) != -1 && (drawable = C43852a.a(imageView.getContext(), resourceId)) != null) {
                imageView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                V.a(drawable);
            }
            if (typedArray.hasValue(2)) {
                imageView.setImageTintList(d0E.a(2));
            }
            if (typedArray.hasValue(3)) {
                imageView.setImageTintMode(V.c(typedArray.getInt(3, -1), null));
            }
            d0E.f();
        } catch (Throwable th2) {
            d0E.f();
            throw th2;
        }
    }

    public final void c(int i12) {
        ImageView imageView = this.f22658a;
        if (i12 != 0) {
            Drawable drawableA = C43852a.a(imageView.getContext(), i12);
            if (drawableA != null) {
                V.a(drawableA);
            }
            imageView.setImageDrawable(drawableA);
        } else {
            imageView.setImageDrawable(null);
        }
        a();
    }
}
