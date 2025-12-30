package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.i;
import j.InterfaceC42164u;
import m.C43852a;

/* compiled from: TintTypedArray.java */
@RestrictTo
/* loaded from: classes.dex */
public class D0 {

    /* renamed from: a, reason: collision with root package name */
    public final Context f22120a;

    /* renamed from: b, reason: collision with root package name */
    public final TypedArray f22121b;

    /* renamed from: c, reason: collision with root package name */
    public TypedValue f22122c;

    /* compiled from: TintTypedArray.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static int a(TypedArray typedArray) {
            return typedArray.getChangingConfigurations();
        }

        @InterfaceC42164u
        public static int b(TypedArray typedArray, int i12) {
            return typedArray.getType(i12);
        }
    }

    public D0(TypedArray typedArray, Context context) {
        this.f22120a = context;
        this.f22121b = typedArray;
    }

    public static D0 e(Context context, AttributeSet attributeSet, int[] iArr, int i12) {
        return new D0(context.obtainStyledAttributes(attributeSet, iArr, i12, 0), context);
    }

    public final ColorStateList a(int i12) {
        int resourceId;
        ColorStateList colorStateList;
        TypedArray typedArray = this.f22121b;
        return (!typedArray.hasValue(i12) || (resourceId = typedArray.getResourceId(i12, 0)) == 0 || (colorStateList = androidx.core.content.d.getColorStateList(this.f22120a, resourceId)) == null) ? typedArray.getColorStateList(i12) : colorStateList;
    }

    public final Drawable b(int i12) {
        int resourceId;
        TypedArray typedArray = this.f22121b;
        return (!typedArray.hasValue(i12) || (resourceId = typedArray.getResourceId(i12, 0)) == 0) ? typedArray.getDrawable(i12) : C43852a.a(this.f22120a, resourceId);
    }

    public final Drawable c(int i12) {
        int resourceId;
        Drawable drawableE;
        if (!this.f22121b.hasValue(i12) || (resourceId = this.f22121b.getResourceId(i12, 0)) == 0) {
            return null;
        }
        r rVarA = r.a();
        Context context = this.f22120a;
        synchronized (rVarA) {
            drawableE = rVarA.f22611a.e(context, resourceId, true);
        }
        return drawableE;
    }

    @j.P
    public final Typeface d(@j.g0 int i12, int i13, @j.P i.f fVar) {
        int resourceId = this.f22121b.getResourceId(i12, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.f22122c == null) {
            this.f22122c = new TypedValue();
        }
        TypedValue typedValue = this.f22122c;
        ThreadLocal<TypedValue> threadLocal = androidx.core.content.res.i.f44673a;
        Context context = this.f22120a;
        if (context.isRestricted()) {
            return null;
        }
        return androidx.core.content.res.i.d(context, resourceId, typedValue, i13, fVar, true, false);
    }

    public final void f() {
        this.f22121b.recycle();
    }
}
