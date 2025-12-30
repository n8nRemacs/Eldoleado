package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.avito.android.R;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import j.InterfaceC42150f;
import j.InterfaceC42164u;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import l.C43521a;

/* compiled from: ListPopupWindow.java */
/* renamed from: androidx.appcompat.widget.f0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19948f0 implements androidx.appcompat.view.menu.r {

    /* renamed from: B, reason: collision with root package name */
    public static final Method f22503B;

    /* renamed from: C, reason: collision with root package name */
    public static final Method f22504C;

    /* renamed from: A, reason: collision with root package name */
    public final PopupWindow f22505A;

    /* renamed from: b, reason: collision with root package name */
    public final Context f22506b;

    /* renamed from: c, reason: collision with root package name */
    public ListAdapter f22507c;

    /* renamed from: d, reason: collision with root package name */
    public W f22508d;

    /* renamed from: e, reason: collision with root package name */
    public final int f22509e;

    /* renamed from: f, reason: collision with root package name */
    public int f22510f;

    /* renamed from: g, reason: collision with root package name */
    public int f22511g;

    /* renamed from: h, reason: collision with root package name */
    public int f22512h;

    /* renamed from: i, reason: collision with root package name */
    public final int f22513i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f22514j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f22515k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f22516l;

    /* renamed from: m, reason: collision with root package name */
    public int f22517m;

    /* renamed from: n, reason: collision with root package name */
    public final int f22518n;

    /* renamed from: o, reason: collision with root package name */
    public DataSetObserver f22519o;

    /* renamed from: p, reason: collision with root package name */
    public View f22520p;

    /* renamed from: q, reason: collision with root package name */
    public AdapterView.OnItemClickListener f22521q;

    /* renamed from: r, reason: collision with root package name */
    public AdapterView.OnItemSelectedListener f22522r;

    /* renamed from: s, reason: collision with root package name */
    public final g f22523s;

    /* renamed from: t, reason: collision with root package name */
    public final f f22524t;

    /* renamed from: u, reason: collision with root package name */
    public final e f22525u;

    /* renamed from: v, reason: collision with root package name */
    public final c f22526v;

    /* renamed from: w, reason: collision with root package name */
    public final Handler f22527w;

    /* renamed from: x, reason: collision with root package name */
    public final Rect f22528x;

    /* renamed from: y, reason: collision with root package name */
    public Rect f22529y;

    /* renamed from: z, reason: collision with root package name */
    public boolean f22530z;

    /* compiled from: ListPopupWindow.java */
    @j.X
    /* renamed from: androidx.appcompat.widget.f0$a */
    public static class a {
        @InterfaceC42164u
        public static int a(PopupWindow popupWindow, View view, int i12, boolean z12) {
            return popupWindow.getMaxAvailableHeight(view, i12, z12);
        }
    }

    /* compiled from: ListPopupWindow.java */
    @j.X
    /* renamed from: androidx.appcompat.widget.f0$b */
    public static class b {
        @InterfaceC42164u
        public static void a(PopupWindow popupWindow, Rect rect) {
            popupWindow.setEpicenterBounds(rect);
        }

        @InterfaceC42164u
        public static void b(PopupWindow popupWindow, boolean z12) {
            popupWindow.setIsClippedToScreen(z12);
        }
    }

    /* compiled from: ListPopupWindow.java */
    /* renamed from: androidx.appcompat.widget.f0$c */
    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            W w12 = C19948f0.this.f22508d;
            if (w12 != null) {
                w12.setListSelectionHidden(true);
                w12.requestLayout();
            }
        }
    }

    /* compiled from: ListPopupWindow.java */
    /* renamed from: androidx.appcompat.widget.f0$d */
    public class d extends DataSetObserver {
        public d() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            C19948f0 c19948f0 = C19948f0.this;
            if (c19948f0.f22505A.isShowing()) {
                c19948f0.show();
            }
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            C19948f0.this.dismiss();
        }
    }

    /* compiled from: ListPopupWindow.java */
    /* renamed from: androidx.appcompat.widget.f0$f */
    public class f implements View.OnTouchListener {
        public f() {
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x12 = (int) motionEvent.getX();
            int y12 = (int) motionEvent.getY();
            C19948f0 c19948f0 = C19948f0.this;
            if (action == 0 && (popupWindow = c19948f0.f22505A) != null && popupWindow.isShowing() && x12 >= 0 && x12 < c19948f0.f22505A.getWidth() && y12 >= 0 && y12 < c19948f0.f22505A.getHeight()) {
                c19948f0.f22527w.postDelayed(c19948f0.f22523s, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            c19948f0.f22527w.removeCallbacks(c19948f0.f22523s);
            return false;
        }
    }

    /* compiled from: ListPopupWindow.java */
    /* renamed from: androidx.appcompat.widget.f0$g */
    public class g implements Runnable {
        public g() {
        }

        @Override // java.lang.Runnable
        public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            C19948f0 c19948f0 = C19948f0.this;
            W w12 = c19948f0.f22508d;
            if (w12 == null || !w12.isAttachedToWindow() || c19948f0.f22508d.getCount() <= c19948f0.f22508d.getChildCount() || c19948f0.f22508d.getChildCount() > c19948f0.f22518n) {
                return;
            }
            c19948f0.f22505A.setInputMethodMode(2);
            c19948f0.show();
        }
    }

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                f22503B = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException unused) {
            }
            try {
                f22504C = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            } catch (NoSuchMethodException unused2) {
            }
        }
    }

    public C19948f0(@j.N Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    @Override // androidx.appcompat.view.menu.r
    public final boolean a() {
        return this.f22505A.isShowing();
    }

    @Override // androidx.appcompat.view.menu.r
    @j.P
    public final ListView d() {
        return this.f22508d;
    }

    @Override // androidx.appcompat.view.menu.r
    public final void dismiss() {
        PopupWindow popupWindow = this.f22505A;
        popupWindow.dismiss();
        popupWindow.setContentView(null);
        this.f22508d = null;
        this.f22527w.removeCallbacks(this.f22523s);
    }

    @j.P
    public final Drawable e() {
        return this.f22505A.getBackground();
    }

    public final void g(@j.P Drawable drawable) {
        this.f22505A.setBackgroundDrawable(drawable);
    }

    public final void h(int i12) {
        this.f22512h = i12;
        this.f22514j = true;
    }

    public final int j() {
        if (this.f22514j) {
            return this.f22512h;
        }
        return 0;
    }

    public final int k() {
        return this.f22511g;
    }

    public final void l(int i12) {
        this.f22511g = i12;
    }

    public void o(@j.P ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.f22519o;
        if (dataSetObserver == null) {
            this.f22519o = new d();
        } else {
            ListAdapter listAdapter2 = this.f22507c;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f22507c = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.f22519o);
        }
        W w12 = this.f22508d;
        if (w12 != null) {
            w12.setAdapter(this.f22507c);
        }
    }

    @j.N
    public W p(Context context, boolean z12) {
        return new W(context, z12);
    }

    public final void q(int i12) {
        Drawable background = this.f22505A.getBackground();
        if (background == null) {
            this.f22510f = i12;
            return;
        }
        Rect rect = this.f22528x;
        background.getPadding(rect);
        this.f22510f = rect.left + rect.right + i12;
    }

    public final void r() {
        this.f22530z = true;
        this.f22505A.setFocusable(true);
    }

    @Override // androidx.appcompat.view.menu.r
    public void show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i12;
        int paddingBottom;
        W w12;
        W w13 = this.f22508d;
        PopupWindow popupWindow = this.f22505A;
        Context context = this.f22506b;
        if (w13 == null) {
            W wP2 = p(context, !this.f22530z);
            this.f22508d = wP2;
            wP2.setAdapter(this.f22507c);
            this.f22508d.setOnItemClickListener(this.f22521q);
            this.f22508d.setFocusable(true);
            this.f22508d.setFocusableInTouchMode(true);
            this.f22508d.setOnItemSelectedListener(new C19946e0(this));
            this.f22508d.setOnScrollListener(this.f22525u);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.f22522r;
            if (onItemSelectedListener != null) {
                this.f22508d.setOnItemSelectedListener(onItemSelectedListener);
            }
            popupWindow.setContentView(this.f22508d);
        }
        Drawable background = popupWindow.getBackground();
        Rect rect = this.f22528x;
        if (background != null) {
            background.getPadding(rect);
            int i13 = rect.top;
            i12 = rect.bottom + i13;
            if (!this.f22514j) {
                this.f22512h = -i13;
            }
        } else {
            rect.setEmpty();
            i12 = 0;
        }
        int iA2 = a.a(popupWindow, this.f22520p, this.f22512h, popupWindow.getInputMethodMode() == 2);
        int i14 = this.f22509e;
        if (i14 == -1) {
            paddingBottom = iA2 + i12;
        } else {
            int i15 = this.f22510f;
            int iA3 = this.f22508d.a(i15 != -2 ? i15 != -1 ? View.MeasureSpec.makeMeasureSpec(i15, 1073741824) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), 1073741824) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), BeduinInputModel.MIN_TEXT_VERSION), iA2);
            paddingBottom = iA3 + (iA3 > 0 ? this.f22508d.getPaddingBottom() + this.f22508d.getPaddingTop() + i12 : 0);
        }
        boolean z12 = this.f22505A.getInputMethodMode() == 2;
        popupWindow.setWindowLayoutType(this.f22513i);
        if (popupWindow.isShowing()) {
            if (this.f22520p.isAttachedToWindow()) {
                int width = this.f22510f;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.f22520p.getWidth();
                }
                if (i14 == -1) {
                    i14 = z12 ? paddingBottom : -1;
                    if (z12) {
                        popupWindow.setWidth(this.f22510f == -1 ? -1 : 0);
                        popupWindow.setHeight(0);
                    } else {
                        popupWindow.setWidth(this.f22510f == -1 ? -1 : 0);
                        popupWindow.setHeight(-1);
                    }
                } else if (i14 == -2) {
                    i14 = paddingBottom;
                }
                popupWindow.setOutsideTouchable(true);
                View view = this.f22520p;
                int i16 = this.f22511g;
                int i17 = this.f22512h;
                if (width < 0) {
                    width = -1;
                }
                popupWindow.update(view, i16, i17, width, i14 < 0 ? -1 : i14);
                return;
            }
            return;
        }
        int width2 = this.f22510f;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.f22520p.getWidth();
        }
        if (i14 == -1) {
            i14 = -1;
        } else if (i14 == -2) {
            i14 = paddingBottom;
        }
        popupWindow.setWidth(width2);
        popupWindow.setHeight(i14);
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = f22503B;
            if (method != null) {
                try {
                    method.invoke(popupWindow, Boolean.TRUE);
                } catch (Exception unused) {
                }
            }
        } else {
            b.b(popupWindow, true);
        }
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(this.f22524t);
        if (this.f22516l) {
            popupWindow.setOverlapAnchor(this.f22515k);
        }
        if (Build.VERSION.SDK_INT <= 28) {
            Method method2 = f22504C;
            if (method2 != null) {
                try {
                    method2.invoke(popupWindow, this.f22529y);
                } catch (Exception unused2) {
                }
            }
        } else {
            b.a(popupWindow, this.f22529y);
        }
        popupWindow.showAsDropDown(this.f22520p, this.f22511g, this.f22512h, this.f22517m);
        this.f22508d.setSelection(-1);
        if ((!this.f22530z || this.f22508d.isInTouchMode()) && (w12 = this.f22508d) != null) {
            w12.setListSelectionHidden(true);
            w12.requestLayout();
        }
        if (this.f22530z) {
            return;
        }
        this.f22527w.post(this.f22526v);
    }

    public C19948f0(@j.N Context context, @j.P AttributeSet attributeSet, @InterfaceC42150f int i12) {
        this.f22509e = -2;
        this.f22510f = -2;
        this.f22513i = 1002;
        this.f22517m = 0;
        this.f22518n = Integer.MAX_VALUE;
        this.f22523s = new g();
        this.f22524t = new f();
        this.f22525u = new e();
        this.f22526v = new c();
        this.f22528x = new Rect();
        this.f22506b = context;
        this.f22527w = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413366o, i12, 0);
        this.f22511g = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0);
        this.f22512h = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.f22514j = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        B b12 = new B(context, attributeSet, i12, 0);
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, C43521a.m.f413370s, i12, 0);
        D0 d02 = new D0(typedArrayObtainStyledAttributes2, context);
        if (typedArrayObtainStyledAttributes2.hasValue(2)) {
            b12.setOverlapAnchor(typedArrayObtainStyledAttributes2.getBoolean(2, false));
        }
        b12.setBackgroundDrawable(d02.b(0));
        d02.f();
        this.f22505A = b12;
        b12.setInputMethodMode(1);
    }

    /* compiled from: ListPopupWindow.java */
    /* renamed from: androidx.appcompat.widget.f0$e */
    public class e implements AbsListView.OnScrollListener {
        public e() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScrollStateChanged(AbsListView absListView, int i12) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (i12 == 1) {
                C19948f0 c19948f0 = C19948f0.this;
                if (c19948f0.f22505A.getInputMethodMode() == 2 || c19948f0.f22505A.getContentView() == null) {
                    return;
                }
                Handler handler = c19948f0.f22527w;
                g gVar = c19948f0.f22523s;
                handler.removeCallbacks(gVar);
                gVar.run();
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScroll(AbsListView absListView, int i12, int i13, int i14) {
        }
    }
}
