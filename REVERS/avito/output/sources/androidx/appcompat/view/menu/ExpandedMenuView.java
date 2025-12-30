package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.widget.D0;

@RestrictTo
/* loaded from: classes.dex */
public final class ExpandedMenuView extends ListView implements h.b, p, AdapterView.OnItemClickListener {

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f21735c = {R.attr.background, R.attr.divider};

    /* renamed from: b, reason: collision with root package name */
    public h f21736b;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        D0 d0E = D0.e(context, attributeSet, f21735c, R.attr.listViewStyle);
        TypedArray typedArray = d0E.f22121b;
        if (typedArray.hasValue(0)) {
            setBackgroundDrawable(d0E.b(0));
        }
        if (typedArray.hasValue(1)) {
            setDivider(d0E.b(1));
        }
        d0E.f();
    }

    @Override // androidx.appcompat.view.menu.p
    public final void b(h hVar) {
        this.f21736b = hVar;
    }

    @Override // androidx.appcompat.view.menu.h.b
    public final boolean e(k kVar) {
        return this.f21736b.q(kVar, null, 0);
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i12, long j12) {
        e((k) getAdapter().getItem(i12));
    }
}
