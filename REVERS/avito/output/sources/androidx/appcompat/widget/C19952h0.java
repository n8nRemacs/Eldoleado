package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.ListMenuItemView;
import j.InterfaceC42164u;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: MenuPopupWindow.java */
@RestrictTo
/* renamed from: androidx.appcompat.widget.h0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19952h0 extends C19948f0 implements InterfaceC19950g0 {

    /* renamed from: E, reason: collision with root package name */
    public static final Method f22541E;

    /* renamed from: D, reason: collision with root package name */
    public InterfaceC19950g0 f22542D;

    /* compiled from: MenuPopupWindow.java */
    @j.X
    /* renamed from: androidx.appcompat.widget.h0$a */
    public static class a {
        @InterfaceC42164u
        public static void a(PopupWindow popupWindow, Transition transition) {
            popupWindow.setEnterTransition(transition);
        }

        @InterfaceC42164u
        public static void b(PopupWindow popupWindow, Transition transition) {
            popupWindow.setExitTransition(transition);
        }
    }

    /* compiled from: MenuPopupWindow.java */
    @j.X
    /* renamed from: androidx.appcompat.widget.h0$b */
    public static class b {
        @InterfaceC42164u
        public static void a(PopupWindow popupWindow, boolean z12) {
            popupWindow.setTouchModal(z12);
        }
    }

    /* compiled from: MenuPopupWindow.java */
    @RestrictTo
    /* renamed from: androidx.appcompat.widget.h0$c */
    public static class c extends W {

        /* renamed from: n, reason: collision with root package name */
        public final int f22543n;

        /* renamed from: o, reason: collision with root package name */
        public final int f22544o;

        /* renamed from: p, reason: collision with root package name */
        public InterfaceC19950g0 f22545p;

        /* renamed from: q, reason: collision with root package name */
        public androidx.appcompat.view.menu.k f22546q;

        public c(Context context, boolean z12) {
            super(context, z12);
            if (1 == context.getResources().getConfiguration().getLayoutDirection()) {
                this.f22543n = 21;
                this.f22544o = 22;
            } else {
                this.f22543n = 22;
                this.f22544o = 21;
            }
        }

        @Override // androidx.appcompat.widget.W, android.view.View
        public final boolean onHoverEvent(MotionEvent motionEvent) {
            androidx.appcompat.view.menu.g gVar;
            int headersCount;
            int iPointToPosition;
            int i12;
            if (this.f22545p != null) {
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    gVar = (androidx.appcompat.view.menu.g) headerViewListAdapter.getWrappedAdapter();
                } else {
                    gVar = (androidx.appcompat.view.menu.g) adapter;
                    headersCount = 0;
                }
                androidx.appcompat.view.menu.k item = (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i12 = iPointToPosition - headersCount) < 0 || i12 >= gVar.getCount()) ? null : gVar.getItem(i12);
                androidx.appcompat.view.menu.k kVar = this.f22546q;
                if (kVar != item) {
                    androidx.appcompat.view.menu.h hVar = gVar.f21824b;
                    if (kVar != null) {
                        this.f22545p.c(hVar, kVar);
                    }
                    this.f22546q = item;
                    if (item != null) {
                        this.f22545p.b(hVar, item);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        public final boolean onKeyDown(int i12, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i12 == this.f22543n) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView == null || i12 != this.f22544o) {
                return super.onKeyDown(i12, keyEvent);
            }
            setSelection(-1);
            ListAdapter adapter = getAdapter();
            (adapter instanceof HeaderViewListAdapter ? (androidx.appcompat.view.menu.g) ((HeaderViewListAdapter) adapter).getWrappedAdapter() : (androidx.appcompat.view.menu.g) adapter).f21824b.c(false);
            return true;
        }

        public void setHoverListener(InterfaceC19950g0 interfaceC19950g0) {
            this.f22545p = interfaceC19950g0;
        }

        @Override // androidx.appcompat.widget.W, android.widget.AbsListView
        public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
            super.setSelector(drawable);
        }
    }

    static {
        try {
            if (Build.VERSION.SDK_INT <= 28) {
                f22541E = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
            }
        } catch (NoSuchMethodException unused) {
        }
    }

    @Override // androidx.appcompat.widget.InterfaceC19950g0
    public final void b(@j.N androidx.appcompat.view.menu.h hVar, @j.N androidx.appcompat.view.menu.k kVar) {
        InterfaceC19950g0 interfaceC19950g0 = this.f22542D;
        if (interfaceC19950g0 != null) {
            interfaceC19950g0.b(hVar, kVar);
        }
    }

    @Override // androidx.appcompat.widget.InterfaceC19950g0
    public final void c(@j.N androidx.appcompat.view.menu.h hVar, @j.N androidx.appcompat.view.menu.k kVar) {
        InterfaceC19950g0 interfaceC19950g0 = this.f22542D;
        if (interfaceC19950g0 != null) {
            interfaceC19950g0.c(hVar, kVar);
        }
    }

    @Override // androidx.appcompat.widget.C19948f0
    @j.N
    public final W p(Context context, boolean z12) {
        c cVar = new c(context, z12);
        cVar.setHoverListener(this);
        return cVar;
    }

    public final void s() {
        a.a(this.f22505A, null);
    }

    public final void t() {
        a.b(this.f22505A, null);
    }

    public final void u() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i12 = Build.VERSION.SDK_INT;
        PopupWindow popupWindow = this.f22505A;
        if (i12 > 28) {
            b.a(popupWindow, false);
            return;
        }
        Method method = f22541E;
        if (method != null) {
            try {
                method.invoke(popupWindow, Boolean.FALSE);
            } catch (Exception unused) {
            }
        }
    }
}
