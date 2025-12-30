package aa1;

import android.graphics.Bitmap;
import kotlin.jvm.internal.N;
import kotlin.jvm.internal.l0;

/* loaded from: classes9.dex */
public final class i extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ s f20945l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ n f20946m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ l0.h f20947n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public i(s sVar, n nVar, l0.h hVar) {
        super(0);
        this.f20945l = sVar;
        this.f20946m = nVar;
        this.f20947n = hVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // Y41.a
    public final Object invoke() {
        s sVar = this.f20945l;
        boolean zA2 = true;
        if (sVar.f20986b && sVar.f20989e && sVar.f20987c) {
            try {
                zA2 = this.f20946m.f20960e.a((Bitmap) this.f20947n.f406842b, sVar.f20985a);
            } catch (IllegalArgumentException e12) {
                pc1.b.f428696a.m(e12);
            }
        }
        return Boolean.valueOf(zA2);
    }
}
