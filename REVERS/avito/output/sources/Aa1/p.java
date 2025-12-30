package aa1;

import android.content.Context;
import android.graphics.Rect;
import wa1.C49586a;
import wa1.u;
import ya1.C50207c;

/* loaded from: classes9.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    public final Rect f20970a;

    /* renamed from: b, reason: collision with root package name */
    public final C50207c f20971b;

    /* renamed from: c, reason: collision with root package name */
    public final t f20972c;

    /* renamed from: d, reason: collision with root package name */
    public final wa1.p f20973d;

    /* renamed from: e, reason: collision with root package name */
    public final C49586a f20974e;

    /* renamed from: f, reason: collision with root package name */
    public Rect f20975f;

    public p(Context context) {
        Rect rectA = H91.b.a(context);
        this.f20970a = rectA;
        u uVar = new u(rectA.width(), rectA.height());
        C50207c c50207c = new C50207c(context);
        this.f20971b = c50207c;
        this.f20972c = new t(context);
        this.f20973d = new wa1.p(context, c50207c, uVar);
        this.f20974e = new C49586a(uVar, c50207c);
    }
}
