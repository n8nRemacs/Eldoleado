package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.r;
import androidx.collection.C20204c0;
import androidx.collection.C20212g0;
import androidx.collection.q1;
import com.avito.android.R;
import j.InterfaceC42165v;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: ResourceManagerInternal.java */
@RestrictTo
/* renamed from: androidx.appcompat.widget.m0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C19962m0 {

    /* renamed from: g, reason: collision with root package name */
    public static C19962m0 f22568g;

    /* renamed from: a, reason: collision with root package name */
    public WeakHashMap<Context, q1<ColorStateList>> f22570a;

    /* renamed from: b, reason: collision with root package name */
    public final WeakHashMap<Context, C20204c0<WeakReference<Drawable.ConstantState>>> f22571b = new WeakHashMap<>(0);

    /* renamed from: c, reason: collision with root package name */
    public TypedValue f22572c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f22573d;

    /* renamed from: e, reason: collision with root package name */
    public f f22574e;

    /* renamed from: f, reason: collision with root package name */
    public static final PorterDuff.Mode f22567f = PorterDuff.Mode.SRC_IN;

    /* renamed from: h, reason: collision with root package name */
    public static final c f22569h = new c(6);

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$a */
    public static class a implements e {
    }

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$b */
    public static class b implements e {
    }

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$c */
    public static class c extends C20212g0<Integer, PorterDuffColorFilter> {
    }

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$d */
    public static class d implements e {
    }

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$e */
    public interface e {
    }

    /* compiled from: ResourceManagerInternal.java */
    @RestrictTo
    /* renamed from: androidx.appcompat.widget.m0$f */
    public interface f {
    }

    /* compiled from: ResourceManagerInternal.java */
    /* renamed from: androidx.appcompat.widget.m0$g */
    public static class g implements e {
    }

    public static synchronized C19962m0 b() {
        try {
            if (f22568g == null) {
                f22568g = new C19962m0();
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return f22568g;
    }

    public static synchronized PorterDuffColorFilter f(int i12, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        c cVar = f22569h;
        cVar.getClass();
        int i13 = (31 + i12) * 31;
        porterDuffColorFilter = cVar.get(Integer.valueOf(mode.hashCode() + i13));
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = new PorterDuffColorFilter(i12, mode);
            cVar.put(Integer.valueOf(mode.hashCode() + i13), porterDuffColorFilter);
        }
        return porterDuffColorFilter;
    }

    public final synchronized void a(@j.N Context context, long j12, @j.N Drawable drawable) {
        try {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                C20204c0<WeakReference<Drawable.ConstantState>> c20204c0 = this.f22571b.get(context);
                if (c20204c0 == null) {
                    c20204c0 = new C20204c0<>();
                    this.f22571b.put(context, c20204c0);
                }
                c20204c0.l(j12, new WeakReference<>(constantState));
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public final synchronized Drawable c(@j.N Context context, long j12) {
        C20204c0<WeakReference<Drawable.ConstantState>> c20204c0 = this.f22571b.get(context);
        if (c20204c0 == null) {
            return null;
        }
        WeakReference<Drawable.ConstantState> weakReferenceF = c20204c0.f(j12);
        if (weakReferenceF != null) {
            Drawable.ConstantState constantState = weakReferenceF.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            c20204c0.m(j12);
        }
        return null;
    }

    public final synchronized Drawable d(@j.N Context context, @InterfaceC42165v int i12) {
        return e(context, i12, false);
    }

    public final synchronized Drawable e(@j.N Context context, @InterfaceC42165v int i12, boolean z12) {
        Drawable drawableC;
        try {
            if (!this.f22573d) {
                this.f22573d = true;
                Drawable drawableD = d(context, R.drawable.abc_vector_test);
                if (drawableD == null || (!(drawableD instanceof androidx.vectordrawable.graphics.drawable.i) && !"android.graphics.drawable.VectorDrawable".equals(drawableD.getClass().getName()))) {
                    this.f22573d = false;
                    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
                }
            }
            if (this.f22572c == null) {
                this.f22572c = new TypedValue();
            }
            TypedValue typedValue = this.f22572c;
            context.getResources().getValue(i12, typedValue, true);
            long j12 = (typedValue.assetCookie << 32) | typedValue.data;
            drawableC = c(context, j12);
            if (drawableC == null) {
                LayerDrawable layerDrawableC = null;
                if (this.f22574e != null) {
                    if (i12 == R.drawable.abc_cab_background_top_material) {
                        layerDrawableC = new LayerDrawable(new Drawable[]{d(context, R.drawable.abc_cab_background_internal_bg), d(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
                    } else if (i12 == R.drawable.abc_ratingbar_material) {
                        layerDrawableC = r.a.c(this, context, R.dimen.abc_star_big);
                    } else if (i12 == R.drawable.abc_ratingbar_indicator_material) {
                        layerDrawableC = r.a.c(this, context, R.dimen.abc_star_medium);
                    } else if (i12 == R.drawable.abc_ratingbar_small_material) {
                        layerDrawableC = r.a.c(this, context, R.dimen.abc_star_small);
                    }
                }
                drawableC = layerDrawableC;
                if (drawableC != null) {
                    drawableC.setChangingConfigurations(typedValue.changingConfigurations);
                    a(context, j12, drawableC);
                }
            }
            if (drawableC == null) {
                drawableC = androidx.core.content.d.getDrawable(context, i12);
            }
            if (drawableC != null) {
                drawableC = h(context, i12, z12, drawableC);
            }
            if (drawableC != null) {
                V.a(drawableC);
            }
        } finally {
        }
        return drawableC;
    }

    public final synchronized ColorStateList g(@InterfaceC42165v int i12, @j.N Context context) {
        ColorStateList colorStateListF;
        q1<ColorStateList> q1Var;
        WeakHashMap<Context, q1<ColorStateList>> weakHashMap = this.f22570a;
        ColorStateList colorStateListD = null;
        colorStateListF = (weakHashMap == null || (q1Var = weakHashMap.get(context)) == null) ? null : q1Var.f(i12);
        if (colorStateListF == null) {
            f fVar = this.f22574e;
            if (fVar != null) {
                colorStateListD = ((r.a) fVar).d(i12, context);
            }
            if (colorStateListD != null) {
                if (this.f22570a == null) {
                    this.f22570a = new WeakHashMap<>();
                }
                q1<ColorStateList> q1Var2 = this.f22570a.get(context);
                if (q1Var2 == null) {
                    q1Var2 = new q1<>();
                    this.f22570a.put(context, q1Var2);
                }
                q1Var2.a(i12, colorStateListD);
            }
            colorStateListF = colorStateListD;
        }
        return colorStateListF;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.graphics.drawable.Drawable h(@j.N android.content.Context r9, @j.InterfaceC42165v int r10, boolean r11, @j.N android.graphics.drawable.Drawable r12) {
        /*
            r8 = this;
            android.content.res.ColorStateList r0 = r8.g(r10, r9)
            r1 = 0
            if (r0 == 0) goto L21
            android.graphics.drawable.Drawable r12 = r12.mutate()
            r12.setTintList(r0)
            androidx.appcompat.widget.m0$f r9 = r8.f22574e
            if (r9 != 0) goto L13
            goto L1a
        L13:
            r9 = 2131230993(0x7f080111, float:1.8078054E38)
            if (r10 != r9) goto L1a
            android.graphics.PorterDuff$Mode r1 = android.graphics.PorterDuff.Mode.MULTIPLY
        L1a:
            if (r1 == 0) goto Lf3
            r12.setTintMode(r1)
            goto Lf3
        L21:
            androidx.appcompat.widget.m0$f r0 = r8.f22574e
            r2 = 2130970033(0x7f0405b1, float:1.7548765E38)
            r3 = 2130970031(0x7f0405af, float:1.754876E38)
            if (r0 == 0) goto L96
            r4 = 2131230988(0x7f08010c, float:1.8078044E38)
            r5 = 16908301(0x102000d, float:2.3877265E-38)
            r6 = 16908303(0x102000f, float:2.387727E-38)
            r7 = 16908288(0x1020000, float:2.387723E-38)
            if (r10 != r4) goto L60
            r10 = r12
            android.graphics.drawable.LayerDrawable r10 = (android.graphics.drawable.LayerDrawable) r10
            android.graphics.drawable.Drawable r11 = r10.findDrawableByLayerId(r7)
            int r0 = androidx.appcompat.widget.y0.c(r2, r9)
            android.graphics.PorterDuff$Mode r1 = androidx.appcompat.widget.r.f22609b
            androidx.appcompat.widget.r.a.e(r11, r0, r1)
            android.graphics.drawable.Drawable r11 = r10.findDrawableByLayerId(r6)
            int r0 = androidx.appcompat.widget.y0.c(r2, r9)
            androidx.appcompat.widget.r.a.e(r11, r0, r1)
            android.graphics.drawable.Drawable r10 = r10.findDrawableByLayerId(r5)
            int r9 = androidx.appcompat.widget.y0.c(r3, r9)
            androidx.appcompat.widget.r.a.e(r10, r9, r1)
            goto Lf3
        L60:
            r4 = 2131230979(0x7f080103, float:1.8078026E38)
            if (r10 == r4) goto L6f
            r4 = 2131230978(0x7f080102, float:1.8078024E38)
            if (r10 == r4) goto L6f
            r4 = 2131230980(0x7f080104, float:1.8078028E38)
            if (r10 != r4) goto L96
        L6f:
            r10 = r12
            android.graphics.drawable.LayerDrawable r10 = (android.graphics.drawable.LayerDrawable) r10
            android.graphics.drawable.Drawable r11 = r10.findDrawableByLayerId(r7)
            int r0 = androidx.appcompat.widget.y0.b(r2, r9)
            android.graphics.PorterDuff$Mode r1 = androidx.appcompat.widget.r.f22609b
            androidx.appcompat.widget.r.a.e(r11, r0, r1)
            android.graphics.drawable.Drawable r11 = r10.findDrawableByLayerId(r6)
            int r0 = androidx.appcompat.widget.y0.c(r3, r9)
            androidx.appcompat.widget.r.a.e(r11, r0, r1)
            android.graphics.drawable.Drawable r10 = r10.findDrawableByLayerId(r5)
            int r9 = androidx.appcompat.widget.y0.c(r3, r9)
            androidx.appcompat.widget.r.a.e(r10, r9, r1)
            goto Lf3
        L96:
            if (r0 == 0) goto Lf0
            androidx.appcompat.widget.r$a r0 = (androidx.appcompat.widget.r.a) r0
            android.graphics.PorterDuff$Mode r4 = androidx.appcompat.widget.r.f22609b
            int[] r5 = r0.f22612a
            boolean r5 = androidx.appcompat.widget.r.a.a(r10, r5)
            r6 = 1
            r7 = -1
            if (r5 == 0) goto La8
        La6:
            r10 = r7
            goto Ld9
        La8:
            int[] r2 = r0.f22614c
            boolean r2 = androidx.appcompat.widget.r.a.a(r10, r2)
            if (r2 == 0) goto Lb2
            r2 = r3
            goto La6
        Lb2:
            int[] r0 = r0.f22615d
            boolean r0 = androidx.appcompat.widget.r.a.a(r10, r0)
            r2 = 16842801(0x1010031, float:2.3693695E-38)
            if (r0 == 0) goto Lc0
            android.graphics.PorterDuff$Mode r4 = android.graphics.PorterDuff.Mode.MULTIPLY
            goto La6
        Lc0:
            r0 = 2131230965(0x7f0800f5, float:1.8077998E38)
            if (r10 != r0) goto Ld0
            r10 = 1109603123(0x42233333, float:40.8)
            int r10 = java.lang.Math.round(r10)
            r2 = 16842800(0x1010030, float:2.3693693E-38)
            goto Ld9
        Ld0:
            r0 = 2131230947(0x7f0800e3, float:1.8077961E38)
            if (r10 != r0) goto Ld6
            goto La6
        Ld6:
            r2 = 0
            r6 = r2
            goto La6
        Ld9:
            if (r6 == 0) goto Lf0
            android.graphics.drawable.Drawable r11 = r12.mutate()
            int r9 = androidx.appcompat.widget.y0.c(r2, r9)
            android.graphics.PorterDuffColorFilter r9 = androidx.appcompat.widget.r.c(r9, r4)
            r11.setColorFilter(r9)
            if (r10 == r7) goto Lf3
            r11.setAlpha(r10)
            goto Lf3
        Lf0:
            if (r11 == 0) goto Lf3
            r12 = r1
        Lf3:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.C19962m0.h(android.content.Context, int, boolean, android.graphics.drawable.Drawable):android.graphics.drawable.Drawable");
    }
}
