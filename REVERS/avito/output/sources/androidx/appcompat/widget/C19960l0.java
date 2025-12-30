package androidx.appcompat.widget;

import android.content.Context;
import android.view.View;
import com.avito.android.R;

/* compiled from: PopupMenu.java */
/* renamed from: androidx.appcompat.widget.l0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19960l0 {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.appcompat.view.menu.h f22564a;

    /* renamed from: b, reason: collision with root package name */
    public final View f22565b;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.appcompat.view.menu.n f22566c;

    /* compiled from: PopupMenu.java */
    /* renamed from: androidx.appcompat.widget.l0$a */
    public interface a {
    }

    /* compiled from: PopupMenu.java */
    /* renamed from: androidx.appcompat.widget.l0$b */
    public interface b {
    }

    public C19960l0(@j.N Context context, @j.N View view) {
        this.f22565b = view;
        androidx.appcompat.view.menu.h hVar = new androidx.appcompat.view.menu.h(context);
        this.f22564a = hVar;
        hVar.f21835e = new C19954i0(this);
        androidx.appcompat.view.menu.n nVar = new androidx.appcompat.view.menu.n(R.attr.popupMenuStyle, context, view, hVar, false);
        this.f22566c = nVar;
        nVar.f21904f = 83;
        nVar.f21908j = new C19956j0(this);
    }
}
