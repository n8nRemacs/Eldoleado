package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.RadioButton;
import androidx.annotation.RestrictTo;
import j.InterfaceC42165v;
import m.C43852a;

/* compiled from: AppCompatRadioButton.java */
/* loaded from: classes.dex */
public class E extends RadioButton implements androidx.core.widget.y, androidx.core.view.Y, X, androidx.core.widget.z {

    /* renamed from: b, reason: collision with root package name */
    public final C19969q f22123b;

    /* renamed from: c, reason: collision with root package name */
    public final C19955j f22124c;

    /* renamed from: d, reason: collision with root package name */
    public final M f22125d;

    /* renamed from: e, reason: collision with root package name */
    public C19972u f22126e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E(Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        A0.a(context);
        y0.a(getContext(), this);
        C19969q c19969q = new C19969q(this);
        this.f22123b = c19969q;
        c19969q.b(attributeSet, i12);
        C19955j c19955j = new C19955j(this);
        this.f22124c = c19955j;
        c19955j.d(attributeSet, i12);
        M m12 = new M(this);
        this.f22125d = m12;
        m12.f(attributeSet, i12);
        getEmojiTextViewHelper().a(attributeSet, i12);
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.f22126e == null) {
            this.f22126e = new C19972u(this);
        }
        return this.f22126e;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            c19955j.a();
        }
        M m12 = this.f22125d;
        if (m12 != null) {
            m12.b();
        }
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @Override // androidx.core.widget.y
    @RestrictTo
    @j.P
    public ColorStateList getSupportButtonTintList() {
        C19969q c19969q = this.f22123b;
        if (c19969q != null) {
            return c19969q.f22604b;
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportButtonTintMode() {
        C19969q c19969q = this.f22123b;
        if (c19969q != null) {
            return c19969q.f22605c;
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.f22125d.d();
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.f22125d.e();
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z12) {
        super.setAllCaps(z12);
        getEmojiTextViewHelper().b(z12);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        C19969q c19969q = this.f22123b;
        if (c19969q != null) {
            if (c19969q.f22608f) {
                c19969q.f22608f = false;
            } else {
                c19969q.f22608f = true;
                c19969q.a();
            }
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22125d;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22125d;
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
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.f22124c;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @Override // androidx.core.widget.y
    @RestrictTo
    public void setSupportButtonTintList(@j.P ColorStateList colorStateList) {
        C19969q c19969q = this.f22123b;
        if (c19969q != null) {
            c19969q.f22604b = colorStateList;
            c19969q.f22606d = true;
            c19969q.a();
        }
    }

    @Override // androidx.core.widget.y
    @RestrictTo
    public void setSupportButtonTintMode(@j.P PorterDuff.Mode mode) {
        C19969q c19969q = this.f22123b;
        if (c19969q != null) {
            c19969q.f22605c = mode;
            c19969q.f22607e = true;
            c19969q.a();
        }
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintList(@j.P ColorStateList colorStateList) {
        M m12 = this.f22125d;
        m12.h(colorStateList);
        m12.b();
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintMode(@j.P PorterDuff.Mode mode) {
        M m12 = this.f22125d;
        m12.i(mode);
        m12.b();
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(@InterfaceC42165v int i12) {
        setButtonDrawable(C43852a.a(getContext(), i12));
    }
}
