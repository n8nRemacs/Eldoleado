package androidx.appcompat.widget;

import android.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.core.view.C22823h0;
import androidx.emoji2.text.e;
import j.InterfaceC42167x;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import l.C43521a;
import m.C43852a;
import o.C44495a;

/* loaded from: classes.dex */
public class SwitchCompat extends CompoundButton implements X {

    /* renamed from: S, reason: collision with root package name */
    public static final Property<SwitchCompat, Float> f22322S = new a(Float.class, "thumbPos");

    /* renamed from: T, reason: collision with root package name */
    public static final int[] f22323T = {R.attr.state_checked};

    /* renamed from: A, reason: collision with root package name */
    public float f22324A;

    /* renamed from: B, reason: collision with root package name */
    public int f22325B;

    /* renamed from: C, reason: collision with root package name */
    public int f22326C;

    /* renamed from: D, reason: collision with root package name */
    public int f22327D;

    /* renamed from: E, reason: collision with root package name */
    public int f22328E;

    /* renamed from: F, reason: collision with root package name */
    public int f22329F;

    /* renamed from: G, reason: collision with root package name */
    public int f22330G;

    /* renamed from: H, reason: collision with root package name */
    public int f22331H;

    /* renamed from: I, reason: collision with root package name */
    public boolean f22332I;

    /* renamed from: J, reason: collision with root package name */
    public final TextPaint f22333J;

    /* renamed from: K, reason: collision with root package name */
    public final ColorStateList f22334K;

    /* renamed from: L, reason: collision with root package name */
    public StaticLayout f22335L;

    /* renamed from: M, reason: collision with root package name */
    public StaticLayout f22336M;

    /* renamed from: N, reason: collision with root package name */
    @j.P
    public final C44495a f22337N;

    /* renamed from: O, reason: collision with root package name */
    public ObjectAnimator f22338O;

    /* renamed from: P, reason: collision with root package name */
    @j.N
    public C19972u f22339P;

    /* renamed from: Q, reason: collision with root package name */
    @j.P
    public b f22340Q;

    /* renamed from: R, reason: collision with root package name */
    public final Rect f22341R;

    /* renamed from: b, reason: collision with root package name */
    public Drawable f22342b;

    /* renamed from: c, reason: collision with root package name */
    public ColorStateList f22343c;

    /* renamed from: d, reason: collision with root package name */
    public PorterDuff.Mode f22344d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f22345e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f22346f;

    /* renamed from: g, reason: collision with root package name */
    public Drawable f22347g;

    /* renamed from: h, reason: collision with root package name */
    public ColorStateList f22348h;

    /* renamed from: i, reason: collision with root package name */
    public PorterDuff.Mode f22349i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f22350j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f22351k;

    /* renamed from: l, reason: collision with root package name */
    public int f22352l;

    /* renamed from: m, reason: collision with root package name */
    public int f22353m;

    /* renamed from: n, reason: collision with root package name */
    public int f22354n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f22355o;

    /* renamed from: p, reason: collision with root package name */
    public CharSequence f22356p;

    /* renamed from: q, reason: collision with root package name */
    public CharSequence f22357q;

    /* renamed from: r, reason: collision with root package name */
    public CharSequence f22358r;

    /* renamed from: s, reason: collision with root package name */
    public CharSequence f22359s;

    /* renamed from: t, reason: collision with root package name */
    public boolean f22360t;

    /* renamed from: u, reason: collision with root package name */
    public int f22361u;

    /* renamed from: v, reason: collision with root package name */
    public final int f22362v;

    /* renamed from: w, reason: collision with root package name */
    public float f22363w;

    /* renamed from: x, reason: collision with root package name */
    public float f22364x;

    /* renamed from: y, reason: collision with root package name */
    public final VelocityTracker f22365y;

    /* renamed from: z, reason: collision with root package name */
    public final int f22366z;

    public class a extends Property<SwitchCompat, Float> {
        @Override // android.util.Property
        public final Float get(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.f22324A);
        }

        @Override // android.util.Property
        public final void set(SwitchCompat switchCompat, Float f12) {
            switchCompat.setThumbPosition(f12.floatValue());
        }
    }

    public static class b extends e.g {

        /* renamed from: b, reason: collision with root package name */
        public final WeakReference f22367b;

        public b(SwitchCompat switchCompat) {
            this.f22367b = new WeakReference(switchCompat);
        }

        @Override // androidx.emoji2.text.e.g
        public final void a() {
            SwitchCompat switchCompat = (SwitchCompat) this.f22367b.get();
            if (switchCompat != null) {
                switchCompat.c();
            }
        }

        @Override // androidx.emoji2.text.e.g
        public final void b() {
            SwitchCompat switchCompat = (SwitchCompat) this.f22367b.get();
            if (switchCompat != null) {
                switchCompat.c();
            }
        }
    }

    public SwitchCompat(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, com.avito.android.R.attr.switchStyle);
    }

    @j.N
    private C19972u getEmojiTextViewHelper() {
        if (this.f22339P == null) {
            this.f22339P = new C19972u(this);
        }
        return this.f22339P;
    }

    private boolean getTargetCheckedState() {
        return this.f22324A > 0.5f;
    }

    private int getThumbOffset() {
        return (int) (((getLayoutDirection() == 1 ? 1.0f - this.f22324A : this.f22324A) * getThumbScrollRange()) + 0.5f);
    }

    private int getThumbScrollRange() {
        Drawable drawable = this.f22347g;
        if (drawable == null) {
            return 0;
        }
        Rect rect = this.f22341R;
        drawable.getPadding(rect);
        Drawable drawable2 = this.f22342b;
        Rect rectB = drawable2 != null ? V.b(drawable2) : V.f22426c;
        return ((((this.f22325B - this.f22327D) - rect.left) - rect.right) - rectB.left) - rectB.right;
    }

    private void setTextOffInternal(CharSequence charSequence) {
        this.f22358r = charSequence;
        C19972u emojiTextViewHelper = getEmojiTextViewHelper();
        TransformationMethod transformationMethodE = emojiTextViewHelper.f22628b.e(this.f22337N);
        if (transformationMethodE != null) {
            charSequence = transformationMethodE.getTransformation(charSequence, this);
        }
        this.f22359s = charSequence;
        this.f22336M = null;
        if (this.f22360t) {
            d();
        }
    }

    private void setTextOnInternal(CharSequence charSequence) {
        this.f22356p = charSequence;
        C19972u emojiTextViewHelper = getEmojiTextViewHelper();
        TransformationMethod transformationMethodE = emojiTextViewHelper.f22628b.e(this.f22337N);
        if (transformationMethodE != null) {
            charSequence = transformationMethodE.getTransformation(charSequence, this);
        }
        this.f22357q = charSequence;
        this.f22335L = null;
        if (this.f22360t) {
            d();
        }
    }

    public final void a() {
        Drawable drawable = this.f22342b;
        if (drawable != null) {
            if (this.f22345e || this.f22346f) {
                Drawable drawableMutate = drawable.mutate();
                this.f22342b = drawableMutate;
                if (this.f22345e) {
                    drawableMutate.setTintList(this.f22343c);
                }
                if (this.f22346f) {
                    this.f22342b.setTintMode(this.f22344d);
                }
                if (this.f22342b.isStateful()) {
                    this.f22342b.setState(getDrawableState());
                }
            }
        }
    }

    public final void b() {
        Drawable drawable = this.f22347g;
        if (drawable != null) {
            if (this.f22350j || this.f22351k) {
                Drawable drawableMutate = drawable.mutate();
                this.f22347g = drawableMutate;
                if (this.f22350j) {
                    drawableMutate.setTintList(this.f22348h);
                }
                if (this.f22351k) {
                    this.f22347g.setTintMode(this.f22349i);
                }
                if (this.f22347g.isStateful()) {
                    this.f22347g.setState(getDrawableState());
                }
            }
        }
    }

    public final void c() {
        setTextOnInternal(this.f22356p);
        setTextOffInternal(this.f22358r);
        requestLayout();
    }

    public final void d() {
        if (this.f22340Q == null && this.f22339P.f22628b.b() && androidx.emoji2.text.e.g()) {
            androidx.emoji2.text.e eVarA = androidx.emoji2.text.e.a();
            int iD2 = eVarA.d();
            if (iD2 == 3 || iD2 == 0) {
                b bVar = new b(this);
                this.f22340Q = bVar;
                eVarA.k(bVar);
            }
        }
    }

    @Override // android.view.View
    public final void draw(@j.N Canvas canvas) {
        int i12;
        int i13;
        int i14 = this.f22328E;
        int i15 = this.f22329F;
        int i16 = this.f22330G;
        int i17 = this.f22331H;
        int thumbOffset = getThumbOffset() + i14;
        Drawable drawable = this.f22342b;
        Rect rectB = drawable != null ? V.b(drawable) : V.f22426c;
        Drawable drawable2 = this.f22347g;
        Rect rect = this.f22341R;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            int i18 = rect.left;
            thumbOffset += i18;
            if (rectB != null) {
                int i19 = rectB.left;
                if (i19 > i18) {
                    i14 += i19 - i18;
                }
                int i22 = rectB.top;
                int i23 = rect.top;
                i12 = i22 > i23 ? (i22 - i23) + i15 : i15;
                int i24 = rectB.right;
                int i25 = rect.right;
                if (i24 > i25) {
                    i16 -= i24 - i25;
                }
                int i26 = rectB.bottom;
                int i27 = rect.bottom;
                if (i26 > i27) {
                    i13 = i17 - (i26 - i27);
                }
                this.f22347g.setBounds(i14, i12, i16, i13);
            } else {
                i12 = i15;
            }
            i13 = i17;
            this.f22347g.setBounds(i14, i12, i16, i13);
        }
        Drawable drawable3 = this.f22342b;
        if (drawable3 != null) {
            drawable3.getPadding(rect);
            int i28 = thumbOffset - rect.left;
            int i29 = thumbOffset + this.f22327D + rect.right;
            this.f22342b.setBounds(i28, i15, i29, i17);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i28, i15, i29, i17);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableHotspotChanged(float f12, float f13) {
        super.drawableHotspotChanged(f12, f13);
        Drawable drawable = this.f22342b;
        if (drawable != null) {
            drawable.setHotspot(f12, f13);
        }
        Drawable drawable2 = this.f22347g;
        if (drawable2 != null) {
            drawable2.setHotspot(f12, f13);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.f22342b;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.f22347g;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        if (getLayoutDirection() != 1) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.f22325B;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.f22354n : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingRight() {
        if (getLayoutDirection() == 1) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.f22325B;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.f22354n : compoundPaddingRight;
    }

    @Override // android.widget.TextView
    @j.P
    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.n.g(super.getCustomSelectionActionModeCallback());
    }

    public boolean getShowText() {
        return this.f22360t;
    }

    public boolean getSplitTrack() {
        return this.f22355o;
    }

    public int getSwitchMinWidth() {
        return this.f22353m;
    }

    public int getSwitchPadding() {
        return this.f22354n;
    }

    public CharSequence getTextOff() {
        return this.f22358r;
    }

    public CharSequence getTextOn() {
        return this.f22356p;
    }

    public Drawable getThumbDrawable() {
        return this.f22342b;
    }

    @InterfaceC42167x
    public final float getThumbPosition() {
        return this.f22324A;
    }

    public int getThumbTextPadding() {
        return this.f22352l;
    }

    @j.P
    public ColorStateList getThumbTintList() {
        return this.f22343c;
    }

    @j.P
    public PorterDuff.Mode getThumbTintMode() {
        return this.f22344d;
    }

    public Drawable getTrackDrawable() {
        return this.f22347g;
    }

    @j.P
    public ColorStateList getTrackTintList() {
        return this.f22348h;
    }

    @j.P
    public PorterDuff.Mode getTrackTintMode() {
        return this.f22349i;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f22342b;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f22347g;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.f22338O;
        if (objectAnimator == null || !objectAnimator.isStarted()) {
            return;
        }
        this.f22338O.end();
        this.f22338O = null;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public int[] onCreateDrawableState(int i12) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i12 + 1);
        if (isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, f22323T);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int width;
        super.onDraw(canvas);
        Drawable drawable = this.f22347g;
        Rect rect = this.f22341R;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i12 = this.f22329F;
        int i13 = this.f22331H;
        int i14 = i12 + rect.top;
        int i15 = i13 - rect.bottom;
        Drawable drawable2 = this.f22342b;
        if (drawable != null) {
            if (!this.f22355o || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect rectB = V.b(drawable2);
                drawable2.copyBounds(rect);
                rect.left += rectB.left;
                rect.right -= rectB.right;
                int iSave = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(iSave);
            }
        }
        int iSave2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        StaticLayout staticLayout = getTargetCheckedState() ? this.f22335L : this.f22336M;
        if (staticLayout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.f22334K;
            TextPaint textPaint = this.f22333J;
            if (colorStateList != null) {
                textPaint.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            textPaint.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                width = bounds.left + bounds.right;
            } else {
                width = getWidth();
            }
            canvas.translate((width / 2) - (staticLayout.getWidth() / 2), ((i14 + i15) / 2) - (staticLayout.getHeight() / 2));
            staticLayout.draw(canvas);
        }
        canvas.restoreToCount(iSave2);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        if (Build.VERSION.SDK_INT < 30) {
            CharSequence charSequence = isChecked() ? this.f22356p : this.f22358r;
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }
            CharSequence text = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(text);
            sb2.append(' ');
            sb2.append(charSequence);
            accessibilityNodeInfo.setText(sb2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z12, int i12, int i13, int i14, int i15) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iMax;
        int width;
        int paddingLeft;
        int height;
        int paddingTop;
        super.onLayout(z12, i12, i13, i14, i15);
        int iMax2 = 0;
        if (this.f22342b != null) {
            Drawable drawable = this.f22347g;
            Rect rect = this.f22341R;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect rectB = V.b(this.f22342b);
            iMax = Math.max(0, rectB.left - rect.left);
            iMax2 = Math.max(0, rectB.right - rect.right);
        } else {
            iMax = 0;
        }
        if (getLayoutDirection() == 1) {
            paddingLeft = getPaddingLeft() + iMax;
            width = ((this.f22325B + paddingLeft) - iMax) - iMax2;
        } else {
            width = (getWidth() - getPaddingRight()) - iMax2;
            paddingLeft = (width - this.f22325B) + iMax + iMax2;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int height2 = ((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2;
            int i16 = this.f22326C;
            int i17 = height2 - (i16 / 2);
            height = i16 + i17;
            paddingTop = i17;
        } else if (gravity != 80) {
            paddingTop = getPaddingTop();
            height = this.f22326C + paddingTop;
        } else {
            height = getHeight() - getPaddingBottom();
            paddingTop = height - this.f22326C;
        }
        this.f22328E = paddingLeft;
        this.f22329F = paddingTop;
        this.f22331H = height;
        this.f22330G = width;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i12, int i13) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int intrinsicWidth;
        int intrinsicHeight;
        int intrinsicHeight2 = 0;
        if (this.f22360t) {
            StaticLayout staticLayout = this.f22335L;
            TextPaint textPaint = this.f22333J;
            if (staticLayout == null) {
                CharSequence charSequence = this.f22357q;
                this.f22335L = new StaticLayout(charSequence, textPaint, charSequence != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
            if (this.f22336M == null) {
                CharSequence charSequence2 = this.f22359s;
                this.f22336M = new StaticLayout(charSequence2, textPaint, charSequence2 != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence2, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
        }
        Drawable drawable = this.f22342b;
        Rect rect = this.f22341R;
        if (drawable != null) {
            drawable.getPadding(rect);
            intrinsicWidth = (this.f22342b.getIntrinsicWidth() - rect.left) - rect.right;
            intrinsicHeight = this.f22342b.getIntrinsicHeight();
        } else {
            intrinsicWidth = 0;
            intrinsicHeight = 0;
        }
        this.f22327D = Math.max(this.f22360t ? (this.f22352l * 2) + Math.max(this.f22335L.getWidth(), this.f22336M.getWidth()) : 0, intrinsicWidth);
        Drawable drawable2 = this.f22347g;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            intrinsicHeight2 = this.f22347g.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int iMax = rect.left;
        int iMax2 = rect.right;
        Drawable drawable3 = this.f22342b;
        if (drawable3 != null) {
            Rect rectB = V.b(drawable3);
            iMax = Math.max(iMax, rectB.left);
            iMax2 = Math.max(iMax2, rectB.right);
        }
        int iMax3 = this.f22332I ? Math.max(this.f22353m, (this.f22327D * 2) + iMax + iMax2) : this.f22353m;
        int iMax4 = Math.max(intrinsicHeight2, intrinsicHeight);
        this.f22325B = iMax3;
        this.f22326C = iMax4;
        super.onMeasure(i12, i13);
        if (getMeasuredHeight() < iMax4) {
            setMeasuredDimension(getMeasuredWidthAndState(), iMax4);
        }
    }

    @Override // android.view.View
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.f22356p : this.f22358r;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x008c  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouchEvent(android.view.MotionEvent r10) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SwitchCompat.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z12) {
        super.setAllCaps(z12);
        getEmojiTextViewHelper().b(z12);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z12) throws Resources.NotFoundException {
        super.setChecked(z12);
        boolean zIsChecked = isChecked();
        if (zIsChecked) {
            if (Build.VERSION.SDK_INT >= 30) {
                CharSequence string = this.f22356p;
                if (string == null) {
                    string = getResources().getString(com.avito.android.R.string.abc_capital_on);
                }
                C22823h0.O(this, string);
            }
        } else if (Build.VERSION.SDK_INT >= 30) {
            CharSequence string2 = this.f22358r;
            if (string2 == null) {
                string2 = getResources().getString(com.avito.android.R.string.abc_capital_off);
            }
            C22823h0.O(this, string2);
        }
        if (getWindowToken() == null || !isLaidOut()) {
            ObjectAnimator objectAnimator = this.f22338O;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            setThumbPosition(zIsChecked ? 1.0f : 0.0f);
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, f22322S, zIsChecked ? 1.0f : 0.0f);
        this.f22338O = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(250L);
        this.f22338O.setAutoCancel(true);
        this.f22338O.start();
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(@j.P ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.n.h(callback, this));
    }

    public void setEmojiCompatEnabled(boolean z12) {
        getEmojiTextViewHelper().c(z12);
        setTextOnInternal(this.f22356p);
        setTextOffInternal(this.f22358r);
        requestLayout();
    }

    public final void setEnforceSwitchWidth(boolean z12) {
        this.f22332I = z12;
        invalidate();
    }

    @Override // android.widget.TextView
    public void setFilters(@j.N InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().f22628b.a(inputFilterArr));
    }

    public void setShowText(boolean z12) {
        if (this.f22360t != z12) {
            this.f22360t = z12;
            requestLayout();
            if (z12) {
                d();
            }
        }
    }

    public void setSplitTrack(boolean z12) {
        this.f22355o = z12;
        invalidate();
    }

    public void setSwitchMinWidth(int i12) {
        this.f22353m = i12;
        requestLayout();
    }

    public void setSwitchPadding(int i12) {
        this.f22354n = i12;
        requestLayout();
    }

    public void setSwitchTypeface(Typeface typeface) {
        TextPaint textPaint = this.f22333J;
        if ((textPaint.getTypeface() == null || textPaint.getTypeface().equals(typeface)) && (textPaint.getTypeface() != null || typeface == null)) {
            return;
        }
        textPaint.setTypeface(typeface);
        requestLayout();
        invalidate();
    }

    public void setTextOff(CharSequence charSequence) throws Resources.NotFoundException {
        setTextOffInternal(charSequence);
        requestLayout();
        if (isChecked() || Build.VERSION.SDK_INT < 30) {
            return;
        }
        CharSequence string = this.f22358r;
        if (string == null) {
            string = getResources().getString(com.avito.android.R.string.abc_capital_off);
        }
        C22823h0.O(this, string);
    }

    public void setTextOn(CharSequence charSequence) throws Resources.NotFoundException {
        setTextOnInternal(charSequence);
        requestLayout();
        if (!isChecked() || Build.VERSION.SDK_INT < 30) {
            return;
        }
        CharSequence string = this.f22356p;
        if (string == null) {
            string = getResources().getString(com.avito.android.R.string.abc_capital_on);
        }
        C22823h0.O(this, string);
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.f22342b;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f22342b = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbPosition(float f12) {
        this.f22324A = f12;
        invalidate();
    }

    public void setThumbResource(int i12) {
        setThumbDrawable(C43852a.a(getContext(), i12));
    }

    public void setThumbTextPadding(int i12) {
        this.f22352l = i12;
        requestLayout();
    }

    public void setThumbTintList(@j.P ColorStateList colorStateList) {
        this.f22343c = colorStateList;
        this.f22345e = true;
        a();
    }

    public void setThumbTintMode(@j.P PorterDuff.Mode mode) {
        this.f22344d = mode;
        this.f22346f = true;
        a();
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.f22347g;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f22347g = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i12) {
        setTrackDrawable(C43852a.a(getContext(), i12));
    }

    public void setTrackTintList(@j.P ColorStateList colorStateList) {
        this.f22348h = colorStateList;
        this.f22350j = true;
        b();
    }

    public void setTrackTintMode(@j.P PorterDuff.Mode mode) {
        this.f22349i = mode;
        this.f22351k = true;
        b();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final boolean verifyDrawable(@j.N Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f22342b || drawable == this.f22347g;
    }

    public SwitchCompat(@j.N Context context, @j.P AttributeSet attributeSet, int i12) throws Resources.NotFoundException {
        super(context, attributeSet, i12);
        this.f22343c = null;
        this.f22344d = null;
        this.f22345e = false;
        this.f22346f = false;
        this.f22348h = null;
        this.f22349i = null;
        this.f22350j = false;
        this.f22351k = false;
        this.f22365y = VelocityTracker.obtain();
        this.f22332I = true;
        this.f22341R = new Rect();
        y0.a(getContext(), this);
        TextPaint textPaint = new TextPaint(1);
        this.f22333J = textPaint;
        textPaint.density = getResources().getDisplayMetrics().density;
        int[] iArr = C43521a.m.f413374w;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        C22823h0.B(this, context, iArr, attributeSet, d0E.f22121b, i12);
        Drawable drawableB = d0E.b(2);
        this.f22342b = drawableB;
        if (drawableB != null) {
            drawableB.setCallback(this);
        }
        Drawable drawableB2 = d0E.b(11);
        this.f22347g = drawableB2;
        if (drawableB2 != null) {
            drawableB2.setCallback(this);
        }
        TypedArray typedArray = d0E.f22121b;
        setTextOnInternal(typedArray.getText(0));
        setTextOffInternal(typedArray.getText(1));
        this.f22360t = typedArray.getBoolean(3, true);
        this.f22352l = typedArray.getDimensionPixelSize(8, 0);
        this.f22353m = typedArray.getDimensionPixelSize(5, 0);
        this.f22354n = typedArray.getDimensionPixelSize(6, 0);
        this.f22355o = typedArray.getBoolean(4, false);
        ColorStateList colorStateListA = d0E.a(9);
        if (colorStateListA != null) {
            this.f22343c = colorStateListA;
            this.f22345e = true;
        }
        PorterDuff.Mode modeC = V.c(typedArray.getInt(10, -1), null);
        if (this.f22344d != modeC) {
            this.f22344d = modeC;
            this.f22346f = true;
        }
        if (this.f22345e || this.f22346f) {
            a();
        }
        ColorStateList colorStateListA2 = d0E.a(12);
        if (colorStateListA2 != null) {
            this.f22348h = colorStateListA2;
            this.f22350j = true;
        }
        PorterDuff.Mode modeC2 = V.c(typedArray.getInt(13, -1), null);
        if (this.f22349i != modeC2) {
            this.f22349i = modeC2;
            this.f22351k = true;
        }
        if (this.f22350j || this.f22351k) {
            b();
        }
        int resourceId = typedArray.getResourceId(7, 0);
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(resourceId, C43521a.m.f413375x);
            D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
            ColorStateList colorStateListA3 = d02.a(3);
            if (colorStateListA3 != null) {
                this.f22334K = colorStateListA3;
            } else {
                this.f22334K = getTextColors();
            }
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
            if (dimensionPixelSize != 0) {
                float f12 = dimensionPixelSize;
                if (f12 != textPaint.getTextSize()) {
                    textPaint.setTextSize(f12);
                    requestLayout();
                }
            }
            int i13 = typedArrayObtainStyledAttributes.getInt(1, -1);
            int i14 = typedArrayObtainStyledAttributes.getInt(2, -1);
            Typeface typeface = i13 != 1 ? i13 != 2 ? i13 != 3 ? null : Typeface.MONOSPACE : Typeface.SERIF : Typeface.SANS_SERIF;
            if (i14 > 0) {
                Typeface typefaceDefaultFromStyle = typeface == null ? Typeface.defaultFromStyle(i14) : Typeface.create(typeface, i14);
                setSwitchTypeface(typefaceDefaultFromStyle);
                int i15 = (~(typefaceDefaultFromStyle != null ? typefaceDefaultFromStyle.getStyle() : 0)) & i14;
                textPaint.setFakeBoldText((i15 & 1) != 0);
                textPaint.setTextSkewX((i15 & 2) != 0 ? -0.25f : 0.0f);
            } else {
                textPaint.setFakeBoldText(false);
                textPaint.setTextSkewX(0.0f);
                setSwitchTypeface(typeface);
            }
            if (typedArrayObtainStyledAttributes.getBoolean(14, false)) {
                Context context2 = getContext();
                C44495a c44495a = new C44495a();
                c44495a.f419253a = context2.getResources().getConfiguration().locale;
                this.f22337N = c44495a;
            } else {
                this.f22337N = null;
            }
            setTextOnInternal(this.f22356p);
            setTextOffInternal(this.f22358r);
            d02.f();
        }
        new M(this).f(attributeSet, i12);
        d0E.f();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.f22362v = viewConfiguration.getScaledTouchSlop();
        this.f22366z = viewConfiguration.getScaledMinimumFlingVelocity();
        getEmojiTextViewHelper().a(attributeSet, i12);
        refreshDrawableState();
        setChecked(isChecked());
    }
}
