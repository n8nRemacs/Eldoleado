package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.l;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.view.menu.p;
import com.adjust.sdk.network.ErrorCodes;
import com.avito.android.R;
import java.util.ArrayList;

/* compiled from: ListMenuPresenter.java */
@RestrictTo
/* loaded from: classes.dex */
public class f implements o, AdapterView.OnItemClickListener {

    /* renamed from: b, reason: collision with root package name */
    public Context f21816b;

    /* renamed from: c, reason: collision with root package name */
    public LayoutInflater f21817c;

    /* renamed from: d, reason: collision with root package name */
    public h f21818d;

    /* renamed from: e, reason: collision with root package name */
    public ExpandedMenuView f21819e;

    /* renamed from: f, reason: collision with root package name */
    public o.a f21820f;

    /* renamed from: g, reason: collision with root package name */
    public a f21821g;

    /* compiled from: ListMenuPresenter.java */
    public class a extends BaseAdapter {

        /* renamed from: b, reason: collision with root package name */
        public int f21822b = -1;

        public a() {
            a();
        }

        public final void a() {
            h hVar = f.this.f21818d;
            k kVar = hVar.f21852v;
            if (kVar != null) {
                hVar.i();
                ArrayList<k> arrayList = hVar.f21840j;
                int size = arrayList.size();
                for (int i12 = 0; i12 < size; i12++) {
                    if (arrayList.get(i12) == kVar) {
                        this.f21822b = i12;
                        return;
                    }
                }
            }
            this.f21822b = -1;
        }

        @Override // android.widget.Adapter
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public final k getItem(int i12) {
            f fVar = f.this;
            h hVar = fVar.f21818d;
            hVar.i();
            ArrayList<k> arrayList = hVar.f21840j;
            fVar.getClass();
            int i13 = this.f21822b;
            if (i13 >= 0 && i12 >= i13) {
                i12++;
            }
            return arrayList.get(i12);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            f fVar = f.this;
            h hVar = fVar.f21818d;
            hVar.i();
            int size = hVar.f21840j.size();
            fVar.getClass();
            return this.f21822b < 0 ? size : size - 1;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i12) {
            return i12;
        }

        @Override // android.widget.Adapter
        public final View getView(int i12, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = f.this.f21817c.inflate(R.layout.abc_list_menu_item_layout, viewGroup, false);
            }
            ((p.a) view).d(getItem(i12));
            return view;
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }
    }

    public f(Context context) {
        this.f21816b = context;
        this.f21817c = LayoutInflater.from(context);
    }

    public final ListAdapter a() {
        if (this.f21821g == null) {
            this.f21821g = new a();
        }
        return this.f21821g;
    }

    @Override // androidx.appcompat.view.menu.o
    public final void b(h hVar, boolean z12) {
        o.a aVar = this.f21820f;
        if (aVar != null) {
            aVar.b(hVar, z12);
        }
    }

    @Override // androidx.appcompat.view.menu.o
    public final void c(boolean z12) {
        a aVar = this.f21821g;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    public final p d(ViewGroup viewGroup) {
        if (this.f21819e == null) {
            this.f21819e = (ExpandedMenuView) this.f21817c.inflate(R.layout.abc_expanded_menu_layout, viewGroup, false);
            if (this.f21821g == null) {
                this.f21821g = new a();
            }
            this.f21819e.setAdapter((ListAdapter) this.f21821g);
            this.f21819e.setOnItemClickListener(this);
        }
        return this.f21819e;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean e(k kVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean f(t tVar) {
        if (!tVar.hasVisibleItems()) {
            return false;
        }
        i iVar = new i();
        iVar.f21855b = tVar;
        Context context = tVar.f21831a;
        l.a aVar = new l.a(context);
        f fVar = new f(aVar.getContext());
        iVar.f21857d = fVar;
        fVar.f21820f = iVar;
        tVar.b(fVar, context);
        aVar.setAdapter(iVar.f21857d.a(), iVar);
        View view = tVar.f21845o;
        if (view != null) {
            aVar.setCustomTitle(view);
        } else {
            aVar.setIcon(tVar.f21844n).setTitle(tVar.f21843m);
        }
        aVar.setOnKeyListener(iVar);
        androidx.appcompat.app.l lVarCreate = aVar.create();
        iVar.f21856c = lVarCreate;
        lVarCreate.setOnDismissListener(iVar);
        WindowManager.LayoutParams attributes = iVar.f21856c.getWindow().getAttributes();
        attributes.type = ErrorCodes.MALFORMED_URL_EXCEPTION;
        attributes.flags |= 131072;
        iVar.f21856c.show();
        o.a aVar2 = this.f21820f;
        if (aVar2 == null) {
            return true;
        }
        aVar2.c(tVar);
        return true;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean g(k kVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean h() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.o
    public final void i(Context context, h hVar) {
        if (this.f21816b != null) {
            this.f21816b = context;
            if (this.f21817c == null) {
                this.f21817c = LayoutInflater.from(context);
            }
        }
        this.f21818d = hVar;
        a aVar = this.f21821g;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.o
    public final void j(o.a aVar) {
        throw null;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
        this.f21818d.q(this.f21821g.getItem(i12), this, 0);
    }
}
