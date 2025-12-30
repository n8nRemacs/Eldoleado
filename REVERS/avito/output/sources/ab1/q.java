package ab1;

import kotlinx.coroutines.C43262l0;

/* loaded from: classes9.dex */
public final class q {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final cb1.p f21057a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final kotlinx.coroutines.scheduling.b f21058b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final T11.f f21059c;

    public q() {
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object a(@Y61.k java.lang.String r5, @Y61.k kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof ab1.o
            if (r0 == 0) goto L13
            r0 = r6
            ab1.o r0 = (ab1.o) r0
            int r1 = r0.f21053s
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.f21053s = r1
            goto L18
        L13:
            ab1.o r0 = new ab1.o
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.f21051q
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.f21053s
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.C42729a0.b(r6)
            goto L45
        L29:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L31:
            kotlin.C42729a0.b(r6)
            ab1.p r6 = new ab1.p
            r2 = 0
            r6.<init>(r4, r5, r2)
            r0.f21053s = r3
            kotlinx.coroutines.scheduling.b r5 = r4.f21058b
            java.lang.Object r6 = kotlinx.coroutines.C43259k.g(r5, r6, r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            kotlin.Z r6 = (kotlin.Z) r6
            java.lang.Object r5 = r6.f406625b
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: ab1.q.a(java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public q(cb1.p pVar, T11.f fVar) {
        kotlinx.coroutines.scheduling.b bVar = C43262l0.f411947c;
        this.f21057a = pVar;
        this.f21058b = bVar;
        this.f21059c = fVar.c(this);
    }
}
