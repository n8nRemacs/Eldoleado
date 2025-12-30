package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.C22767g;
import l.C43521a;

/* compiled from: ThemeUtils.java */
@RestrictTo
/* loaded from: classes.dex */
public class y0 {

    /* renamed from: a, reason: collision with root package name */
    public static final ThreadLocal<TypedValue> f22680a = new ThreadLocal<>();

    /* renamed from: b, reason: collision with root package name */
    public static final int[] f22681b = {-16842910};

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f22682c = {R.attr.state_focused};

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f22683d = {R.attr.state_pressed};

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f22684e = {R.attr.state_checked};

    /* renamed from: f, reason: collision with root package name */
    public static final int[] f22685f = new int[0];

    /* renamed from: g, reason: collision with root package name */
    public static final int[] f22686g = new int[1];

    public static void a(@j.N Context context, @j.N View view) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(C43521a.m.f413361j);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(117)) {
                view.getClass().toString();
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static int b(int i12, @j.N Context context) {
        ColorStateList colorStateListD = d(i12, context);
        if (colorStateListD != null && colorStateListD.isStateful()) {
            return colorStateListD.getColorForState(f22681b, colorStateListD.getDefaultColor());
        }
        ThreadLocal<TypedValue> threadLocal = f22680a;
        TypedValue typedValue = threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        float f12 = typedValue.getFloat();
        return C22767g.i(c(i12, context), Math.round(Color.alpha(r4) * f12));
    }

    public static int c(int i12, @j.N Context context) {
        int[] iArr = f22686g;
        iArr[0] = i12;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        D0 d02 = new D0(typedArrayObtainStyledAttributes, context);
        try {
            return typedArrayObtainStyledAttributes.getColor(0, 0);
        } finally {
            d02.f();
        }
    }

    @j.P
    public static ColorStateList d(int i12, @j.N Context context) {
        int[] iArr = f22686g;
        iArr[0] = i12;
        D0 d02 = new D0(context.obtainStyledAttributes((AttributeSet) null, iArr), context);
        try {
            return d02.a(0);
        } finally {
            d02.f();
        }
    }
}
