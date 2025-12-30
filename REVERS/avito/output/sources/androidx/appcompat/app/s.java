package androidx.appcompat.app;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.O0;
import androidx.core.view.C22823h0;
import androidx.core.view.J0;
import androidx.core.view.M;
import com.avito.android.R;
import java.lang.reflect.InvocationTargetException;

/* compiled from: AppCompatDelegateImpl.java */
/* loaded from: classes.dex */
class s implements M {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppCompatDelegateImpl f21574b;

    public s(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f21574b = appCompatDelegateImpl;
    }

    @Override // androidx.core.view.M
    public final J0 h(View view, J0 j02) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        boolean z12;
        View view2;
        J0 j0Q;
        boolean z13;
        int iK2 = j02.k();
        AppCompatDelegateImpl appCompatDelegateImpl = this.f21574b;
        appCompatDelegateImpl.getClass();
        int iK3 = j02.k();
        ActionBarContextView actionBarContextView = appCompatDelegateImpl.f21430w;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z12 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) appCompatDelegateImpl.f21430w.getLayoutParams();
            if (appCompatDelegateImpl.f21430w.isShown()) {
                if (appCompatDelegateImpl.f21413e0 == null) {
                    appCompatDelegateImpl.f21413e0 = new Rect();
                    appCompatDelegateImpl.f21414f0 = new Rect();
                }
                Rect rect = appCompatDelegateImpl.f21413e0;
                Rect rect2 = appCompatDelegateImpl.f21414f0;
                rect.set(j02.i(), j02.k(), j02.j(), j02.h());
                O0.a(appCompatDelegateImpl.f21385C, rect, rect2);
                int i12 = rect.top;
                int i13 = rect.left;
                int i14 = rect.right;
                J0 j0O = C22823h0.o(appCompatDelegateImpl.f21385C);
                int i15 = j0O == null ? 0 : j0O.i();
                int iJ2 = j0O == null ? 0 : j0O.j();
                if (marginLayoutParams.topMargin == i12 && marginLayoutParams.leftMargin == i13 && marginLayoutParams.rightMargin == i14) {
                    z13 = false;
                } else {
                    marginLayoutParams.topMargin = i12;
                    marginLayoutParams.leftMargin = i13;
                    marginLayoutParams.rightMargin = i14;
                    z13 = true;
                }
                Context context = appCompatDelegateImpl.f21419l;
                if (i12 <= 0 || appCompatDelegateImpl.f21387E != null) {
                    View view3 = appCompatDelegateImpl.f21387E;
                    if (view3 != null) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
                        int i16 = marginLayoutParams2.height;
                        int i17 = marginLayoutParams.topMargin;
                        if (i16 != i17 || marginLayoutParams2.leftMargin != i15 || marginLayoutParams2.rightMargin != iJ2) {
                            marginLayoutParams2.height = i17;
                            marginLayoutParams2.leftMargin = i15;
                            marginLayoutParams2.rightMargin = iJ2;
                            appCompatDelegateImpl.f21387E.setLayoutParams(marginLayoutParams2);
                        }
                    }
                } else {
                    View view4 = new View(context);
                    appCompatDelegateImpl.f21387E = view4;
                    view4.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = i15;
                    layoutParams.rightMargin = iJ2;
                    appCompatDelegateImpl.f21385C.addView(appCompatDelegateImpl.f21387E, -1, layoutParams);
                }
                View view5 = appCompatDelegateImpl.f21387E;
                z = view5 != null;
                if (z && view5.getVisibility() != 0) {
                    View view6 = appCompatDelegateImpl.f21387E;
                    view6.setBackgroundColor((view6.getWindowSystemUiVisibility() & 8192) != 0 ? androidx.core.content.d.getColor(context, R.color.abc_decor_view_status_guard_light) : androidx.core.content.d.getColor(context, R.color.abc_decor_view_status_guard));
                }
                if (!appCompatDelegateImpl.f21392J && z) {
                    iK3 = 0;
                }
                boolean z14 = z;
                z = z13;
                z12 = z14;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z12 = false;
            } else {
                z12 = false;
                z = false;
            }
            if (z) {
                appCompatDelegateImpl.f21430w.setLayoutParams(marginLayoutParams);
            }
        }
        View view7 = appCompatDelegateImpl.f21387E;
        if (view7 != null) {
            view7.setVisibility(z12 ? 0 : 8);
        }
        if (iK2 != iK3) {
            j0Q = j02.q(j02.i(), iK3, j02.j(), j02.h());
            view2 = view;
        } else {
            view2 = view;
            j0Q = j02;
        }
        return C22823h0.v(view2, j0Q);
    }
}
