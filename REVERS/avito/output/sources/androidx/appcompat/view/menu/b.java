package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.view.menu.p;
import java.util.ArrayList;

/* compiled from: BaseMenuPresenter.java */
@RestrictTo
/* loaded from: classes.dex */
public abstract class b implements o {

    /* renamed from: b, reason: collision with root package name */
    public Context f21770b;

    /* renamed from: c, reason: collision with root package name */
    public Context f21771c;

    /* renamed from: d, reason: collision with root package name */
    public h f21772d;

    /* renamed from: e, reason: collision with root package name */
    public LayoutInflater f21773e;

    /* renamed from: f, reason: collision with root package name */
    public o.a f21774f;

    /* renamed from: g, reason: collision with root package name */
    public int f21775g;

    /* renamed from: h, reason: collision with root package name */
    public int f21776h;

    /* renamed from: i, reason: collision with root package name */
    public p f21777i;

    public abstract void a(k kVar, p.a aVar);

    @Override // androidx.appcompat.view.menu.o
    public void b(h hVar, boolean z12) {
        o.a aVar = this.f21774f;
        if (aVar != null) {
            aVar.b(hVar, z12);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.o
    public void c(boolean z12) {
        ViewGroup viewGroup = (ViewGroup) this.f21777i;
        if (viewGroup == null) {
            return;
        }
        h hVar = this.f21772d;
        int i12 = 0;
        if (hVar != null) {
            hVar.i();
            ArrayList<k> arrayListL = this.f21772d.l();
            int size = arrayListL.size();
            int i13 = 0;
            for (int i14 = 0; i14 < size; i14++) {
                k kVar = arrayListL.get(i14);
                if (l(kVar)) {
                    View childAt = viewGroup.getChildAt(i13);
                    k itemData = childAt instanceof p.a ? ((p.a) childAt).getItemData() : null;
                    View viewK = k(kVar, childAt, viewGroup);
                    if (kVar != itemData) {
                        viewK.setPressed(false);
                        viewK.jumpDrawablesToCurrentState();
                    }
                    if (viewK != childAt) {
                        ViewGroup viewGroup2 = (ViewGroup) viewK.getParent();
                        if (viewGroup2 != null) {
                            viewGroup2.removeView(viewK);
                        }
                        ((ViewGroup) this.f21777i).addView(viewK, i13);
                    }
                    i13++;
                }
            }
            i12 = i13;
        }
        while (i12 < viewGroup.getChildCount()) {
            if (!d(viewGroup, i12)) {
                i12++;
            }
        }
    }

    public boolean d(ViewGroup viewGroup, int i12) {
        viewGroup.removeViewAt(i12);
        return true;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean e(k kVar) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [androidx.appcompat.view.menu.h] */
    @Override // androidx.appcompat.view.menu.o
    public boolean f(t tVar) {
        o.a aVar = this.f21774f;
        t tVar2 = tVar;
        if (aVar == null) {
            return false;
        }
        if (tVar == null) {
            tVar2 = this.f21772d;
        }
        return aVar.c(tVar2);
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean g(k kVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public boolean h() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public void i(Context context, h hVar) {
        this.f21771c = context;
        LayoutInflater.from(context);
        this.f21772d = hVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View k(k kVar, View view, ViewGroup viewGroup) {
        p.a aVar = view instanceof p.a ? (p.a) view : (p.a) this.f21773e.inflate(this.f21776h, viewGroup, false);
        a(kVar, aVar);
        return (View) aVar;
    }

    public boolean l(k kVar) {
        return true;
    }
}
