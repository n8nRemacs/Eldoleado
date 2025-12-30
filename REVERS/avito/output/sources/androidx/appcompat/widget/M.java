package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.core.content.res.i;
import androidx.core.view.C22823h0;
import j.InterfaceC42164u;
import java.lang.ref.WeakReference;
import java.util.Locale;
import l.C43521a;

/* compiled from: AppCompatTextHelper.java */
/* loaded from: classes.dex */
class M {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final TextView f22203a;

    /* renamed from: b, reason: collision with root package name */
    public B0 f22204b;

    /* renamed from: c, reason: collision with root package name */
    public B0 f22205c;

    /* renamed from: d, reason: collision with root package name */
    public B0 f22206d;

    /* renamed from: e, reason: collision with root package name */
    public B0 f22207e;

    /* renamed from: f, reason: collision with root package name */
    public B0 f22208f;

    /* renamed from: g, reason: collision with root package name */
    public B0 f22209g;

    /* renamed from: h, reason: collision with root package name */
    public B0 f22210h;

    /* renamed from: i, reason: collision with root package name */
    @j.N
    public final P f22211i;

    /* renamed from: j, reason: collision with root package name */
    public int f22212j = 0;

    /* renamed from: k, reason: collision with root package name */
    public int f22213k = -1;

    /* renamed from: l, reason: collision with root package name */
    public Typeface f22214l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f22215m;

    /* compiled from: AppCompatTextHelper.java */
    @j.X
    public static class b {
        @InterfaceC42164u
        public static Locale a(String str) {
            return Locale.forLanguageTag(str);
        }
    }

    /* compiled from: AppCompatTextHelper.java */
    @j.X
    public static class c {
        @InterfaceC42164u
        public static LocaleList a(String str) {
            return LocaleList.forLanguageTags(str);
        }

        @InterfaceC42164u
        public static void b(TextView textView, LocaleList localeList) {
            textView.setTextLocales(localeList);
        }
    }

    /* compiled from: AppCompatTextHelper.java */
    @j.X
    public static class d {
        @InterfaceC42164u
        public static int a(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        @InterfaceC42164u
        public static void b(TextView textView, int i12, int i13, int i14, int i15) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i12, i13, i14, i15);
        }

        @InterfaceC42164u
        public static void c(TextView textView, int[] iArr, int i12) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i12);
        }

        @InterfaceC42164u
        public static boolean d(TextView textView, String str) {
            return textView.setFontVariationSettings(str);
        }
    }

    /* compiled from: AppCompatTextHelper.java */
    @j.X
    public static class e {
        @InterfaceC42164u
        public static Typeface a(Typeface typeface, int i12, boolean z12) {
            return Typeface.create(typeface, i12, z12);
        }
    }

    public M(@j.N TextView textView) {
        this.f22203a = textView;
        this.f22211i = new P(textView);
    }

    public static B0 c(Context context, r rVar, int i12) {
        ColorStateList colorStateListG;
        synchronized (rVar) {
            colorStateListG = rVar.f22611a.g(i12, context);
        }
        if (colorStateListG == null) {
            return null;
        }
        B0 b02 = new B0();
        b02.f22098d = true;
        b02.f22095a = colorStateListG;
        return b02;
    }

    public final void a(Drawable drawable, B0 b02) {
        if (drawable == null || b02 == null) {
            return;
        }
        r.e(drawable, b02, this.f22203a.getDrawableState());
    }

    public final void b() {
        B0 b02 = this.f22204b;
        TextView textView = this.f22203a;
        if (b02 != null || this.f22205c != null || this.f22206d != null || this.f22207e != null) {
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            a(compoundDrawables[0], this.f22204b);
            a(compoundDrawables[1], this.f22205c);
            a(compoundDrawables[2], this.f22206d);
            a(compoundDrawables[3], this.f22207e);
        }
        if (this.f22208f == null && this.f22209g == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
        a(compoundDrawablesRelative[0], this.f22208f);
        a(compoundDrawablesRelative[2], this.f22209g);
    }

    @j.P
    public final ColorStateList d() {
        B0 b02 = this.f22210h;
        if (b02 != null) {
            return b02.f22095a;
        }
        return null;
    }

    @j.P
    public final PorterDuff.Mode e() {
        B0 b02 = this.f22210h;
        if (b02 != null) {
            return b02.f22096b;
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    public final void f(@j.P AttributeSet attributeSet, int i12) {
        boolean z12;
        boolean z13;
        String string;
        String string2;
        int i13;
        int i14;
        int i15;
        float dimensionPixelSize;
        int i16;
        int resourceId;
        int i17;
        TextView textView = this.f22203a;
        Context context = textView.getContext();
        r rVarA = r.a();
        int[] iArr = C43521a.m.f413359h;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        C22823h0.B(textView, textView.getContext(), iArr, attributeSet, d0E.f22121b, i12);
        TypedArray typedArray = d0E.f22121b;
        int resourceId2 = typedArray.getResourceId(0, -1);
        if (typedArray.hasValue(3)) {
            this.f22204b = c(context, rVarA, typedArray.getResourceId(3, 0));
        }
        if (typedArray.hasValue(1)) {
            this.f22205c = c(context, rVarA, typedArray.getResourceId(1, 0));
        }
        if (typedArray.hasValue(4)) {
            this.f22206d = c(context, rVarA, typedArray.getResourceId(4, 0));
        }
        if (typedArray.hasValue(2)) {
            this.f22207e = c(context, rVarA, typedArray.getResourceId(2, 0));
        }
        if (typedArray.hasValue(5)) {
            this.f22208f = c(context, rVarA, typedArray.getResourceId(5, 0));
        }
        if (typedArray.hasValue(6)) {
            this.f22209g = c(context, rVarA, typedArray.getResourceId(6, 0));
        }
        d0E.f();
        boolean z14 = textView.getTransformationMethod() instanceof PasswordTransformationMethod;
        int[] iArr2 = C43521a.m.f413375x;
        if (resourceId2 != -1) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(resourceId2, iArr2);
            D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
            if (z14 || !typedArrayObtainStyledAttributes.hasValue(14)) {
                z12 = false;
                z13 = false;
            } else {
                z12 = typedArrayObtainStyledAttributes.getBoolean(14, false);
                z13 = true;
            }
            j(context, d02);
            if (typedArrayObtainStyledAttributes.hasValue(15)) {
                string2 = typedArrayObtainStyledAttributes.getString(15);
                i17 = 13;
            } else {
                i17 = 13;
                string2 = null;
            }
            string = typedArrayObtainStyledAttributes.hasValue(i17) ? typedArrayObtainStyledAttributes.getString(i17) : null;
            d02.f();
        } else {
            z12 = false;
            z13 = false;
            string = null;
            string2 = null;
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr2, i12, 0);
        D0 d03 = new D0(typedArrayObtainStyledAttributes2, context);
        if (!z14 && typedArrayObtainStyledAttributes2.hasValue(14)) {
            z12 = typedArrayObtainStyledAttributes2.getBoolean(14, false);
            z13 = true;
        }
        boolean z15 = z12;
        int i18 = Build.VERSION.SDK_INT;
        if (typedArrayObtainStyledAttributes2.hasValue(15)) {
            string2 = typedArrayObtainStyledAttributes2.getString(15);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(13)) {
            string = typedArrayObtainStyledAttributes2.getString(13);
        }
        if (i18 >= 28 && typedArrayObtainStyledAttributes2.hasValue(0) && typedArrayObtainStyledAttributes2.getDimensionPixelSize(0, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        j(context, d03);
        d03.f();
        if (!z14 && z13) {
            textView.setAllCaps(z15);
        }
        Typeface typeface = this.f22214l;
        if (typeface != null) {
            if (this.f22213k == -1) {
                textView.setTypeface(typeface, this.f22212j);
            } else {
                textView.setTypeface(typeface);
            }
        }
        if (string != null) {
            d.d(textView, string);
        }
        if (string2 != null) {
            c.b(textView, c.a(string2));
        }
        int[] iArr3 = C43521a.m.f413360i;
        P p12 = this.f22211i;
        Context context2 = p12.f22243h;
        TypedArray typedArrayObtainStyledAttributes3 = context2.obtainStyledAttributes(attributeSet, iArr3, i12, 0);
        TextView textView2 = p12.f22242g;
        C22823h0.B(textView2, textView2.getContext(), iArr3, attributeSet, typedArrayObtainStyledAttributes3, i12);
        if (typedArrayObtainStyledAttributes3.hasValue(5)) {
            p12.f22236a = typedArrayObtainStyledAttributes3.getInt(5, 0);
        }
        float dimension = typedArrayObtainStyledAttributes3.hasValue(4) ? typedArrayObtainStyledAttributes3.getDimension(4, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes3.hasValue(2) ? typedArrayObtainStyledAttributes3.getDimension(2, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes3.hasValue(1) ? typedArrayObtainStyledAttributes3.getDimension(1, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes3.hasValue(3) && (resourceId = typedArrayObtainStyledAttributes3.getResourceId(3, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes3.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr4 = new int[length];
            if (length > 0) {
                for (int i19 = 0; i19 < length; i19++) {
                    iArr4[i19] = typedArrayObtainTypedArray.getDimensionPixelSize(i19, -1);
                }
                int[] iArrA = P.a(iArr4);
                p12.f22240e = iArrA;
                boolean z16 = iArrA.length > 0;
                p12.f22241f = z16;
                if (z16) {
                    p12.f22236a = 1;
                    p12.f22238c = iArrA[0];
                    p12.f22239d = iArrA[r4 - 1];
                    p12.f22237b = -1.0f;
                }
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes3.recycle();
        if (!p12.d()) {
            p12.f22236a = 0;
        } else if (p12.f22236a == 1) {
            if (!p12.f22241f) {
                DisplayMetrics displayMetrics = context2.getResources().getDisplayMetrics();
                if (dimension2 == -1.0f) {
                    i16 = 2;
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                } else {
                    i16 = 2;
                }
                if (dimension3 == -1.0f) {
                    dimension3 = TypedValue.applyDimension(i16, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                if (dimension2 <= 0.0f) {
                    throw new IllegalArgumentException("Minimum auto-size text size (" + dimension2 + "px) is less or equal to (0px)");
                }
                if (dimension3 <= dimension2) {
                    throw new IllegalArgumentException("Maximum auto-size text size (" + dimension3 + "px) is less or equal to minimum auto-size text size (" + dimension2 + "px)");
                }
                if (dimension <= 0.0f) {
                    throw new IllegalArgumentException("The auto-size step granularity (" + dimension + "px) is less or equal to (0px)");
                }
                p12.f22236a = 1;
                p12.f22238c = dimension2;
                p12.f22239d = dimension3;
                p12.f22237b = dimension;
                p12.f22241f = false;
            }
            if (p12.d() && p12.f22236a == 1 && (!p12.f22241f || p12.f22240e.length == 0)) {
                int iFloor = ((int) Math.floor((p12.f22239d - p12.f22238c) / p12.f22237b)) + 1;
                int[] iArr5 = new int[iFloor];
                for (int i22 = 0; i22 < iFloor; i22++) {
                    iArr5[i22] = Math.round((i22 * p12.f22237b) + p12.f22238c);
                }
                p12.f22240e = P.a(iArr5);
            }
        }
        if (p12.f22236a != 0) {
            int[] iArr6 = p12.f22240e;
            if (iArr6.length > 0) {
                if (d.a(textView) != -1.0f) {
                    d.b(textView, Math.round(p12.f22238c), Math.round(p12.f22239d), Math.round(p12.f22237b), 0);
                } else {
                    d.c(textView, iArr6, 0);
                }
            }
        }
        TypedArray typedArrayObtainStyledAttributes4 = context.obtainStyledAttributes(attributeSet, iArr3);
        D0 d04 = new D0(typedArrayObtainStyledAttributes4, context);
        int resourceId3 = typedArrayObtainStyledAttributes4.getResourceId(8, -1);
        Drawable drawableB = resourceId3 != -1 ? rVarA.b(context, resourceId3) : null;
        int resourceId4 = typedArrayObtainStyledAttributes4.getResourceId(13, -1);
        Drawable drawableB2 = resourceId4 != -1 ? rVarA.b(context, resourceId4) : null;
        int resourceId5 = typedArrayObtainStyledAttributes4.getResourceId(9, -1);
        Drawable drawableB3 = resourceId5 != -1 ? rVarA.b(context, resourceId5) : null;
        int resourceId6 = typedArrayObtainStyledAttributes4.getResourceId(6, -1);
        Drawable drawableB4 = resourceId6 != -1 ? rVarA.b(context, resourceId6) : null;
        int resourceId7 = typedArrayObtainStyledAttributes4.getResourceId(10, -1);
        Drawable drawableB5 = resourceId7 != -1 ? rVarA.b(context, resourceId7) : null;
        int resourceId8 = typedArrayObtainStyledAttributes4.getResourceId(7, -1);
        Drawable drawableB6 = resourceId8 != -1 ? rVarA.b(context, resourceId8) : null;
        if (drawableB5 != null || drawableB6 != null) {
            Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
            if (drawableB5 == null) {
                drawableB5 = compoundDrawablesRelative[0];
            }
            if (drawableB2 == null) {
                drawableB2 = compoundDrawablesRelative[1];
            }
            if (drawableB6 == null) {
                drawableB6 = compoundDrawablesRelative[2];
            }
            if (drawableB4 == null) {
                drawableB4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableB5, drawableB2, drawableB6, drawableB4);
        } else if (drawableB != null || drawableB2 != null || drawableB3 != null || drawableB4 != null) {
            Drawable[] compoundDrawablesRelative2 = textView.getCompoundDrawablesRelative();
            Drawable drawable = compoundDrawablesRelative2[0];
            if (drawable == null && compoundDrawablesRelative2[2] == null) {
                Drawable[] compoundDrawables = textView.getCompoundDrawables();
                if (drawableB == null) {
                    drawableB = compoundDrawables[0];
                }
                if (drawableB2 == null) {
                    drawableB2 = compoundDrawables[1];
                }
                if (drawableB3 == null) {
                    drawableB3 = compoundDrawables[2];
                }
                if (drawableB4 == null) {
                    drawableB4 = compoundDrawables[3];
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawableB, drawableB2, drawableB3, drawableB4);
            } else {
                if (drawableB2 == null) {
                    drawableB2 = compoundDrawablesRelative2[1];
                }
                if (drawableB4 == null) {
                    drawableB4 = compoundDrawablesRelative2[3];
                }
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawableB2, compoundDrawablesRelative2[2], drawableB4);
            }
        }
        if (typedArrayObtainStyledAttributes4.hasValue(11)) {
            textView.setCompoundDrawableTintList(d04.a(11));
        }
        if (typedArrayObtainStyledAttributes4.hasValue(12)) {
            i13 = -1;
            textView.setCompoundDrawableTintMode(V.c(typedArrayObtainStyledAttributes4.getInt(12, -1), null));
        } else {
            i13 = -1;
        }
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(15, i13);
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(18, i13);
        if (typedArrayObtainStyledAttributes4.hasValue(19)) {
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes4.peekValue(19);
            if (typedValuePeekValue == null || typedValuePeekValue.type != 5) {
                i14 = -1;
                dimensionPixelSize = typedArrayObtainStyledAttributes4.getDimensionPixelSize(19, -1);
                i15 = -1;
            } else {
                int i23 = typedValuePeekValue.data;
                i15 = i23 & 15;
                dimensionPixelSize = TypedValue.complexToFloat(i23);
                i14 = -1;
            }
        } else {
            i14 = -1;
            i15 = -1;
            dimensionPixelSize = -1.0f;
        }
        d04.f();
        if (dimensionPixelSize2 != i14) {
            androidx.core.widget.n.b(textView, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != i14) {
            androidx.core.widget.n.c(textView, dimensionPixelSize3);
        }
        if (dimensionPixelSize != -1.0f) {
            if (i15 == i14) {
                androidx.core.widget.n.d(textView, (int) dimensionPixelSize);
            } else {
                androidx.core.widget.n.e(textView, i15, dimensionPixelSize);
            }
        }
    }

    public final void g(int i12, Context context) {
        String string;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i12, C43521a.m.f413375x);
        D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(14);
        TextView textView = this.f22203a;
        if (zHasValue) {
            textView.setAllCaps(typedArrayObtainStyledAttributes.getBoolean(14, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(0) && typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        j(context, d02);
        if (typedArrayObtainStyledAttributes.hasValue(13) && (string = typedArrayObtainStyledAttributes.getString(13)) != null) {
            d.d(textView, string);
        }
        d02.f();
        Typeface typeface = this.f22214l;
        if (typeface != null) {
            textView.setTypeface(typeface, this.f22212j);
        }
    }

    public final void h(@j.P ColorStateList colorStateList) {
        if (this.f22210h == null) {
            this.f22210h = new B0();
        }
        B0 b02 = this.f22210h;
        b02.f22095a = colorStateList;
        b02.f22098d = colorStateList != null;
        this.f22204b = b02;
        this.f22205c = b02;
        this.f22206d = b02;
        this.f22207e = b02;
        this.f22208f = b02;
        this.f22209g = b02;
    }

    public final void i(@j.P PorterDuff.Mode mode) {
        if (this.f22210h == null) {
            this.f22210h = new B0();
        }
        B0 b02 = this.f22210h;
        b02.f22096b = mode;
        b02.f22097c = mode != null;
        this.f22204b = b02;
        this.f22205c = b02;
        this.f22206d = b02;
        this.f22207e = b02;
        this.f22208f = b02;
        this.f22209g = b02;
    }

    public final void j(Context context, D0 d02) {
        String string;
        int i12 = this.f22212j;
        TypedArray typedArray = d02.f22121b;
        this.f22212j = typedArray.getInt(2, i12);
        int i13 = Build.VERSION.SDK_INT;
        if (i13 >= 28) {
            int i14 = typedArray.getInt(11, -1);
            this.f22213k = i14;
            if (i14 != -1) {
                this.f22212j &= 2;
            }
        }
        if (!typedArray.hasValue(10) && !typedArray.hasValue(12)) {
            if (typedArray.hasValue(1)) {
                this.f22215m = false;
                int i15 = typedArray.getInt(1, 1);
                if (i15 == 1) {
                    this.f22214l = Typeface.SANS_SERIF;
                    return;
                } else if (i15 == 2) {
                    this.f22214l = Typeface.SERIF;
                    return;
                } else {
                    if (i15 != 3) {
                        return;
                    }
                    this.f22214l = Typeface.MONOSPACE;
                    return;
                }
            }
            return;
        }
        this.f22214l = null;
        int i16 = typedArray.hasValue(12) ? 12 : 10;
        int i17 = this.f22213k;
        int i18 = this.f22212j;
        if (!context.isRestricted()) {
            try {
                Typeface typefaceD = d02.d(i16, this.f22212j, new a(i17, i18, new WeakReference(this.f22203a)));
                if (typefaceD != null) {
                    if (i13 < 28 || this.f22213k == -1) {
                        this.f22214l = typefaceD;
                    } else {
                        this.f22214l = e.a(Typeface.create(typefaceD, 0), this.f22213k, (this.f22212j & 2) != 0);
                    }
                }
                this.f22215m = this.f22214l == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.f22214l != null || (string = typedArray.getString(i16)) == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 28 || this.f22213k == -1) {
            this.f22214l = Typeface.create(string, this.f22212j);
        } else {
            this.f22214l = e.a(Typeface.create(string, 0), this.f22213k, (this.f22212j & 2) != 0);
        }
    }

    /* compiled from: AppCompatTextHelper.java */
    public class a extends i.f {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ int f22216a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22217b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ WeakReference f22218c;

        public a(int i12, int i13, WeakReference weakReference) {
            this.f22216a = i12;
            this.f22217b = i13;
            this.f22218c = weakReference;
        }

        @Override // androidx.core.content.res.i.f
        public final void c(@j.N Typeface typeface) {
            int i12;
            if (Build.VERSION.SDK_INT >= 28 && (i12 = this.f22216a) != -1) {
                typeface = e.a(typeface, i12, (this.f22217b & 2) != 0);
            }
            M m12 = M.this;
            if (m12.f22215m) {
                m12.f22214l = typeface;
                TextView textView = (TextView) this.f22218c.get();
                if (textView != null) {
                    if (textView.isAttachedToWindow()) {
                        textView.post(new N(textView, typeface, m12.f22212j));
                    } else {
                        textView.setTypeface(typeface, m12.f22212j);
                    }
                }
            }
        }

        @Override // androidx.core.content.res.i.f
        public final void b(int i12) {
        }
    }
}
