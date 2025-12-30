package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.avito.android.R;
import j.InterfaceC42164u;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DropDownListView.java */
/* loaded from: classes.dex */
public class W extends ListView {

    /* renamed from: b, reason: collision with root package name */
    public final Rect f22438b;

    /* renamed from: c, reason: collision with root package name */
    public int f22439c;

    /* renamed from: d, reason: collision with root package name */
    public int f22440d;

    /* renamed from: e, reason: collision with root package name */
    public int f22441e;

    /* renamed from: f, reason: collision with root package name */
    public int f22442f;

    /* renamed from: g, reason: collision with root package name */
    public int f22443g;

    /* renamed from: h, reason: collision with root package name */
    public d f22444h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f22445i;

    /* renamed from: j, reason: collision with root package name */
    public final boolean f22446j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f22447k;

    /* renamed from: l, reason: collision with root package name */
    public androidx.core.widget.i f22448l;

    /* renamed from: m, reason: collision with root package name */
    public f f22449m;

    /* compiled from: DropDownListView.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static void a(View view, float f12, float f13) {
            view.drawableHotspotChanged(f12, f13);
        }
    }

    /* compiled from: DropDownListView.java */
    @j.X
    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public static final Method f22450a;

        /* renamed from: b, reason: collision with root package name */
        public static final Method f22451b;

        /* renamed from: c, reason: collision with root package name */
        public static final Method f22452c;

        /* renamed from: d, reason: collision with root package name */
        public static final boolean f22453d;

        static {
            try {
                Class cls = Integer.TYPE;
                Class cls2 = Boolean.TYPE;
                Class cls3 = Float.TYPE;
                Method declaredMethod = AbsListView.class.getDeclaredMethod("positionSelector", cls, View.class, cls2, cls3, cls3);
                f22450a = declaredMethod;
                declaredMethod.setAccessible(true);
                Method declaredMethod2 = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", cls);
                f22451b = declaredMethod2;
                declaredMethod2.setAccessible(true);
                Method declaredMethod3 = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", cls);
                f22452c = declaredMethod3;
                declaredMethod3.setAccessible(true);
                f22453d = true;
            } catch (NoSuchMethodException e12) {
                e12.printStackTrace();
            }
        }
    }

    /* compiled from: DropDownListView.java */
    @j.X
    public static class c {
        @InterfaceC42164u
        public static boolean a(AbsListView absListView) {
            return absListView.isSelectedChildViewEnabled();
        }

        @InterfaceC42164u
        public static void b(AbsListView absListView, boolean z12) {
            absListView.setSelectedChildViewEnabled(z12);
        }
    }

    /* compiled from: DropDownListView.java */
    public static class d extends androidx.appcompat.graphics.drawable.c {

        /* renamed from: c, reason: collision with root package name */
        public boolean f22454c;

        @Override // androidx.appcompat.graphics.drawable.c, android.graphics.drawable.Drawable
        public final void draw(@j.N Canvas canvas) {
            if (this.f22454c) {
                super.draw(canvas);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.c, android.graphics.drawable.Drawable
        public final void setHotspot(float f12, float f13) {
            if (this.f22454c) {
                super.setHotspot(f12, f13);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.c, android.graphics.drawable.Drawable
        public final void setHotspotBounds(int i12, int i13, int i14, int i15) {
            if (this.f22454c) {
                super.setHotspotBounds(i12, i13, i14, i15);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.c, android.graphics.drawable.Drawable
        public final boolean setState(int[] iArr) {
            if (this.f22454c) {
                return this.f21644b.setState(iArr);
            }
            return false;
        }

        @Override // androidx.appcompat.graphics.drawable.c, android.graphics.drawable.Drawable
        public final boolean setVisible(boolean z12, boolean z13) {
            if (this.f22454c) {
                return super.setVisible(z12, z13);
            }
            return false;
        }
    }

    /* compiled from: DropDownListView.java */
    public static class e {

        /* renamed from: a, reason: collision with root package name */
        public static final Field f22455a;

        static {
            Field declaredField = null;
            try {
                declaredField = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e12) {
                e12.printStackTrace();
            }
            f22455a = declaredField;
        }
    }

    /* compiled from: DropDownListView.java */
    public class f implements Runnable {
        public f() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            W w12 = W.this;
            w12.f22449m = null;
            w12.drawableStateChanged();
        }
    }

    public W(@j.N Context context, boolean z12) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.f22438b = new Rect();
        this.f22439c = 0;
        this.f22440d = 0;
        this.f22441e = 0;
        this.f22442f = 0;
        this.f22446j = z12;
        setCacheColorHint(0);
    }

    private void setSelectorEnabled(boolean z12) {
        d dVar = this.f22444h;
        if (dVar != null) {
            dVar.f22454c = z12;
        }
    }

    public int a(int i12, int i13) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int measuredHeight = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int i14 = 0;
        View view = null;
        for (int i15 = 0; i15 < count; i15++) {
            int itemViewType = adapter.getItemViewType(i15);
            if (itemViewType != i14) {
                view = null;
                i14 = itemViewType;
            }
            view = adapter.getView(i15, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            int i16 = layoutParams.height;
            view.measure(i12, i16 > 0 ? View.MeasureSpec.makeMeasureSpec(i16, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i15 > 0) {
                measuredHeight += dividerHeight;
            }
            measuredHeight += view.getMeasuredHeight();
            if (measuredHeight >= i13) {
                return i13;
            }
        }
        return measuredHeight;
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean b(android.view.MotionEvent r17, int r18) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 390
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.W.b(android.view.MotionEvent, int):boolean");
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Drawable selector;
        Rect rect = this.f22438b;
        if (!rect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(rect);
            selector.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.f22449m != null) {
            return;
        }
        super.drawableStateChanged();
        setSelectorEnabled(true);
        Drawable selector = getSelector();
        if (selector != null && this.f22447k && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.f22446j || super.hasFocus();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.f22446j || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.f22446j || super.isFocused();
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.f22446j && this.f22445i) || super.isInTouchMode();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.f22449m = null;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(@j.N MotionEvent motionEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i12 = Build.VERSION.SDK_INT;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.f22449m == null) {
            f fVar = new f();
            this.f22449m = fVar;
            post(fVar);
        }
        boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (iPointToPosition != -1 && iPointToPosition != getSelectedItemPosition()) {
                View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    requestFocus();
                    if (i12 < 30 || !b.f22453d) {
                        setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop());
                    } else {
                        try {
                            b.f22450a.invoke(this, Integer.valueOf(iPointToPosition), childAt, Boolean.FALSE, -1, -1);
                            b.f22451b.invoke(this, Integer.valueOf(iPointToPosition));
                            b.f22452c.invoke(this, Integer.valueOf(iPointToPosition));
                        } catch (IllegalAccessException e12) {
                            e12.printStackTrace();
                        } catch (InvocationTargetException e13) {
                            e13.printStackTrace();
                        }
                    }
                }
                Drawable selector = getSelector();
                if (selector != null && this.f22447k && isPressed()) {
                    selector.setState(getDrawableState());
                }
            }
        } else {
            setSelection(-1);
        }
        return zOnHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f22443g = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        f fVar = this.f22449m;
        if (fVar != null) {
            W w12 = W.this;
            w12.f22449m = null;
            w12.removeCallbacks(fVar);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean z12) {
        this.f22445i = z12;
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        d dVar = null;
        if (drawable != null) {
            d dVar2 = new d();
            Drawable drawable2 = dVar2.f21644b;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            dVar2.f21644b = drawable;
            drawable.setCallback(dVar2);
            dVar2.f22454c = true;
            dVar = dVar2;
        }
        this.f22444h = dVar;
        super.setSelector(dVar);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.f22439c = rect.left;
        this.f22440d = rect.top;
        this.f22441e = rect.right;
        this.f22442f = rect.bottom;
    }
}
