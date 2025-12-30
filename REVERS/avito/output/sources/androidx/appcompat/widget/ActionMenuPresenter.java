package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.K0;
import androidx.core.view.AbstractC22810b;
import com.avito.android.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
class ActionMenuPresenter extends androidx.appcompat.view.menu.b implements AbstractC22810b.a {

    /* renamed from: j, reason: collision with root package name */
    public d f21998j;

    /* renamed from: k, reason: collision with root package name */
    public Drawable f21999k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f22000l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f22001m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f22002n;

    /* renamed from: o, reason: collision with root package name */
    public int f22003o;

    /* renamed from: p, reason: collision with root package name */
    public int f22004p;

    /* renamed from: q, reason: collision with root package name */
    public int f22005q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f22006r;

    /* renamed from: s, reason: collision with root package name */
    public final SparseBooleanArray f22007s;

    /* renamed from: t, reason: collision with root package name */
    public e f22008t;

    /* renamed from: u, reason: collision with root package name */
    public a f22009u;

    /* renamed from: v, reason: collision with root package name */
    public c f22010v;

    /* renamed from: w, reason: collision with root package name */
    public b f22011w;

    /* renamed from: x, reason: collision with root package name */
    public final f f22012x;

    @SuppressLint({"BanParcelableUsage"})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: b, reason: collision with root package name */
        public int f22013b;

        public class a implements Parcelable.Creator<SavedState> {
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState();
                savedState.f22013b = parcel.readInt();
                return savedState;
            }

            @Override // android.os.Parcelable.Creator
            public final SavedState[] newArray(int i12) {
                return new SavedState[i12];
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i12) {
            parcel.writeInt(this.f22013b);
        }
    }

    public class a extends androidx.appcompat.view.menu.n {
        public a(Context context, androidx.appcompat.view.menu.t tVar, View view) {
            super(R.attr.actionOverflowMenuStyle, context, view, tVar, false);
            if (!tVar.f21933A.f()) {
                View view2 = ActionMenuPresenter.this.f21998j;
                this.f21903e = view2 == null ? (View) ActionMenuPresenter.this.f21777i : view2;
            }
            f fVar = ActionMenuPresenter.this.f22012x;
            this.f21906h = fVar;
            androidx.appcompat.view.menu.m mVar = this.f21907i;
            if (mVar != null) {
                mVar.j(fVar);
            }
        }

        @Override // androidx.appcompat.view.menu.n
        public final void c() {
            ActionMenuPresenter.this.f22009u = null;
            super.c();
        }
    }

    public class b extends ActionMenuItemView.b {
        public b() {
        }

        @Override // androidx.appcompat.view.menu.ActionMenuItemView.b
        public final androidx.appcompat.view.menu.r a() {
            a aVar = ActionMenuPresenter.this.f22009u;
            if (aVar != null) {
                return aVar.a();
            }
            return null;
        }
    }

    public class c implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final e f22016b;

        public c(e eVar) {
            this.f22016b = eVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            h.a aVar;
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            androidx.appcompat.view.menu.h hVar = actionMenuPresenter.f21772d;
            if (hVar != null && (aVar = hVar.f21835e) != null) {
                aVar.a(hVar);
            }
            View view = (View) actionMenuPresenter.f21777i;
            if (view != null && view.getWindowToken() != null) {
                e eVar = this.f22016b;
                if (eVar.b()) {
                    actionMenuPresenter.f22008t = eVar;
                } else if (eVar.f21903e != null) {
                    eVar.e(0, 0, false, false);
                    actionMenuPresenter.f22008t = eVar;
                }
            }
            actionMenuPresenter.f22010v = null;
        }
    }

    public class d extends AppCompatImageView implements ActionMenuView.a {

        public class a extends Z {
            public a(View view) {
                super(view);
            }

            @Override // androidx.appcompat.widget.Z
            public final androidx.appcompat.view.menu.r c() {
                e eVar = ActionMenuPresenter.this.f22008t;
                if (eVar == null) {
                    return null;
                }
                return eVar.a();
            }

            @Override // androidx.appcompat.widget.Z
            public final boolean d() {
                ActionMenuPresenter.this.p();
                return true;
            }

            @Override // androidx.appcompat.widget.Z
            public final boolean e() {
                ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
                if (actionMenuPresenter.f22010v != null) {
                    return false;
                }
                actionMenuPresenter.m();
                return true;
            }
        }

        public d(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            K0.a.a(this, getContentDescription());
            setOnTouchListener(new a(this));
        }

        @Override // androidx.appcompat.widget.ActionMenuView.a
        public final boolean a() {
            return false;
        }

        @Override // androidx.appcompat.widget.ActionMenuView.a
        public final boolean i() {
            return false;
        }

        @Override // android.view.View
        public final boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.p();
            return true;
        }

        @Override // android.widget.ImageView
        public final boolean setFrame(int i12, int i13, int i14, int i15) {
            boolean frame = super.setFrame(i12, i13, i14, i15);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (drawable != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int iMax = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                background.setHotspotBounds(paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
            }
            return frame;
        }
    }

    public class e extends androidx.appcompat.view.menu.n {
        public e(Context context, androidx.appcompat.view.menu.h hVar, View view) {
            super(R.attr.actionOverflowMenuStyle, context, view, hVar, true);
            this.f21904f = 8388613;
            f fVar = ActionMenuPresenter.this.f22012x;
            this.f21906h = fVar;
            androidx.appcompat.view.menu.m mVar = this.f21907i;
            if (mVar != null) {
                mVar.j(fVar);
            }
        }

        @Override // androidx.appcompat.view.menu.n
        public final void c() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            androidx.appcompat.view.menu.h hVar = actionMenuPresenter.f21772d;
            if (hVar != null) {
                hVar.c(true);
            }
            actionMenuPresenter.f22008t = null;
            super.c();
        }
    }

    public class f implements o.a {
        public f() {
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final void b(@j.N androidx.appcompat.view.menu.h hVar, boolean z12) {
            if (hVar instanceof androidx.appcompat.view.menu.t) {
                ((androidx.appcompat.view.menu.t) hVar).f21934z.k().c(false);
            }
            o.a aVar = ActionMenuPresenter.this.f21774f;
            if (aVar != null) {
                aVar.b(hVar, z12);
            }
        }

        @Override // androidx.appcompat.view.menu.o.a
        public final boolean c(@j.N androidx.appcompat.view.menu.h hVar) {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            if (hVar == actionMenuPresenter.f21772d) {
                return false;
            }
            int i12 = ((androidx.appcompat.view.menu.t) hVar).f21933A.f21861a;
            o.a aVar = actionMenuPresenter.f21774f;
            if (aVar != null) {
                return aVar.c(hVar);
            }
            return false;
        }
    }

    public ActionMenuPresenter(Context context) {
        this.f21770b = context;
        this.f21773e = LayoutInflater.from(context);
        this.f21775g = R.layout.abc_action_menu_layout;
        this.f21776h = R.layout.abc_action_menu_item_layout;
        this.f22007s = new SparseBooleanArray();
        this.f22012x = new f();
    }

    @Override // androidx.appcompat.view.menu.b
    public final void a(androidx.appcompat.view.menu.k kVar, p.a aVar) {
        aVar.d(kVar);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.f21777i);
        if (this.f22011w == null) {
            this.f22011w = new b();
        }
        actionMenuItemView.setPopupCallback(this.f22011w);
    }

    @Override // androidx.appcompat.view.menu.b, androidx.appcompat.view.menu.o
    public final void b(androidx.appcompat.view.menu.h hVar, boolean z12) {
        m();
        a aVar = this.f22009u;
        if (aVar != null && aVar.b()) {
            aVar.f21907i.dismiss();
        }
        super.b(hVar, z12);
    }

    @Override // androidx.appcompat.view.menu.b, androidx.appcompat.view.menu.o
    public final void c(boolean z12) {
        ArrayList<androidx.appcompat.view.menu.k> arrayList;
        super.c(z12);
        ((View) this.f21777i).requestLayout();
        androidx.appcompat.view.menu.h hVar = this.f21772d;
        boolean z13 = false;
        if (hVar != null) {
            hVar.i();
            ArrayList<androidx.appcompat.view.menu.k> arrayList2 = hVar.f21839i;
            int size = arrayList2.size();
            for (int i12 = 0; i12 < size; i12++) {
                AbstractC22810b abstractC22810b = arrayList2.get(i12).f21858A;
                if (abstractC22810b != null) {
                    abstractC22810b.f45026a = this;
                }
            }
        }
        androidx.appcompat.view.menu.h hVar2 = this.f21772d;
        if (hVar2 != null) {
            hVar2.i();
            arrayList = hVar2.f21840j;
        } else {
            arrayList = null;
        }
        if (this.f22001m && arrayList != null) {
            int size2 = arrayList.size();
            if (size2 == 1) {
                z13 = !arrayList.get(0).f21860C;
            } else if (size2 > 0) {
                z13 = true;
            }
        }
        if (z13) {
            if (this.f21998j == null) {
                this.f21998j = new d(this.f21770b);
            }
            ViewGroup viewGroup = (ViewGroup) this.f21998j.getParent();
            if (viewGroup != this.f21777i) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.f21998j);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.f21777i;
                d dVar = this.f21998j;
                actionMenuView.getClass();
                ActionMenuView.c cVarN = ActionMenuView.n();
                cVarN.f22034a = true;
                actionMenuView.addView(dVar, cVarN);
            }
        } else {
            d dVar2 = this.f21998j;
            if (dVar2 != null) {
                Object parent = dVar2.getParent();
                Object obj = this.f21777i;
                if (parent == obj) {
                    ((ViewGroup) obj).removeView(this.f21998j);
                }
            }
        }
        ((ActionMenuView) this.f21777i).setOverflowReserved(this.f22001m);
    }

    @Override // androidx.appcompat.view.menu.b
    public final boolean d(ViewGroup viewGroup, int i12) {
        if (viewGroup.getChildAt(i12) == this.f21998j) {
            return false;
        }
        viewGroup.removeViewAt(i12);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.b, androidx.appcompat.view.menu.o
    public final boolean f(androidx.appcompat.view.menu.t tVar) {
        boolean z12;
        if (!tVar.hasVisibleItems()) {
            return false;
        }
        androidx.appcompat.view.menu.t tVar2 = tVar;
        while (true) {
            androidx.appcompat.view.menu.h hVar = tVar2.f21934z;
            if (hVar == this.f21772d) {
                break;
            }
            tVar2 = (androidx.appcompat.view.menu.t) hVar;
        }
        ViewGroup viewGroup = (ViewGroup) this.f21777i;
        View view = null;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            int i12 = 0;
            while (true) {
                if (i12 >= childCount) {
                    break;
                }
                View childAt = viewGroup.getChildAt(i12);
                if ((childAt instanceof p.a) && ((p.a) childAt).getItemData() == tVar2.f21933A) {
                    view = childAt;
                    break;
                }
                i12++;
            }
        }
        if (view == null) {
            return false;
        }
        int i13 = tVar.f21933A.f21861a;
        int size = tVar.f21836f.size();
        int i14 = 0;
        while (true) {
            if (i14 >= size) {
                z12 = false;
                break;
            }
            MenuItem item = tVar.getItem(i14);
            if (item.isVisible() && item.getIcon() != null) {
                z12 = true;
                break;
            }
            i14++;
        }
        a aVar = new a(this.f21771c, tVar, view);
        this.f22009u = aVar;
        aVar.d(z12);
        a aVar2 = this.f22009u;
        if (!aVar2.b()) {
            if (aVar2.f21903e == null) {
                throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
            }
            aVar2.e(0, 0, false, false);
        }
        super.f(tVar);
        return true;
    }

    @Override // androidx.appcompat.view.menu.b, androidx.appcompat.view.menu.o
    public final boolean h() {
        int size;
        ArrayList<androidx.appcompat.view.menu.k> arrayListL;
        int i12;
        boolean z12;
        androidx.appcompat.view.menu.h hVar = this.f21772d;
        if (hVar != null) {
            arrayListL = hVar.l();
            size = arrayListL.size();
        } else {
            size = 0;
            arrayListL = null;
        }
        int i13 = this.f22005q;
        int i14 = this.f22004p;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) this.f21777i;
        int i15 = 0;
        boolean z13 = false;
        int i16 = 0;
        int i17 = 0;
        while (true) {
            i12 = 2;
            z12 = true;
            if (i15 >= size) {
                break;
            }
            androidx.appcompat.view.menu.k kVar = arrayListL.get(i15);
            int i18 = kVar.f21885y;
            if ((i18 & 2) == 2) {
                i16++;
            } else if ((i18 & 1) == 1) {
                i17++;
            } else {
                z13 = true;
            }
            if (this.f22006r && kVar.f21860C) {
                i13 = 0;
            }
            i15++;
        }
        if (this.f22001m && (z13 || i17 + i16 > i13)) {
            i13--;
        }
        int i19 = i13 - i16;
        SparseBooleanArray sparseBooleanArray = this.f22007s;
        sparseBooleanArray.clear();
        int i22 = 0;
        int i23 = 0;
        while (i22 < size) {
            androidx.appcompat.view.menu.k kVar2 = arrayListL.get(i22);
            int i24 = kVar2.f21885y;
            boolean z14 = (i24 & 2) == i12 ? z12 : false;
            int i25 = kVar2.f21862b;
            if (z14) {
                View viewK = k(kVar2, null, viewGroup);
                viewK.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredWidth = viewK.getMeasuredWidth();
                i14 -= measuredWidth;
                if (i23 == 0) {
                    i23 = measuredWidth;
                }
                if (i25 != 0) {
                    sparseBooleanArray.put(i25, z12);
                }
                kVar2.h(z12);
            } else if ((i24 & 1) == z12) {
                boolean z15 = sparseBooleanArray.get(i25);
                boolean z16 = ((i19 > 0 || z15) && i14 > 0) ? z12 : false;
                if (z16) {
                    View viewK2 = k(kVar2, null, viewGroup);
                    viewK2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                    int measuredWidth2 = viewK2.getMeasuredWidth();
                    i14 -= measuredWidth2;
                    if (i23 == 0) {
                        i23 = measuredWidth2;
                    }
                    z16 &= i14 + i23 > 0;
                }
                if (z16 && i25 != 0) {
                    sparseBooleanArray.put(i25, true);
                } else if (z15) {
                    sparseBooleanArray.put(i25, false);
                    for (int i26 = 0; i26 < i22; i26++) {
                        androidx.appcompat.view.menu.k kVar3 = arrayListL.get(i26);
                        if (kVar3.f21862b == i25) {
                            if (kVar3.f()) {
                                i19++;
                            }
                            kVar3.h(false);
                        }
                    }
                }
                if (z16) {
                    i19--;
                }
                kVar2.h(z16);
            } else {
                kVar2.h(false);
                i22++;
                i12 = 2;
                z12 = true;
            }
            i22++;
            i12 = 2;
            z12 = true;
        }
        return z12;
    }

    @Override // androidx.appcompat.view.menu.b, androidx.appcompat.view.menu.o
    public final void i(@j.N Context context, @j.P androidx.appcompat.view.menu.h hVar) {
        super.i(context, hVar);
        Resources resources = context.getResources();
        androidx.appcompat.view.a aVar = new androidx.appcompat.view.a();
        aVar.f21653a = context;
        if (!this.f22002n) {
            this.f22001m = true;
        }
        this.f22003o = context.getResources().getDisplayMetrics().widthPixels / 2;
        this.f22005q = aVar.a();
        int measuredWidth = this.f22003o;
        if (this.f22001m) {
            if (this.f21998j == null) {
                d dVar = new d(this.f21770b);
                this.f21998j = dVar;
                if (this.f22000l) {
                    dVar.setImageDrawable(this.f21999k);
                    this.f21999k = null;
                    this.f22000l = false;
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.f21998j.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.f21998j.getMeasuredWidth();
        } else {
            this.f21998j = null;
        }
        this.f22004p = measuredWidth;
        float f12 = resources.getDisplayMetrics().density;
    }

    @Override // androidx.appcompat.view.menu.b
    public final View k(androidx.appcompat.view.menu.k kVar, View view, ViewGroup viewGroup) {
        View actionView = kVar.getActionView();
        if (actionView == null || kVar.e()) {
            actionView = super.k(kVar, view, viewGroup);
        }
        actionView.setVisibility(kVar.f21860C ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        ((ActionMenuView) viewGroup).getClass();
        if (!(layoutParams instanceof ActionMenuView.c)) {
            actionView.setLayoutParams(ActionMenuView.o(layoutParams));
        }
        return actionView;
    }

    @Override // androidx.appcompat.view.menu.b
    public final boolean l(androidx.appcompat.view.menu.k kVar) {
        return kVar.f();
    }

    public final boolean m() {
        Object obj;
        c cVar = this.f22010v;
        if (cVar != null && (obj = this.f21777i) != null) {
            ((View) obj).removeCallbacks(cVar);
            this.f22010v = null;
            return true;
        }
        e eVar = this.f22008t;
        if (eVar == null) {
            return false;
        }
        if (eVar.b()) {
            eVar.f21907i.dismiss();
        }
        return true;
    }

    public final boolean n() {
        e eVar = this.f22008t;
        return eVar != null && eVar.b();
    }

    public final void o(boolean z12) {
        if (z12) {
            super.f(null);
            return;
        }
        androidx.appcompat.view.menu.h hVar = this.f21772d;
        if (hVar != null) {
            hVar.c(false);
        }
    }

    public final boolean p() {
        androidx.appcompat.view.menu.h hVar;
        if (!this.f22001m || n() || (hVar = this.f21772d) == null || this.f21777i == null || this.f22010v != null) {
            return false;
        }
        hVar.i();
        if (hVar.f21840j.isEmpty()) {
            return false;
        }
        c cVar = new c(new e(this.f21771c, this.f21772d, this.f21998j));
        this.f22010v = cVar;
        ((View) this.f21777i).post(cVar);
        return true;
    }
}
