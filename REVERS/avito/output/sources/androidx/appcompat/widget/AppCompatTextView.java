package androidx.appcompat.widget;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.L;
import androidx.core.text.g;
import j.InterfaceC42165v;
import j.InterfaceC42167x;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import m.C43852a;

/* loaded from: classes.dex */
public class AppCompatTextView extends TextView implements androidx.core.view.Y, androidx.core.widget.z, androidx.core.widget.b, X {
    private final C19955j mBackgroundTintHelper;

    @j.N
    private C19972u mEmojiTextViewHelper;
    private boolean mIsSetTypefaceProcessing;

    @j.P
    private Future<androidx.core.text.g> mPrecomputedTextFuture;

    @j.P
    private a mSuperCaller;
    private final L mTextClassifierHelper;
    private final M mTextHelper;

    public interface a {
        int a();

        int b();

        void c(int i12, int i13, int i14, int i15);

        void d(@j.U int i12);

        int e();

        void f(int i12);

        void g(@j.U int i12);

        TextClassifier h();

        void i(int[] iArr, int i12);

        int j();

        void k(int i12, @InterfaceC42167x float f12);

        int[] l();

        void m(@j.P TextClassifier textClassifier);
    }

    @j.X
    public class c extends b {
        public c() {
            super();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.b, androidx.appcompat.widget.AppCompatTextView.a
        public final void d(@j.U int i12) {
            AppCompatTextView.super.setFirstBaselineToTopHeight(i12);
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.b, androidx.appcompat.widget.AppCompatTextView.a
        public final void g(@j.U int i12) {
            AppCompatTextView.super.setLastBaselineToBottomHeight(i12);
        }
    }

    @j.X
    public class d extends c {
        public d() {
            super();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.b, androidx.appcompat.widget.AppCompatTextView.a
        public final void k(int i12, float f12) {
            AppCompatTextView.super.setLineHeight(i12, f12);
        }
    }

    public AppCompatTextView(@j.N Context context) {
        this(context, null);
    }

    private void consumeTextFutureAndSetBlocking() throws ExecutionException, InterruptedException {
        Future<androidx.core.text.g> future = this.mPrecomputedTextFuture;
        if (future != null) {
            try {
                this.mPrecomputedTextFuture = null;
                androidx.core.widget.n.f(this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.mEmojiTextViewHelper == null) {
            this.mEmojiTextViewHelper = new C19972u(this);
        }
        return this.mEmojiTextViewHelper;
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
        return getSuperCaller().b();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int getAutoSizeMinTextSize() {
        return getSuperCaller().a();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int getAutoSizeStepGranularity() {
        return getSuperCaller().e();
    }

    @Override // android.widget.TextView
    @RestrictTo
    public int[] getAutoSizeTextAvailableSizes() {
        return getSuperCaller().l();
    }

    @Override // android.widget.TextView
    @SuppressLint({"WrongConstant"})
    @RestrictTo
    public int getAutoSizeTextType() {
        return getSuperCaller().j() == 1 ? 1 : 0;
    }

    @Override // android.widget.TextView
    @j.P
    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.n.g(super.getCustomSelectionActionModeCallback());
    }

    @Override // android.widget.TextView
    public int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    @Override // android.widget.TextView
    public int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    @j.j0
    @j.X
    public a getSuperCaller() {
        if (this.mSuperCaller == null) {
            int i12 = Build.VERSION.SDK_INT;
            if (i12 >= 34) {
                this.mSuperCaller = new d();
            } else if (i12 >= 28) {
                this.mSuperCaller = new c();
            } else {
                this.mSuperCaller = new b();
            }
        }
        return this.mSuperCaller;
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

    @Override // android.widget.TextView
    public CharSequence getText() throws ExecutionException, InterruptedException {
        consumeTextFutureAndSetBlocking();
        return super.getText();
    }

    @Override // android.widget.TextView
    @j.N
    @j.X
    public TextClassifier getTextClassifier() {
        L l12;
        if (Build.VERSION.SDK_INT >= 28 || (l12 = this.mTextClassifierHelper) == null) {
            return getSuperCaller().h();
        }
        TextClassifier textClassifier = l12.f22187b;
        return textClassifier == null ? L.a.a(l12.f22186a) : textClassifier;
    }

    @j.N
    public g.b getTextMetricsParamsCompat() {
        return androidx.core.widget.n.a(this);
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        this.mTextHelper.getClass();
        if (Build.VERSION.SDK_INT < 30 && inputConnectionOnCreateInputConnection != null) {
            androidx.core.view.inputmethod.a.a(editorInfo, getText());
        }
        C19973v.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int i12 = Build.VERSION.SDK_INT;
        if (i12 < 30 || i12 >= 33 || !onCheckIsTextEditor()) {
            return;
        }
        ((InputMethodManager) getContext().getSystemService("input_method")).isActive(this);
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z12, int i12, int i13, int i14, int i15) {
        super.onLayout(z12, i12, i13, i14, i15);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.getClass();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i12, int i13) throws ExecutionException, InterruptedException {
        consumeTextFutureAndSetBlocking();
        super.onMeasure(i12, i13);
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
        getSuperCaller().c(i12, i13, i14, i15);
    }

    @Override // android.widget.TextView
    @RestrictTo
    public void setAutoSizeTextTypeUniformWithPresetSizes(@j.N int[] iArr, int i12) {
        getSuperCaller().i(iArr, i12);
    }

    @Override // android.widget.TextView
    @RestrictTo
    public void setAutoSizeTextTypeWithDefaults(int i12) {
        getSuperCaller().f(i12);
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
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(@j.P Drawable drawable, @j.P Drawable drawable2, @j.P Drawable drawable3, @j.P Drawable drawable4) {
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        M m12 = this.mTextHelper;
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

    @Override // android.widget.TextView
    public void setFilters(@j.N InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().f22628b.a(inputFilterArr));
    }

    @Override // android.widget.TextView
    public void setFirstBaselineToTopHeight(@j.U @j.F int i12) {
        if (Build.VERSION.SDK_INT >= 28) {
            getSuperCaller().d(i12);
        } else {
            androidx.core.widget.n.b(this, i12);
        }
    }

    @Override // android.widget.TextView
    public void setLastBaselineToBottomHeight(@j.U @j.F int i12) {
        if (Build.VERSION.SDK_INT >= 28) {
            getSuperCaller().g(i12);
        } else {
            androidx.core.widget.n.c(this, i12);
        }
    }

    @Override // android.widget.TextView
    public void setLineHeight(@j.U @j.F int i12) {
        androidx.core.widget.n.d(this, i12);
    }

    public void setPrecomputedText(@j.N androidx.core.text.g gVar) {
        androidx.core.widget.n.f(this, gVar);
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
    @j.X
    public void setTextClassifier(@j.P TextClassifier textClassifier) {
        L l12;
        if (Build.VERSION.SDK_INT >= 28 || (l12 = this.mTextClassifierHelper) == null) {
            getSuperCaller().m(textClassifier);
        } else {
            l12.f22187b = textClassifier;
        }
    }

    public void setTextFuture(@j.P Future<androidx.core.text.g> future) {
        this.mPrecomputedTextFuture = future;
        if (future != null) {
            requestLayout();
        }
    }

    public void setTextMetricsParamsCompat(@j.N g.b bVar) {
        TextDirectionHeuristic textDirectionHeuristic;
        TextDirectionHeuristic textDirectionHeuristic2 = bVar.f44874b;
        TextDirectionHeuristic textDirectionHeuristic3 = TextDirectionHeuristics.FIRSTSTRONG_RTL;
        int i12 = 1;
        if (textDirectionHeuristic2 != textDirectionHeuristic3 && textDirectionHeuristic2 != (textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR)) {
            if (textDirectionHeuristic2 == TextDirectionHeuristics.ANYRTL_LTR) {
                i12 = 2;
            } else if (textDirectionHeuristic2 == TextDirectionHeuristics.LTR) {
                i12 = 3;
            } else if (textDirectionHeuristic2 == TextDirectionHeuristics.RTL) {
                i12 = 4;
            } else if (textDirectionHeuristic2 == TextDirectionHeuristics.LOCALE) {
                i12 = 5;
            } else if (textDirectionHeuristic2 == textDirectionHeuristic) {
                i12 = 6;
            } else if (textDirectionHeuristic2 == textDirectionHeuristic3) {
                i12 = 7;
            }
        }
        setTextDirection(i12);
        getPaint().set(bVar.f44873a);
        setBreakStrategy(bVar.f44875c);
        setHyphenationFrequency(bVar.f44876d);
    }

    @Override // android.widget.TextView
    public void setTextSize(int i12, float f12) {
        super.setTextSize(i12, f12);
    }

    @Override // android.widget.TextView
    public void setTypeface(@j.P Typeface typeface, int i12) {
        Typeface typefaceCreate;
        if (this.mIsSetTypefaceProcessing) {
            return;
        }
        if (typeface == null || i12 <= 0) {
            typefaceCreate = null;
        } else {
            Context context = getContext();
            androidx.core.graphics.F f12 = androidx.core.graphics.z.f44783a;
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            typefaceCreate = Typeface.create(typeface, i12);
        }
        this.mIsSetTypefaceProcessing = true;
        if (typefaceCreate != null) {
            typeface = typefaceCreate;
        }
        try {
            super.setTypeface(typeface, i12);
        } finally {
            this.mIsSetTypefaceProcessing = false;
        }
    }

    public AppCompatTextView(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    @Override // android.widget.TextView
    public void setLineHeight(int i12, @InterfaceC42167x float f12) {
        if (Build.VERSION.SDK_INT >= 34) {
            getSuperCaller().k(i12, f12);
        } else {
            androidx.core.widget.n.e(this, i12, f12);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatTextView(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        A0.a(context);
        this.mIsSetTypefaceProcessing = false;
        this.mSuperCaller = null;
        y0.a(getContext(), this);
        C19955j c19955j = new C19955j(this);
        this.mBackgroundTintHelper = c19955j;
        c19955j.d(attributeSet, i12);
        M m12 = new M(this);
        this.mTextHelper = m12;
        m12.f(attributeSet, i12);
        m12.b();
        L l12 = new L();
        l12.f22186a = this;
        this.mTextClassifierHelper = l12;
        getEmojiTextViewHelper().a(attributeSet, i12);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i12, int i13, int i14, int i15) {
        Context context = getContext();
        setCompoundDrawablesRelativeWithIntrinsicBounds(i12 != 0 ? C43852a.a(context, i12) : null, i13 != 0 ? C43852a.a(context, i13) : null, i14 != 0 ? C43852a.a(context, i14) : null, i15 != 0 ? C43852a.a(context, i15) : null);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int i12, int i13, int i14, int i15) {
        Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(i12 != 0 ? C43852a.a(context, i12) : null, i13 != 0 ? C43852a.a(context, i13) : null, i14 != 0 ? C43852a.a(context, i14) : null, i15 != 0 ? C43852a.a(context, i15) : null);
        M m12 = this.mTextHelper;
        if (m12 != null) {
            m12.b();
        }
    }

    @j.X
    public class b implements a {
        public b() {
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final int a() {
            return AppCompatTextView.super.getAutoSizeMinTextSize();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final int b() {
            return AppCompatTextView.super.getAutoSizeMaxTextSize();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final void c(int i12, int i13, int i14, int i15) {
            AppCompatTextView.super.setAutoSizeTextTypeUniformWithConfiguration(i12, i13, i14, i15);
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final int e() {
            return AppCompatTextView.super.getAutoSizeStepGranularity();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final void f(int i12) {
            AppCompatTextView.super.setAutoSizeTextTypeWithDefaults(i12);
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final TextClassifier h() {
            return AppCompatTextView.super.getTextClassifier();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final void i(int[] iArr, int i12) {
            AppCompatTextView.super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i12);
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final int j() {
            return AppCompatTextView.super.getAutoSizeTextType();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final int[] l() {
            return AppCompatTextView.super.getAutoSizeTextAvailableSizes();
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public final void m(@j.P TextClassifier textClassifier) {
            AppCompatTextView.super.setTextClassifier(textClassifier);
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public void d(int i12) {
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public void g(int i12) {
        }

        @Override // androidx.appcompat.widget.AppCompatTextView.a
        public void k(int i12, float f12) {
        }
    }
}
