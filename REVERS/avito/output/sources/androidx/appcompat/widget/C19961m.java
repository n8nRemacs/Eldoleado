package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import j.InterfaceC42165v;

/* compiled from: AppCompatButton.java */
/* renamed from: androidx.appcompat.widget.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19961m extends Button implements androidx.core.view.Y, androidx.core.widget.b, androidx.core.widget.z, X {

    @j.N
    private C19972u mAppCompatEmojiTextHelper;
    private final C19955j mBackgroundTintHelper;
    private final M mTextHelper;

    public C19961m(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.mAppCompatEmojiTextHelper == null) {
            this.mAppCompatEmojiTextHelper = new C19972u(this);
        }
        return this.mAppCompatEmojiTextHelper;
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
    @RestrictTo
    public int getAutoSizeMaxTextSize() {
        return super.getAutoSizeMaxTextSize();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int getAutoSizeMinTextSize() {
        return super.getAutoSizeMinTextSize();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int getAutoSizeStepGranularity() {
        return super.getAutoSizeStepGranularity();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int[] getAutoSizeTextAvailableSizes() {
        return super.getAutoSizeTextAvailableSizes();
    }

    @Override // android.widget.TextView
    @SuppressLint({"WrongConstant"})
    @RestrictTo
    public int getAutoSizeTextType() {
        return super.getAutoSizeTextType() == 1 ? 1 : 0;
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

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        super.onLayout(z12, i12, i13, i14, i15);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.getClass();
        }
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i12, int i13, int i14) {
        super.onTextChanged(charSequence, i12, i13, i14);
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z12) {
        super.setAllCaps(z12);
        getEmojiTextViewHelper().b(z12);
    }

    @Override // android.widget.TextView
    @RestrictTo
    public void setAutoSizeTextTypeUniformWithConfiguration(int i12, int i13, int i14, int i15) {
        super.setAutoSizeTextTypeUniformWithConfiguration(i12, i13, i14, i15);
    }

    @Override // android.widget.TextView
    @RestrictTo
    public void setAutoSizeTextTypeUniformWithPresetSizes(@j.N int[] iArr, int i12) {
        super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i12);
    }

    @Override // android.widget.TextView
    @RestrictTo
    public void setAutoSizeTextTypeWithDefaults(int i12) {
        super.setAutoSizeTextTypeWithDefaults(i12);
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
    public void setCustomSelectionActionModeCallback(@j.P ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.n.h(callback, this));
    }

    public void setEmojiCompatEnabled(boolean z12) {
        getEmojiTextViewHelper().c(z12);
    }

    @Override // android.widget.TextView
    public void setFilters(@j.N InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().f22628b.a(inputFilterArr));
    }

    public void setSupportAllCaps(boolean z12) {
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.f22203a.setAllCaps(z12);
        }
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

    @Override // android.widget.TextView
    public void setTextSize(int i12, float f12) {
        super.setTextSize(i12, f12);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C19961m(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        A0.a(context);
        y0.a(getContext(), this);
        C19955j c19955j = new C19955j(this);
        this.mBackgroundTintHelper = c19955j;
        c19955j.d(attributeSet, i12);
        M m12 = new M(this);
        this.mTextHelper = m12;
        m12.f(attributeSet, i12);
        m12.b();
        getEmojiTextViewHelper().a(attributeSet, i12);
    }
}
