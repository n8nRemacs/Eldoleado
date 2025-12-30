package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;
import androidx.annotation.RestrictTo;
import j.InterfaceC42165v;
import m.C43852a;

/* compiled from: AppCompatMultiAutoCompleteTextView.java */
/* loaded from: classes.dex */
public class A extends MultiAutoCompleteTextView implements androidx.core.view.Y, X, androidx.core.widget.z {

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f21936e = {R.attr.popupBackground};

    /* renamed from: b, reason: collision with root package name */
    public final C19955j f21937b;

    /* renamed from: c, reason: collision with root package name */
    public final M f21938c;

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public final C19971t f21939d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, com.avito.android.R.attr.autoCompleteTextViewStyle);
        A0.a(context);
        y0.a(getContext(), this);
        D0 d0E = D0.e(getContext(), attributeSet, f21936e, com.avito.android.R.attr.autoCompleteTextViewStyle);
        if (d0E.f22121b.hasValue(0)) {
            setDropDownBackgroundDrawable(d0E.b(0));
        }
        d0E.f();
        C19955j c19955j = new C19955j(this);
        this.f21937b = c19955j;
        c19955j.d(attributeSet, com.avito.android.R.attr.autoCompleteTextViewStyle);
        M m12 = new M(this);
        this.f21938c = m12;
        m12.f(attributeSet, com.avito.android.R.attr.autoCompleteTextViewStyle);
        m12.b();
        C19971t c19971t = new C19971t(this);
        this.f21939d = c19971t;
        c19971t.b(attributeSet, com.avito.android.R.attr.autoCompleteTextViewStyle);
        KeyListener keyListener = getKeyListener();
        if (keyListener instanceof NumberKeyListener) {
            return;
        }
        boolean zIsFocusable = super.isFocusable();
        boolean zIsClickable = super.isClickable();
        boolean zIsLongClickable = super.isLongClickable();
        int inputType = super.getInputType();
        KeyListener keyListenerA = c19971t.a(keyListener);
        if (keyListenerA == keyListener) {
            return;
        }
        super.setKeyListener(keyListenerA);
        super.setRawInputType(inputType);
        super.setFocusable(zIsFocusable);
        super.setClickable(zIsClickable);
        super.setLongClickable(zIsLongClickable);
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            c19955j.a();
        }
        M m12 = this.f21938c;
        if (m12 != null) {
            m12.b();
        }
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.f21938c.d();
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.f21938c.e();
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        C19973v.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return this.f21939d.f22626b.b(inputConnectionOnCreateInputConnection, editorInfo);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f21938c;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f21938c;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(@InterfaceC42165v int i12) {
        setDropDownBackgroundDrawable(C43852a.a(getContext(), i12));
    }

    public void setEmojiCompatEnabled(boolean z12) {
        this.f21939d.f22626b.c(z12);
    }

    @Override // android.widget.TextView
    public void setKeyListener(@j.P KeyListener keyListener) {
        super.setKeyListener(this.f21939d.a(keyListener));
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.f21937b;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintList(@j.P ColorStateList colorStateList) {
        M m12 = this.f21938c;
        m12.h(colorStateList);
        m12.b();
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintMode(@j.P PorterDuff.Mode mode) {
        M m12 = this.f21938c;
        m12.i(mode);
        m12.b();
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i12) {
        super.setTextAppearance(context, i12);
        M m12 = this.f21938c;
        if (m12 != null) {
            m12.g(i12, context);
        }
    }
}
