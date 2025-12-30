package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.p;
import java.util.ArrayList;

/* compiled from: MenuAdapter.java */
@RestrictTo
/* loaded from: classes.dex */
public class g extends BaseAdapter {

    /* renamed from: b, reason: collision with root package name */
    public final h f21824b;

    /* renamed from: c, reason: collision with root package name */
    public int f21825c = -1;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21826d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f21827e;

    /* renamed from: f, reason: collision with root package name */
    public final LayoutInflater f21828f;

    /* renamed from: g, reason: collision with root package name */
    public final int f21829g;

    public g(h hVar, LayoutInflater layoutInflater, boolean z12, int i12) {
        this.f21827e = z12;
        this.f21828f = layoutInflater;
        this.f21824b = hVar;
        this.f21829g = i12;
        a();
    }

    public final void a() {
        h hVar = this.f21824b;
        k kVar = hVar.f21852v;
        if (kVar != null) {
            hVar.i();
            ArrayList<k> arrayList = hVar.f21840j;
            int size = arrayList.size();
            for (int i12 = 0; i12 < size; i12++) {
                if (arrayList.get(i12) == kVar) {
                    this.f21825c = i12;
                    return;
                }
            }
        }
        this.f21825c = -1;
    }

    @Override // android.widget.Adapter
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public final k getItem(int i12) {
        ArrayList<k> arrayListL;
        h hVar = this.f21824b;
        if (this.f21827e) {
            hVar.i();
            arrayListL = hVar.f21840j;
        } else {
            arrayListL = hVar.l();
        }
        int i13 = this.f21825c;
        if (i13 >= 0 && i12 >= i13) {
            i12++;
        }
        return arrayListL.get(i12);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        ArrayList<k> arrayListL;
        h hVar = this.f21824b;
        if (this.f21827e) {
            hVar.i();
            arrayListL = hVar.f21840j;
        } else {
            arrayListL = hVar.l();
        }
        return this.f21825c < 0 ? arrayListL.size() : arrayListL.size() - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i12) {
        return i12;
    }

    @Override // android.widget.Adapter
    public final View getView(int i12, View view, ViewGroup viewGroup) {
        boolean z12 = false;
        if (view == null) {
            view = this.f21828f.inflate(this.f21829g, viewGroup, false);
        }
        int i13 = getItem(i12).f21862b;
        int i14 = i12 - 1;
        int i15 = i14 >= 0 ? getItem(i14).f21862b : i13;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        if (this.f21824b.m() && i13 != i15) {
            z12 = true;
        }
        listMenuItemView.setGroupDividerEnabled(z12);
        p.a aVar = (p.a) view;
        if (this.f21826d) {
            listMenuItemView.setForceShowIcon(true);
        }
        aVar.d(getItem(i12));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
