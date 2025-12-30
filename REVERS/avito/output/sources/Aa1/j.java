package aa1;

import android.graphics.Bitmap;
import kotlin.jvm.internal.N;
import kotlin.jvm.internal.l0;

/* loaded from: classes9.dex */
public final class j extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ s f20948l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ n f20949m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ l0.h f20950n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(s sVar, n nVar, l0.h hVar) {
        super(0);
        this.f20948l = sVar;
        this.f20949m = nVar;
        this.f20950n = hVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // Y41.a
    public final Object invoke() {
        s sVar = this.f20948l;
        return Boolean.valueOf((sVar.f20986b && sVar.f20989e && sVar.f20987c) ? this.f20949m.f20962g.a((Bitmap) this.f20950n.f406842b, sVar.f20985a, false) : true);
    }
}
