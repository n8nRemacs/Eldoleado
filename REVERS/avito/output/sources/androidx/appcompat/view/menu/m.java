package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import j.N;
import j.P;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MenuPopup.java */
/* loaded from: classes.dex */
public abstract class m implements r, o, AdapterView.OnItemClickListener {

    /* renamed from: b, reason: collision with root package name */
    public Rect f21898b;

    public static int l(ListAdapter listAdapter, Context context, int i12) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i13 = 0;
        int i14 = 0;
        FrameLayout frameLayout = null;
        View view = null;
        for (int i15 = 0; i15 < count; i15++) {
            int itemViewType = listAdapter.getItemViewType(i15);
            if (itemViewType != i14) {
                view = null;
                i14 = itemViewType;
            }
            if (frameLayout == null) {
                frameLayout = new FrameLayout(context);
            }
            view = listAdapter.getView(i15, view, frameLayout);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i12) {
                return i12;
            }
            if (measuredWidth > i13) {
                i13 = measuredWidth;
            }
        }
        return i13;
    }

    public static boolean t(h hVar) {
        int size = hVar.f21836f.size();
        for (int i12 = 0; i12 < size; i12++) {
            MenuItem item = hVar.getItem(i12);
            if (item.isVisible() && item.getIcon() != null) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean e(k kVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean g(k kVar) {
        return false;
    }

    public abstract void k(h hVar);

    public abstract void m(View view);

    public abstract void n(boolean z12);

    public abstract void o(int i12);

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        (listAdapter instanceof HeaderViewListAdapter ? (g) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (g) listAdapter).f21824b.q((MenuItem) listAdapter.getItem(i12), this, !(this instanceof d) ? 0 : 4);
    }

    public abstract void p(int i12);

    public abstract void q(PopupWindow.OnDismissListener onDismissListener);

    public abstract void r(boolean z12);

    public abstract void s(int i12);

    @Override // androidx.appcompat.view.menu.o
    public final void i(@N Context context, @P h hVar) {
    }
}
