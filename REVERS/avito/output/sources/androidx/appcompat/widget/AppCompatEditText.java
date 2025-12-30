package androidx.appcompat.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassifier;
import android.widget.EditText;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.G;
import androidx.appcompat.widget.L;
import androidx.core.view.C22814d;
import androidx.core.view.C22823h0;
import androidx.core.view.inputmethod.c;
import androidx.core.view.inputmethod.d;
import com.avito.android.R;
import j.InterfaceC42165v;

/* loaded from: classes.dex */
public class AppCompatEditText extends EditText implements androidx.core.view.Y, androidx.core.view.O, X, androidx.core.widget.z {

    @j.N
    private final C19971t mAppCompatEmojiEditTextHelper;
    private final C19955j mBackgroundTintHelper;
    private final androidx.core.widget.w mDefaultOnReceiveContentListener;

    @j.P
    private a mSuperCaller;
    private final L mTextClassifierHelper;
    private final M mTextHelper;

    @j.X
    public class a {
        public a() {
        }
    }

    public AppCompatEditText(@j.N Context context) {
        this(context, null);
    }

    @j.j0
    @j.N
    @j.X
    private a getSuperCaller() {
        if (this.mSuperCaller == null) {
            this.mSuperCaller = new a();
        }
        return this.mSuperCaller;
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

    @Override // android.widget.TextView
    @j.N
    @j.X
    public TextClassifier getTextClassifier() {
        L l12;
        if (Build.VERSION.SDK_INT >= 28 || (l12 = this.mTextClassifierHelper) == null) {
            return super.getTextClassifier();
        }
        TextClassifier textClassifier = l12.f22187b;
        return textClassifier == null ? L.a.a(l12.f22186a) : textClassifier;
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
    @j.P
    public InputConnection onCreateInputConnection(@j.N EditorInfo editorInfo) {
        String[] strArrN;
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        this.mTextHelper.getClass();
        int i12 = Build.VERSION.SDK_INT;
        if (i12 < 30 && inputConnectionOnCreateInputConnection != null) {
            androidx.core.view.inputmethod.a.a(editorInfo, getText());
        }
        C19973v.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        if (inputConnectionOnCreateInputConnection != null && i12 <= 30 && (strArrN = C22823h0.n(this)) != null) {
            editorInfo.contentMimeTypes = strArrN;
            inputConnectionOnCreateInputConnection = androidx.core.view.inputmethod.c.a(inputConnectionOnCreateInputConnection, editorInfo, new c.d() { // from class: androidx.core.view.inputmethod.b
                @Override // androidx.core.view.inputmethod.c.d
                public final boolean a(d dVar, int i13, Bundle bundle) {
                    if ((i13 & 1) != 0) {
                        try {
                            dVar.d();
                            InputContentInfo inputContentInfo = dVar.f45075a.f45076a;
                            bundle = bundle == null ? new Bundle() : new Bundle(bundle);
                            bundle.putParcelable("androidx.core.view.extra.INPUT_CONTENT_INFO", inputContentInfo);
                        } catch (Exception unused) {
                            return false;
                        }
                    }
                    ClipDescription description = dVar.f45075a.f45076a.getDescription();
                    d.a aVar = dVar.f45075a;
                    C22814d.b bVar = new C22814d.b(new ClipData(description, new ClipData.Item(aVar.f45076a.getContentUri())), 2);
                    bVar.d(aVar.f45076a.getLinkUri());
                    bVar.b(bundle);
                    return C22823h0.w(this.f45073a, bVar.a()) == null;
                }
            });
        }
        return this.mAppCompatEmojiEditTextHelper.f22626b.b(inputConnectionOnCreateInputConnection, editorInfo);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int i12 = Build.VERSION.SDK_INT;
        if (i12 < 30 || i12 >= 33) {
            return;
        }
        ((InputMethodManager) getContext().getSystemService("input_method")).isActive(this);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        Activity activity;
        boolean zA2 = false;
        if (Build.VERSION.SDK_INT < 31 && dragEvent.getLocalState() == null && C22823h0.n(this) != null) {
            Context context = getContext();
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    activity = null;
                    break;
                }
                if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
            if (activity == null) {
                toString();
            } else if (dragEvent.getAction() != 1 && dragEvent.getAction() == 3) {
                zA2 = G.a.a(dragEvent, this, activity);
            }
        }
        if (zA2) {
            return true;
        }
        return super.onDragEvent(dragEvent);
    }

    @Override // androidx.core.view.O
    @j.P
    public C22814d onReceiveContent(@j.N C22814d c22814d) {
        return this.mDefaultOnReceiveContentListener.a(this, c22814d);
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i12) {
        if (Build.VERSION.SDK_INT >= 31 || C22823h0.n(this) == null || !(i12 == 16908322 || i12 == 16908337)) {
            return super.onTextContextMenuItem(i12);
        }
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        ClipData primaryClip = clipboardManager == null ? null : clipboardManager.getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            C22814d.b bVar = new C22814d.b(primaryClip, 1);
            bVar.c(i12 == 16908322 ? 0 : 1);
            C22823h0.w(this, bVar.a());
        }
        return true;
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

    @Override // android.widget.TextView
    @j.X
    public void setTextClassifier(@j.P TextClassifier textClassifier) {
        L l12;
        if (Build.VERSION.SDK_INT >= 28 || (l12 = this.mTextClassifierHelper) == null) {
            super.setTextClassifier(textClassifier);
        } else {
            l12.f22187b = textClassifier;
        }
    }

    public AppCompatEditText(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    @Override // android.widget.EditText, android.widget.TextView
    @j.P
    public Editable getText() {
        return Build.VERSION.SDK_INT >= 28 ? super.getText() : super.getEditableText();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatEditText(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
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
        L l12 = new L();
        l12.f22186a = this;
        this.mTextClassifierHelper = l12;
        this.mDefaultOnReceiveContentListener = new androidx.core.widget.w();
        C19971t c19971t = new C19971t(this);
        this.mAppCompatEmojiEditTextHelper = c19971t;
        c19971t.b(attributeSet, i12);
        initEmojiKeyListener(c19971t);
    }
}
