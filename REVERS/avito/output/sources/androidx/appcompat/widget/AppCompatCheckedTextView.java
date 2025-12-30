package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import androidx.annotation.RestrictTo;
import androidx.core.view.C22823h0;
import com.avito.android.R;
import j.InterfaceC42165v;
import l.C43521a;
import m.C43852a;

/* loaded from: classes.dex */
public class AppCompatCheckedTextView extends CheckedTextView implements androidx.core.widget.x, androidx.core.view.Y, X, androidx.core.widget.z {

    /* renamed from: b, reason: collision with root package name */
    public final C19967p f22061b;

    /* renamed from: c, reason: collision with root package name */
    public final C19955j f22062c;

    /* renamed from: d, reason: collision with root package name */
    public final M f22063d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public C19972u f22064e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatCheckedTextView(@j.N Context context, @j.P AttributeSet attributeSet) {
        int resourceId;
        int resourceId2;
        super(context, attributeSet, R.attr.checkedTextViewStyle);
        A0.a(context);
        y0.a(getContext(), this);
        M m12 = new M(this);
        this.f22063d = m12;
        m12.f(attributeSet, R.attr.checkedTextViewStyle);
        m12.b();
        C19955j c19955j = new C19955j(this);
        this.f22062c = c19955j;
        c19955j.d(attributeSet, R.attr.checkedTextViewStyle);
        this.f22061b = new C19967p(this);
        Context context2 = getContext();
        int[] iArr = C43521a.m.f413363l;
        D0 d0E = D0.e(context2, attributeSet, iArr, R.attr.checkedTextViewStyle);
        TypedArray typedArray = d0E.f22121b;
        C22823h0.B(this, getContext(), iArr, attributeSet, d0E.f22121b, R.attr.checkedTextViewStyle);
        try {
            if (typedArray.hasValue(1) && (resourceId2 = typedArray.getResourceId(1, 0)) != 0) {
                try {
                    setCheckMarkDrawable(C43852a.a(getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                }
            } else if (typedArray.hasValue(0) && (resourceId = typedArray.getResourceId(0, 0)) != 0) {
                setCheckMarkDrawable(C43852a.a(getContext(), resourceId));
            }
            if (typedArray.hasValue(2)) {
                setCheckMarkTintList(d0E.a(2));
            }
            if (typedArray.hasValue(3)) {
                setCheckMarkTintMode(V.c(typedArray.getInt(3, -1), null));
            }
            d0E.f();
            getEmojiTextViewHelper().a(attributeSet, R.attr.checkedTextViewStyle);
        } catch (Throwable th2) {
            d0E.f();
            throw th2;
        }
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.f22064e == null) {
            this.f22064e = new C19972u(this);
        }
        return this.f22064e;
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        M m12 = this.f22063d;
        if (m12 != null) {
            m12.b();
        }
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            c19955j.a();
        }
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            c19967p.a();
        }
    }

    @Override // android.widget.TextView
    @j.P
    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.n.g(super.getCustomSelectionActionModeCallback());
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCheckMarkTintList() {
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            return c19967p.f22598b;
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCheckMarkTintMode() {
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            return c19967p.f22599c;
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.f22063d.d();
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.f22063d.e();
    }

    @Override // android.widget.TextView, android.view.View
    @j.P
    public final InputConnection onCreateInputConnection(@j.N EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        C19973v.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z12) {
        super.setAllCaps(z12);
        getEmojiTextViewHelper().b(z12);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.CheckedTextView
    public void setCheckMarkDrawable(@j.P Drawable drawable) {
        super.setCheckMarkDrawable(drawable);
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            if (c19967p.f22602f) {
                c19967p.f22602f = false;
            } else {
                c19967p.f22602f = true;
                c19967p.a();
            }
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22063d;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        M m12 = this.f22063d;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(@j.P ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.n.h(callback, this));
    }

    public void setEmojiCompatEnabled(boolean z12) {
        getEmojiTextViewHelper().c(z12);
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.f22062c;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @RestrictTo
    public void setSupportCheckMarkTintList(@j.P ColorStateList colorStateList) {
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            c19967p.f22598b = colorStateList;
            c19967p.f22600d = true;
            c19967p.a();
        }
    }

    @RestrictTo
    public void setSupportCheckMarkTintMode(@j.P PorterDuff.Mode mode) {
        C19967p c19967p = this.f22061b;
        if (c19967p != null) {
            c19967p.f22599c = mode;
            c19967p.f22601e = true;
            c19967p.a();
        }
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintList(@j.P ColorStateList colorStateList) {
        M m12 = this.f22063d;
        m12.h(colorStateList);
        m12.b();
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintMode(@j.P PorterDuff.Mode mode) {
        M m12 = this.f22063d;
        m12.i(mode);
        m12.b();
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(@j.N Context context, int i12) {
        super.setTextAppearance(context, i12);
        M m12 = this.f22063d;
        if (m12 != null) {
            m12.g(i12, context);
        }
    }

    @Override // android.widget.CheckedTextView
    public void setCheckMarkDrawable(@InterfaceC42165v int i12) {
        setCheckMarkDrawable(C43852a.a(getContext(), i12));
    }
}
