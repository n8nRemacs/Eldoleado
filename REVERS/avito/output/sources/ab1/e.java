package ab1;

import bb1.C25625c;
import cb1.C27170i;

/* loaded from: classes9.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final com.vk.push.core.data.source.d f21028a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final C25625c f21029b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final bb1.i f21030c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final com.vk.push.core.network.data.source.g f21031d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final bb1.e f21032e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final C27170i f21033f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final T11.f f21034g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public final kotlinx.coroutines.sync.d f21035h = new kotlinx.coroutines.sync.d();

    public e(@Y61.k com.vk.push.core.data.source.d dVar, @Y61.k C25625c c25625c, @Y61.k bb1.i iVar, @Y61.k com.vk.push.core.network.data.source.g gVar, @Y61.k bb1.e eVar, @Y61.k C27170i c27170i, @Y61.k T11.f fVar) {
        this.f21028a = dVar;
        this.f21029b = c25625c;
        this.f21030c = iVar;
        this.f21031d = gVar;
        this.f21032e = eVar;
        this.f21033f = c27170i;
        this.f21034g = fVar.c(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object a(java.util.List r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof ab1.C19863c
            if (r0 == 0) goto L13
            r0 = r6
            ab1.c r0 = (ab1.C19863c) r0
            int r1 = r0.f21021t
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.f21021t = r1
            goto L18
        L13:
            ab1.c r0 = new ab1.c
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.f21019r
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.f21021t
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 != r3) goto L2f
            ab1.e r5 = r0.f21018q
            kotlin.C42729a0.b(r6)
            kotlin.Z r6 = (kotlin.Z) r6
            java.lang.Object r6 = r6.f406625b
            goto L48
        L2f:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L37:
            kotlin.C42729a0.b(r6)
            r0.f21018q = r4
            r0.f21021t = r3
            com.vk.push.core.network.data.source.g r6 = r4.f21031d
            java.lang.Object r6 = r6.a(r5, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            r5 = r4
        L48:
            java.lang.Throwable r0 = kotlin.Z.b(r6)
            if (r0 != 0) goto L4f
            goto L58
        L4f:
            T11.f r5 = r5.f21034g
            java.lang.String r6 = "Unable to get host list. Will be used empty host list"
            r5.a(r6, r0)
            kotlin.collections.z0 r6 = kotlin.collections.C42784z0.f406748b
        L58:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: ab1.e.a(java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object b(@Y61.k kotlin.coroutines.jvm.internal.ContinuationImpl r8) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r8 instanceof ab1.C19862b
            if (r0 == 0) goto L13
            r0 = r8
            ab1.b r0 = (ab1.C19862b) r0
            int r1 = r0.f21017u
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.f21017u = r1
            goto L18
        L13:
            ab1.b r0 = new ab1.b
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.f21015s
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.f21017u
            r3 = 1
            r4 = 2
            r5 = 0
            if (r2 == 0) goto L46
            if (r2 == r3) goto L3b
            if (r2 != r4) goto L33
            java.lang.Object r0 = r0.f21013q
            kotlinx.coroutines.sync.a r0 = (kotlinx.coroutines.sync.a) r0
            kotlin.C42729a0.b(r8)     // Catch: java.lang.Throwable -> L31
            goto L79
        L31:
            r8 = move-exception
            goto L83
        L33:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L3b:
            kotlinx.coroutines.sync.d r2 = r0.f21014r
            java.lang.Object r3 = r0.f21013q
            ab1.e r3 = (ab1.e) r3
            kotlin.C42729a0.b(r8)
            r8 = r2
            goto L59
        L46:
            kotlin.C42729a0.b(r8)
            kotlinx.coroutines.sync.d r8 = r7.f21035h
            r0.f21013q = r7
            r0.f21014r = r8
            r0.f21017u = r3
            java.lang.Object r2 = r8.b(r0)
            if (r2 != r1) goto L58
            return r1
        L58:
            r3 = r7
        L59:
            bb1.i r2 = r3.f21030c     // Catch: java.lang.Throwable -> L7f
            r0.f21013q = r8     // Catch: java.lang.Throwable -> L7f
            r0.f21014r = r5     // Catch: java.lang.Throwable -> L7f
            r0.f21017u = r4     // Catch: java.lang.Throwable -> L7f
            androidx.datastore.core.h<androidx.datastore.preferences.core.f> r2 = r2.f57232a     // Catch: java.lang.Throwable -> L7f
            bb1.f r3 = new bb1.f     // Catch: java.lang.Throwable -> L7f
            r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L7f
            java.lang.Object r0 = androidx.datastore.preferences.core.j.a(r2, r3, r0)     // Catch: java.lang.Throwable -> L7f
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch: java.lang.Throwable -> L7f
            if (r0 != r2) goto L73
            goto L75
        L73:
            kotlin.G0 r0 = kotlin.G0.f406611a     // Catch: java.lang.Throwable -> L7f
        L75:
            if (r0 != r1) goto L78
            return r1
        L78:
            r0 = r8
        L79:
            kotlin.G0 r8 = kotlin.G0.f406611a     // Catch: java.lang.Throwable -> L31
            r0.d(r5)
            return r8
        L7f:
            r0 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
        L83:
            r0.d(r5)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: ab1.e.b(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0055: MOVE (r0 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]) (LINE:86), block:B:20:0x0055 */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01fc A[Catch: all -> 0x0211, TRY_LEAVE, TryCatch #0 {all -> 0x0211, blocks: (B:101:0x01e4, B:106:0x01ef, B:108:0x01f3, B:112:0x01fc, B:117:0x0215, B:118:0x021a, B:120:0x0220, B:123:0x0235, B:124:0x0239, B:126:0x023f, B:130:0x0250, B:132:0x0255, B:135:0x026a, B:138:0x0288, B:145:0x0294), top: B:155:0x01e4, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0215 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ef A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00fb A[Catch: all -> 0x00a3, TRY_LEAVE, TryCatch #1 {all -> 0x00a3, blocks: (B:36:0x009f, B:49:0x00f3, B:51:0x00fb), top: B:156:0x009f }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x010f A[Catch: all -> 0x0058, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x0058, blocks: (B:18:0x0049, B:33:0x008e, B:55:0x010f, B:58:0x011a), top: B:158:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x011a A[Catch: all -> 0x0058, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x0058, blocks: (B:18:0x0049, B:33:0x008e, B:55:0x010f, B:58:0x011a), top: B:158:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0136 A[Catch: all -> 0x0159, TRY_ENTER, TryCatch #3 {all -> 0x0159, blocks: (B:27:0x006f, B:76:0x016a, B:78:0x0172, B:81:0x0180, B:83:0x0186, B:86:0x01a7, B:92:0x01b2, B:94:0x01ba, B:97:0x01cf, B:30:0x007d, B:62:0x012e, B:66:0x0136, B:68:0x0144, B:73:0x015c), top: B:158:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0172 A[Catch: all -> 0x0159, TRY_LEAVE, TryCatch #3 {all -> 0x0159, blocks: (B:27:0x006f, B:76:0x016a, B:78:0x0172, B:81:0x0180, B:83:0x0186, B:86:0x01a7, B:92:0x01b2, B:94:0x01ba, B:97:0x01cf, B:30:0x007d, B:62:0x012e, B:66:0x0136, B:68:0x0144, B:73:0x015c), top: B:158:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0180 A[Catch: all -> 0x0159, TRY_ENTER, TryCatch #3 {all -> 0x0159, blocks: (B:27:0x006f, B:76:0x016a, B:78:0x0172, B:81:0x0180, B:83:0x0186, B:86:0x01a7, B:92:0x01b2, B:94:0x01ba, B:97:0x01cf, B:30:0x007d, B:62:0x012e, B:66:0x0136, B:68:0x0144, B:73:0x015c), top: B:158:0x0022 }] */
    /* JADX WARN: Type inference failed for: r11v7, types: [java.lang.Object, kotlinx.coroutines.sync.d] */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1, types: [kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r2v12, types: [kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v28, types: [java.lang.Object, kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v32 */
    /* JADX WARN: Type inference failed for: r5v10, types: [kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.lang.Object, kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r5v14, types: [kotlinx.coroutines.sync.a] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.lang.Object] */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object c(@Y61.k kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 716
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: ab1.e.c(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
