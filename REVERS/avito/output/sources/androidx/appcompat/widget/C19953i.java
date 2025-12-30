package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AutoCompleteTextView;
import androidx.annotation.RestrictTo;
import j.InterfaceC42165v;
import m.C43852a;

/* compiled from: AppCompatAutoCompleteTextView.java */
/* renamed from: androidx.appcompat.widget.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19953i extends AutoCompleteTextView implements androidx.core.view.Y, X, androidx.core.widget.z {
    private static final int[] TINT_ATTRS = {R.attr.popupBackground};

    @j.N
    private final C19971t mAppCompatEmojiEditTextHelper;
    private final C19955j mBackgroundTintHelper;
    private final M mTextHelper;

    public C19953i(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, com.avito.android.R.attr.autoCompleteTextViewStyle);
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.a();
        }
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
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
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.mTextHelper.d();
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.mTextHelper.e();
    }

    public void initEmojiKeyListener(C19971t c19971t) {
        KeyListener keyListener = getKeyListener();
        c19971t.getClass();
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
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        C19973v.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return this.mAppCompatEmojiEditTextHelper.f22626b.b(inputConnectionOnCreateInputConnection, editorInfo);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawables(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelative(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(@j.P ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.n.h(callback, this));
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(@InterfaceC42165v int i12) {
        setDropDownBackgroundDrawable(C43852a.a(getContext(), i12));
    }

    public void setEmojiCompatEnabled(boolean z12) {
        this.mAppCompatEmojiEditTextHelper.f22626b.c(z12);
    }

    @Override // android.widget.TextView
    public void setKeyListener(@j.P KeyListener keyListener) {
        super.setKeyListener(this.mAppCompatEmojiEditTextHelper.a(keyListener));
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintList(@j.P ColorStateList colorStateList) {
        this.mTextHelper.h(colorStateList);
        this.mTextHelper.b();
    }

    @RestrictTo
    public void setSupportCompoundDrawablesTintMode(@j.P PorterDuff.Mode mode) {
        this.mTextHelper.i(mode);
        this.mTextHelper.b();
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i12) {
        super.setTextAppearance(context, i12);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.g(i12, context);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C19953i(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        A0.a(context);
        y0.a(getContext(), this);
        D0 d0E = D0.e(getContext(), attributeSet, TINT_ATTRS, i12);
        if (d0E.f22121b.hasValue(0)) {
            setDropDownBackgroundDrawable(d0E.b(0));
        }
        d0E.f();
        C19955j c19955j = new C19955j(this);
        this.mBackgroundTintHelper = c19955j;
        c19955j.d(attributeSet, i12);
        M m12 = new M(this);
        this.mTextHelper = m12;
        m12.f(attributeSet, i12);
        m12.b();
        C19971t c19971t = new C19971t(this);
        this.mAppCompatEmojiEditTextHelper = c19971t;
        c19971t.b(attributeSet, i12);
        initEmojiKeyListener(c19971t);
    }
}
