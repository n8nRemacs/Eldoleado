package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import androidx.appcompat.graphics.drawable.e;
import j.InterfaceC42156l;
import j.N;
import j.X;

/* compiled from: DrawableContainerCompat.java */
/* loaded from: classes.dex */
public class b extends Drawable implements Drawable.Callback {

    /* renamed from: n, reason: collision with root package name */
    public static final /* synthetic */ int f21595n = 0;

    /* renamed from: b, reason: collision with root package name */
    public d f21596b;

    /* renamed from: c, reason: collision with root package name */
    public Rect f21597c;

    /* renamed from: d, reason: collision with root package name */
    public Drawable f21598d;

    /* renamed from: e, reason: collision with root package name */
    public Drawable f21599e;

    /* renamed from: g, reason: collision with root package name */
    public boolean f21601g;

    /* renamed from: i, reason: collision with root package name */
    public boolean f21603i;

    /* renamed from: j, reason: collision with root package name */
    public Runnable f21604j;

    /* renamed from: k, reason: collision with root package name */
    public long f21605k;

    /* renamed from: l, reason: collision with root package name */
    public long f21606l;

    /* renamed from: m, reason: collision with root package name */
    public c f21607m;

    /* renamed from: f, reason: collision with root package name */
    public int f21600f = 255;

    /* renamed from: h, reason: collision with root package name */
    public int f21602h = -1;

    /* compiled from: DrawableContainerCompat.java */
    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ e f21608b;

        public a(e eVar) {
            this.f21608b = eVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            e eVar = this.f21608b;
            eVar.a(true);
            eVar.invalidateSelf();
        }
    }

    /* compiled from: DrawableContainerCompat.java */
    @X
    /* renamed from: androidx.appcompat.graphics.drawable.b$b, reason: collision with other inner class name */
    public static class C1517b {
    }

    /* compiled from: DrawableContainerCompat.java */
    public static abstract class d extends Drawable.ConstantState {

        /* renamed from: A, reason: collision with root package name */
        public final int f21610A;

        /* renamed from: B, reason: collision with root package name */
        public boolean f21611B;

        /* renamed from: C, reason: collision with root package name */
        public ColorFilter f21612C;

        /* renamed from: D, reason: collision with root package name */
        public boolean f21613D;

        /* renamed from: E, reason: collision with root package name */
        public ColorStateList f21614E;

        /* renamed from: F, reason: collision with root package name */
        public PorterDuff.Mode f21615F;

        /* renamed from: G, reason: collision with root package name */
        public boolean f21616G;

        /* renamed from: H, reason: collision with root package name */
        public boolean f21617H;

        /* renamed from: a, reason: collision with root package name */
        public final e f21618a;

        /* renamed from: b, reason: collision with root package name */
        public Resources f21619b;

        /* renamed from: c, reason: collision with root package name */
        public int f21620c;

        /* renamed from: d, reason: collision with root package name */
        public int f21621d;

        /* renamed from: e, reason: collision with root package name */
        public int f21622e;

        /* renamed from: f, reason: collision with root package name */
        public SparseArray<Drawable.ConstantState> f21623f;

        /* renamed from: g, reason: collision with root package name */
        public final Drawable[] f21624g;

        /* renamed from: h, reason: collision with root package name */
        public final int f21625h;

        /* renamed from: i, reason: collision with root package name */
        public final boolean f21626i;

        /* renamed from: j, reason: collision with root package name */
        public boolean f21627j;

        /* renamed from: k, reason: collision with root package name */
        public Rect f21628k;

        /* renamed from: l, reason: collision with root package name */
        public final boolean f21629l;

        /* renamed from: m, reason: collision with root package name */
        public boolean f21630m;

        /* renamed from: n, reason: collision with root package name */
        public int f21631n;

        /* renamed from: o, reason: collision with root package name */
        public int f21632o;

        /* renamed from: p, reason: collision with root package name */
        public int f21633p;

        /* renamed from: q, reason: collision with root package name */
        public int f21634q;

        /* renamed from: r, reason: collision with root package name */
        public boolean f21635r;

        /* renamed from: s, reason: collision with root package name */
        public int f21636s;

        /* renamed from: t, reason: collision with root package name */
        public boolean f21637t;

        /* renamed from: u, reason: collision with root package name */
        public boolean f21638u;

        /* renamed from: v, reason: collision with root package name */
        public boolean f21639v;

        /* renamed from: w, reason: collision with root package name */
        public boolean f21640w;

        /* renamed from: x, reason: collision with root package name */
        public boolean f21641x;

        /* renamed from: y, reason: collision with root package name */
        public int f21642y;

        /* renamed from: z, reason: collision with root package name */
        public final int f21643z;

        public d(e.a aVar, e eVar, Resources resources) {
            this.f21626i = false;
            this.f21629l = false;
            this.f21641x = true;
            this.f21643z = 0;
            this.f21610A = 0;
            this.f21618a = eVar;
            this.f21619b = resources != null ? resources : aVar != null ? aVar.f21619b : null;
            int i12 = aVar != null ? aVar.f21620c : 0;
            int i13 = b.f21595n;
            i12 = resources != null ? resources.getDisplayMetrics().densityDpi : i12;
            i12 = i12 == 0 ? 160 : i12;
            this.f21620c = i12;
            if (aVar == null) {
                this.f21624g = new Drawable[10];
                this.f21625h = 0;
                return;
            }
            this.f21621d = aVar.f21621d;
            this.f21622e = aVar.f21622e;
            this.f21639v = true;
            this.f21640w = true;
            this.f21626i = aVar.f21626i;
            this.f21629l = aVar.f21629l;
            this.f21641x = aVar.f21641x;
            this.f21642y = aVar.f21642y;
            this.f21643z = aVar.f21643z;
            this.f21610A = aVar.f21610A;
            this.f21611B = aVar.f21611B;
            this.f21612C = aVar.f21612C;
            this.f21613D = aVar.f21613D;
            this.f21614E = aVar.f21614E;
            this.f21615F = aVar.f21615F;
            this.f21616G = aVar.f21616G;
            this.f21617H = aVar.f21617H;
            if (aVar.f21620c == i12) {
                if (aVar.f21627j) {
                    this.f21628k = aVar.f21628k != null ? new Rect(aVar.f21628k) : null;
                    this.f21627j = true;
                }
                if (aVar.f21630m) {
                    this.f21631n = aVar.f21631n;
                    this.f21632o = aVar.f21632o;
                    this.f21633p = aVar.f21633p;
                    this.f21634q = aVar.f21634q;
                    this.f21630m = true;
                }
            }
            if (aVar.f21635r) {
                this.f21636s = aVar.f21636s;
                this.f21635r = true;
            }
            if (aVar.f21637t) {
                this.f21638u = aVar.f21638u;
                this.f21637t = true;
            }
            Drawable[] drawableArr = aVar.f21624g;
            this.f21624g = new Drawable[drawableArr.length];
            this.f21625h = aVar.f21625h;
            SparseArray<Drawable.ConstantState> sparseArray = aVar.f21623f;
            if (sparseArray != null) {
                this.f21623f = sparseArray.clone();
            } else {
                this.f21623f = new SparseArray<>(this.f21625h);
            }
            int i14 = this.f21625h;
            for (int i15 = 0; i15 < i14; i15++) {
                Drawable drawable = drawableArr[i15];
                if (drawable != null) {
                    Drawable.ConstantState constantState = drawable.getConstantState();
                    if (constantState != null) {
                        this.f21623f.put(i15, constantState);
                    } else {
                        this.f21624g[i15] = drawableArr[i15];
                    }
                }
            }
        }

        public final void a() {
            this.f21630m = true;
            b();
            int i12 = this.f21625h;
            Drawable[] drawableArr = this.f21624g;
            this.f21632o = -1;
            this.f21631n = -1;
            this.f21634q = 0;
            this.f21633p = 0;
            for (int i13 = 0; i13 < i12; i13++) {
                Drawable drawable = drawableArr[i13];
                int intrinsicWidth = drawable.getIntrinsicWidth();
                if (intrinsicWidth > this.f21631n) {
                    this.f21631n = intrinsicWidth;
                }
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicHeight > this.f21632o) {
                    this.f21632o = intrinsicHeight;
                }
                int minimumWidth = drawable.getMinimumWidth();
                if (minimumWidth > this.f21633p) {
                    this.f21633p = minimumWidth;
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (minimumHeight > this.f21634q) {
                    this.f21634q = minimumHeight;
                }
            }
        }

        public final void b() {
            SparseArray<Drawable.ConstantState> sparseArray = this.f21623f;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i12 = 0; i12 < size; i12++) {
                    int iKeyAt = this.f21623f.keyAt(i12);
                    Drawable.ConstantState constantStateValueAt = this.f21623f.valueAt(i12);
                    Drawable[] drawableArr = this.f21624g;
                    Drawable drawableNewDrawable = constantStateValueAt.newDrawable(this.f21619b);
                    drawableNewDrawable.setLayoutDirection(this.f21642y);
                    Drawable drawableMutate = drawableNewDrawable.mutate();
                    drawableMutate.setCallback(this.f21618a);
                    drawableArr[iKeyAt] = drawableMutate;
                }
                this.f21623f = null;
            }
        }

        public final Drawable c(int i12) {
            int iIndexOfKey;
            Drawable drawable = this.f21624g[i12];
            if (drawable != null) {
                return drawable;
            }
            SparseArray<Drawable.ConstantState> sparseArray = this.f21623f;
            if (sparseArray == null || (iIndexOfKey = sparseArray.indexOfKey(i12)) < 0) {
                return null;
            }
            Drawable drawableNewDrawable = this.f21623f.valueAt(iIndexOfKey).newDrawable(this.f21619b);
            drawableNewDrawable.setLayoutDirection(this.f21642y);
            Drawable drawableMutate = drawableNewDrawable.mutate();
            drawableMutate.setCallback(this.f21618a);
            this.f21624g[i12] = drawableMutate;
            this.f21623f.removeAt(iIndexOfKey);
            if (this.f21623f.size() == 0) {
                this.f21623f = null;
            }
            return drawableMutate;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @X
        public final boolean canApplyTheme() {
            int i12 = this.f21625h;
            Drawable[] drawableArr = this.f21624g;
            for (int i13 = 0; i13 < i12; i13++) {
                Drawable drawable = drawableArr[i13];
                if (drawable == null) {
                    Drawable.ConstantState constantState = this.f21623f.get(i13);
                    if (constantState != null && constantState.canApplyTheme()) {
                        return true;
                    }
                } else if (drawable.canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        public void d() {
            int i12 = this.f21625h;
            Drawable[] drawableArr = this.f21624g;
            for (int i13 = 0; i13 < i12; i13++) {
                Drawable drawable = drawableArr[i13];
                if (drawable != null) {
                    drawable.mutate();
                }
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return this.f21621d | this.f21622e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0066 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a(boolean r14) {
        /*
            r13 = this;
            r0 = 1
            r13.f21601g = r0
            long r1 = android.os.SystemClock.uptimeMillis()
            android.graphics.drawable.Drawable r3 = r13.f21598d
            r4 = 255(0xff, double:1.26E-321)
            r6 = 0
            r8 = 0
            if (r3 == 0) goto L36
            long r9 = r13.f21605k
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 == 0) goto L38
            int r11 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r11 > 0) goto L22
            int r9 = r13.f21600f
            r3.setAlpha(r9)
            r13.f21605k = r6
            goto L38
        L22:
            long r9 = r9 - r1
            long r9 = r9 * r4
            int r9 = (int) r9
            androidx.appcompat.graphics.drawable.b$d r10 = r13.f21596b
            int r10 = r10.f21643z
            int r9 = r9 / r10
            int r9 = 255 - r9
            int r10 = r13.f21600f
            int r9 = r9 * r10
            int r9 = r9 / 255
            r3.setAlpha(r9)
            r3 = r0
            goto L39
        L36:
            r13.f21605k = r6
        L38:
            r3 = r8
        L39:
            android.graphics.drawable.Drawable r9 = r13.f21599e
            if (r9 == 0) goto L61
            long r10 = r13.f21606l
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 == 0) goto L63
            int r12 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r12 > 0) goto L50
            r9.setVisible(r8, r8)
            r0 = 0
            r13.f21599e = r0
            r13.f21606l = r6
            goto L63
        L50:
            long r10 = r10 - r1
            long r10 = r10 * r4
            int r3 = (int) r10
            androidx.appcompat.graphics.drawable.b$d r4 = r13.f21596b
            int r4 = r4.f21610A
            int r3 = r3 / r4
            int r4 = r13.f21600f
            int r3 = r3 * r4
            int r3 = r3 / 255
            r9.setAlpha(r3)
            goto L64
        L61:
            r13.f21606l = r6
        L63:
            r0 = r3
        L64:
            if (r14 == 0) goto L70
            if (r0 == 0) goto L70
            java.lang.Runnable r14 = r13.f21604j
            r3 = 16
            long r1 = r1 + r3
            r13.scheduleSelf(r14, r1)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.b.a(boolean):void");
    }

    @Override // android.graphics.drawable.Drawable
    @X
    public void applyTheme(@N Resources.Theme theme) {
        d dVar = this.f21596b;
        if (theme == null) {
            dVar.getClass();
            return;
        }
        dVar.b();
        int i12 = dVar.f21625h;
        Drawable[] drawableArr = dVar.f21624g;
        for (int i13 = 0; i13 < i12; i13++) {
            Drawable drawable = drawableArr[i13];
            if (drawable != null && drawable.canApplyTheme()) {
                drawableArr[i13].applyTheme(theme);
                dVar.f21622e |= drawableArr[i13].getChangingConfigurations();
            }
        }
        Resources resources = theme.getResources();
        if (resources != null) {
            dVar.f21619b = resources;
            int i14 = resources.getDisplayMetrics().densityDpi;
            if (i14 == 0) {
                i14 = 160;
            }
            int i15 = dVar.f21620c;
            dVar.f21620c = i14;
            if (i15 != i14) {
                dVar.f21630m = false;
                dVar.f21627j = false;
            }
        }
    }

    public d b() {
        return this.f21596b;
    }

    public final void c(Drawable drawable) {
        if (this.f21607m == null) {
            this.f21607m = new c();
        }
        c cVar = this.f21607m;
        cVar.f21609b = drawable.getCallback();
        drawable.setCallback(cVar);
        try {
            if (this.f21596b.f21643z <= 0 && this.f21601g) {
                drawable.setAlpha(this.f21600f);
            }
            d dVar = this.f21596b;
            if (dVar.f21613D) {
                drawable.setColorFilter(dVar.f21612C);
            } else {
                if (dVar.f21616G) {
                    drawable.setTintList(dVar.f21614E);
                }
                d dVar2 = this.f21596b;
                if (dVar2.f21617H) {
                    drawable.setTintMode(dVar2.f21615F);
                }
            }
            drawable.setVisible(isVisible(), true);
            drawable.setDither(this.f21596b.f21641x);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            drawable.setLayoutDirection(getLayoutDirection());
            drawable.setAutoMirrored(this.f21596b.f21611B);
            Rect rect = this.f21597c;
            if (rect != null) {
                drawable.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom);
            }
            c cVar2 = this.f21607m;
            Drawable.Callback callback = cVar2.f21609b;
            cVar2.f21609b = null;
            drawable.setCallback(callback);
        } catch (Throwable th2) {
            c cVar3 = this.f21607m;
            Drawable.Callback callback2 = cVar3.f21609b;
            cVar3.f21609b = null;
            drawable.setCallback(callback2);
            throw th2;
        }
    }

    @Override // android.graphics.drawable.Drawable
    @X
    public final boolean canApplyTheme() {
        return this.f21596b.canApplyTheme();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean d(int r10) {
        /*
            r9 = this;
            int r0 = r9.f21602h
            r1 = 0
            if (r10 != r0) goto L6
            return r1
        L6:
            long r2 = android.os.SystemClock.uptimeMillis()
            androidx.appcompat.graphics.drawable.b$d r0 = r9.f21596b
            int r0 = r0.f21610A
            r4 = 0
            r5 = 0
            if (r0 <= 0) goto L2e
            android.graphics.drawable.Drawable r0 = r9.f21599e
            if (r0 == 0) goto L1a
            r0.setVisible(r1, r1)
        L1a:
            android.graphics.drawable.Drawable r0 = r9.f21598d
            if (r0 == 0) goto L29
            r9.f21599e = r0
            androidx.appcompat.graphics.drawable.b$d r0 = r9.f21596b
            int r0 = r0.f21610A
            long r0 = (long) r0
            long r0 = r0 + r2
            r9.f21606l = r0
            goto L35
        L29:
            r9.f21599e = r4
            r9.f21606l = r5
            goto L35
        L2e:
            android.graphics.drawable.Drawable r0 = r9.f21598d
            if (r0 == 0) goto L35
            r0.setVisible(r1, r1)
        L35:
            if (r10 < 0) goto L55
            androidx.appcompat.graphics.drawable.b$d r0 = r9.f21596b
            int r1 = r0.f21625h
            if (r10 >= r1) goto L55
            android.graphics.drawable.Drawable r0 = r0.c(r10)
            r9.f21598d = r0
            r9.f21602h = r10
            if (r0 == 0) goto L5a
            androidx.appcompat.graphics.drawable.b$d r10 = r9.f21596b
            int r10 = r10.f21643z
            if (r10 <= 0) goto L51
            long r7 = (long) r10
            long r2 = r2 + r7
            r9.f21605k = r2
        L51:
            r9.c(r0)
            goto L5a
        L55:
            r9.f21598d = r4
            r10 = -1
            r9.f21602h = r10
        L5a:
            long r0 = r9.f21605k
            int r10 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            r0 = 1
            if (r10 != 0) goto L67
            long r1 = r9.f21606l
            int r10 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r10 == 0) goto L7c
        L67:
            java.lang.Runnable r10 = r9.f21604j
            if (r10 != 0) goto L76
            androidx.appcompat.graphics.drawable.b$a r10 = new androidx.appcompat.graphics.drawable.b$a
            r1 = r9
            androidx.appcompat.graphics.drawable.e r1 = (androidx.appcompat.graphics.drawable.e) r1
            r10.<init>(r1)
            r9.f21604j = r10
            goto L79
        L76:
            r9.unscheduleSelf(r10)
        L79:
            r9.a(r0)
        L7c:
            r9.invalidateSelf()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.b.d(int):boolean");
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(@N Canvas canvas) {
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.f21599e;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    public void e(d dVar) {
        this.f21596b = dVar;
        int i12 = this.f21602h;
        if (i12 >= 0) {
            Drawable drawableC = dVar.c(i12);
            this.f21598d = drawableC;
            if (drawableC != null) {
                c(drawableC);
            }
        }
        this.f21599e = null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.f21600f;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.f21596b.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        boolean z12;
        d dVar = this.f21596b;
        if (!dVar.f21639v) {
            dVar.b();
            dVar.f21639v = true;
            int i12 = dVar.f21625h;
            Drawable[] drawableArr = dVar.f21624g;
            int i13 = 0;
            while (true) {
                if (i13 >= i12) {
                    dVar.f21640w = true;
                    z12 = true;
                    break;
                }
                if (drawableArr[i13].getConstantState() == null) {
                    dVar.f21640w = false;
                    z12 = false;
                    break;
                }
                i13++;
            }
        } else {
            z12 = dVar.f21640w;
        }
        if (!z12) {
            return null;
        }
        this.f21596b.f21621d = getChangingConfigurations();
        return this.f21596b;
    }

    @Override // android.graphics.drawable.Drawable
    @N
    public final Drawable getCurrent() {
        return this.f21598d;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getHotspotBounds(@N Rect rect) {
        Rect rect2 = this.f21597c;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        d dVar = this.f21596b;
        if (dVar.f21629l) {
            if (!dVar.f21630m) {
                dVar.a();
            }
            return dVar.f21632o;
        }
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        d dVar = this.f21596b;
        if (dVar.f21629l) {
            if (!dVar.f21630m) {
                dVar.a();
            }
            return dVar.f21631n;
        }
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumHeight() {
        d dVar = this.f21596b;
        if (dVar.f21629l) {
            if (!dVar.f21630m) {
                dVar.a();
            }
            return dVar.f21634q;
        }
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumWidth() {
        d dVar = this.f21596b;
        if (dVar.f21629l) {
            if (!dVar.f21630m) {
                dVar.a();
            }
            return dVar.f21633p;
        }
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Drawable drawable = this.f21598d;
        if (drawable == null || !drawable.isVisible()) {
            return -2;
        }
        d dVar = this.f21596b;
        if (dVar.f21635r) {
            return dVar.f21636s;
        }
        dVar.b();
        int i12 = dVar.f21625h;
        Drawable[] drawableArr = dVar.f21624g;
        int opacity = i12 > 0 ? drawableArr[0].getOpacity() : -2;
        for (int i13 = 1; i13 < i12; i13++) {
            opacity = Drawable.resolveOpacity(opacity, drawableArr[i13].getOpacity());
        }
        dVar.f21636s = opacity;
        dVar.f21635r = true;
        return opacity;
    }

    @Override // android.graphics.drawable.Drawable
    @X
    public final void getOutline(@N Outline outline) {
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            drawable.getOutline(outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(@N Rect rect) {
        d dVar = this.f21596b;
        boolean padding = false;
        Rect rect2 = null;
        if (!dVar.f21626i) {
            Rect rect3 = dVar.f21628k;
            if (rect3 != null || dVar.f21627j) {
                rect2 = rect3;
            } else {
                dVar.b();
                Rect rect4 = new Rect();
                int i12 = dVar.f21625h;
                Drawable[] drawableArr = dVar.f21624g;
                for (int i13 = 0; i13 < i12; i13++) {
                    if (drawableArr[i13].getPadding(rect4)) {
                        if (rect2 == null) {
                            rect2 = new Rect(0, 0, 0, 0);
                        }
                        int i14 = rect4.left;
                        if (i14 > rect2.left) {
                            rect2.left = i14;
                        }
                        int i15 = rect4.top;
                        if (i15 > rect2.top) {
                            rect2.top = i15;
                        }
                        int i16 = rect4.right;
                        if (i16 > rect2.right) {
                            rect2.right = i16;
                        }
                        int i17 = rect4.bottom;
                        if (i17 > rect2.bottom) {
                            rect2.bottom = i17;
                        }
                    }
                }
                dVar.f21627j = true;
                dVar.f21628k = rect2;
            }
        }
        if (rect2 != null) {
            rect.set(rect2);
            if ((rect2.left | rect2.top | rect2.bottom | rect2.right) != 0) {
                padding = true;
            }
        } else {
            Drawable drawable = this.f21598d;
            padding = drawable != null ? drawable.getPadding(rect) : super.getPadding(rect);
        }
        if (this.f21596b.f21611B && getLayoutDirection() == 1) {
            int i18 = rect.left;
            rect.left = rect.right;
            rect.right = i18;
        }
        return padding;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(@N Drawable drawable) {
        d dVar = this.f21596b;
        if (dVar != null) {
            dVar.f21635r = false;
            dVar.f21637t = false;
        }
        if (drawable != this.f21598d || getCallback() == null) {
            return;
        }
        getCallback().invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        return this.f21596b.f21611B;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        d dVar = this.f21596b;
        if (dVar.f21637t) {
            return dVar.f21638u;
        }
        dVar.b();
        int i12 = dVar.f21625h;
        Drawable[] drawableArr = dVar.f21624g;
        boolean z12 = false;
        int i13 = 0;
        while (true) {
            if (i13 >= i12) {
                break;
            }
            if (drawableArr[i13].isStateful()) {
                z12 = true;
                break;
            }
            i13++;
        }
        dVar.f21638u = z12;
        dVar.f21637t = true;
        return z12;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        boolean z12;
        Drawable drawable = this.f21599e;
        boolean z13 = true;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.f21599e = null;
            z12 = true;
        } else {
            z12 = false;
        }
        Drawable drawable2 = this.f21598d;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.f21601g) {
                this.f21598d.setAlpha(this.f21600f);
            }
        }
        if (this.f21606l != 0) {
            this.f21606l = 0L;
            z12 = true;
        }
        if (this.f21605k != 0) {
            this.f21605k = 0L;
        } else {
            z13 = z12;
        }
        if (z13) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.f21603i && super.mutate() == this) {
            d dVarB = b();
            dVarB.d();
            e(dVarB);
            this.f21603i = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Drawable drawable = this.f21599e;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
        Drawable drawable2 = this.f21598d;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i12) {
        d dVar = this.f21596b;
        int i13 = this.f21602h;
        int i14 = dVar.f21625h;
        Drawable[] drawableArr = dVar.f21624g;
        boolean z12 = false;
        for (int i15 = 0; i15 < i14; i15++) {
            Drawable drawable = drawableArr[i15];
            if (drawable != null) {
                boolean layoutDirection = drawable.setLayoutDirection(i12);
                if (i15 == i13) {
                    z12 = layoutDirection;
                }
            }
        }
        dVar.f21642y = i12;
        return z12;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i12) {
        Drawable drawable = this.f21599e;
        if (drawable != null) {
            return drawable.setLevel(i12);
        }
        Drawable drawable2 = this.f21598d;
        if (drawable2 != null) {
            return drawable2.setLevel(i12);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(@N int[] iArr) {
        Drawable drawable = this.f21599e;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        Drawable drawable2 = this.f21598d;
        if (drawable2 != null) {
            return drawable2.setState(iArr);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(@N Drawable drawable, @N Runnable runnable, long j12) {
        if (drawable != this.f21598d || getCallback() == null) {
            return;
        }
        getCallback().scheduleDrawable(this, runnable, j12);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i12) {
        if (this.f21601g && this.f21600f == i12) {
            return;
        }
        this.f21601g = true;
        this.f21600f = i12;
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            if (this.f21605k == 0) {
                drawable.setAlpha(i12);
            } else {
                a(false);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z12) {
        d dVar = this.f21596b;
        if (dVar.f21611B != z12) {
            dVar.f21611B = z12;
            Drawable drawable = this.f21598d;
            if (drawable != null) {
                drawable.setAutoMirrored(z12);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        d dVar = this.f21596b;
        dVar.f21613D = true;
        if (dVar.f21612C != colorFilter) {
            dVar.f21612C = colorFilter;
            Drawable drawable = this.f21598d;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setDither(boolean z12) {
        d dVar = this.f21596b;
        if (dVar.f21641x != z12) {
            dVar.f21641x = z12;
            Drawable drawable = this.f21598d;
            if (drawable != null) {
                drawable.setDither(z12);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setHotspot(float f12, float f13) {
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            drawable.setHotspot(f12, f13);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setHotspotBounds(int i12, int i13, int i14, int i15) {
        Rect rect = this.f21597c;
        if (rect == null) {
            this.f21597c = new Rect(i12, i13, i14, i15);
        } else {
            rect.set(i12, i13, i14, i15);
        }
        Drawable drawable = this.f21598d;
        if (drawable != null) {
            drawable.setHotspotBounds(i12, i13, i14, i15);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(@InterfaceC42156l int i12) {
        setTintList(ColorStateList.valueOf(i12));
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        d dVar = this.f21596b;
        dVar.f21616G = true;
        if (dVar.f21614E != colorStateList) {
            dVar.f21614E = colorStateList;
            androidx.core.graphics.drawable.c.b(this.f21598d, colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(@N PorterDuff.Mode mode) {
        d dVar = this.f21596b;
        dVar.f21617H = true;
        if (dVar.f21615F != mode) {
            dVar.f21615F = mode;
            androidx.core.graphics.drawable.c.c(this.f21598d, mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z12, boolean z13) {
        boolean visible = super.setVisible(z12, z13);
        Drawable drawable = this.f21599e;
        if (drawable != null) {
            drawable.setVisible(z12, z13);
        }
        Drawable drawable2 = this.f21598d;
        if (drawable2 != null) {
            drawable2.setVisible(z12, z13);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(@N Drawable drawable, @N Runnable runnable) {
        if (drawable != this.f21598d || getCallback() == null) {
            return;
        }
        getCallback().unscheduleDrawable(this, runnable);
    }

    /* compiled from: DrawableContainerCompat.java */
    public static class c implements Drawable.Callback {

        /* renamed from: b, reason: collision with root package name */
        public Drawable.Callback f21609b;

        @Override // android.graphics.drawable.Drawable.Callback
        public final void scheduleDrawable(@N Drawable drawable, @N Runnable runnable, long j12) {
            Drawable.Callback callback = this.f21609b;
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j12);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public final void unscheduleDrawable(@N Drawable drawable, @N Runnable runnable) {
            Drawable.Callback callback = this.f21609b;
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public final void invalidateDrawable(@N Drawable drawable) {
        }
    }
}
