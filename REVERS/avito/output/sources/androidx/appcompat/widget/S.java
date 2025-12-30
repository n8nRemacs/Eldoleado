package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import androidx.annotation.RestrictTo;
import j.InterfaceC42165v;

/* compiled from: AppCompatToggleButton.java */
/* loaded from: classes.dex */
public class S extends ToggleButton implements androidx.core.view.Y, X, androidx.core.widget.z {

    /* renamed from: b, reason: collision with root package name */
    public final C19955j f22249b;

    /* renamed from: c, reason: collision with root package name */
    public final M f22250c;

    /* renamed from: d, reason: collision with root package name */
    public C19972u f22251d;

    public S(@j.N Context context, @j.P AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.buttonStyleToggle);
        y0.a(getContext(), this);
        C19955j c19955j = new C19955j(this);
        this.f22249b = c19955j;
        c19955j.d(attributeSet, R.attr.buttonStyleToggle);
        M m12 = new M(this);
        this.f22250c = m12;
        m12.f(attributeSet, R.attr.buttonStyleToggle);
        getEmojiTextViewHelper().a(attributeSet, R.attr.buttonStyleToggle);
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.f22251d == null) {
            this.f22251d = new C19972u(this);
        }
        return this.f22251d;
    }

    @Override // android.widget.ToggleButton, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            c19955j.a();
        }
        M m12 = this.f22250c;
        if (m12 != null) {
            m12.b();
        }
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.f22250c.d();
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.f22250c.e();
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z12) {
        super.setAllCaps(z12);
        getEmojiTextViewHelper().b(z12);
    }

    @Override // android.widget.ToggleButton, android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22250c;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22250c;
        if (m12 != null) {
            m12.b();
        }
    }

    public void setEmojiCompatEnabled(boolean z12) {
        getEmojiTextViewHelper().c(z12);
    }

    @Override // android.widget.TextView
    public void setFilters(@j.N InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().f22628b.a(inputFilterArr));
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.f22249b;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintList(@j.P ColorStateList colorStateList) {
        M m12 = this.f22250c;
        m12.h(colorStateList);
        m12.b();
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintMode(@j.P PorterDuff.Mode mode) {
        M m12 = this.f22250c;
        m12.i(mode);
        m12.b();
    }
}
