package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: SieveCache.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u00020\u0001¨\u0006\u0004"}, d2 = {"Landroidx/collection/m1;", "", "K", "V", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class m1<K, V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final Y41.p<K, V, Integer> f25764a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final Y41.l<K, V> f25765b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final Y41.r<K, V, V, Boolean, kotlin.G0> f25766c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25767d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25768e;

    /* renamed from: f, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25769f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public long[] f25770g;

    /* renamed from: h, reason: collision with root package name */
    public int f25771h;

    /* renamed from: i, reason: collision with root package name */
    public int f25772i;

    /* renamed from: j, reason: collision with root package name */
    public int f25773j;

    /* renamed from: k, reason: collision with root package name */
    public final int f25774k;

    /* renamed from: l, reason: collision with root package name */
    public int f25775l;

    /* renamed from: m, reason: collision with root package name */
    public int f25776m;

    /* renamed from: n, reason: collision with root package name */
    public int f25777n;

    /* renamed from: o, reason: collision with root package name */
    public int f25778o;

    /* compiled from: SieveCache.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u00032\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u0006\u001a\u0002H\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"<anonymous>", "", "K", "", "V", "<anonymous parameter 0>", "<anonymous parameter 1>", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Integer;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a extends kotlin.jvm.internal.N implements Y41.p<K, V, Integer> {

        /* renamed from: l, reason: collision with root package name */
        public static final a f25779l = new a();

        public a() {
            super(2);
        }

        @Override // Y41.p
        public final /* bridge */ /* synthetic */ Integer invoke(Object obj, Object obj2) {
            return 1;
        }
    }

    /* compiled from: SieveCache.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u00032\u0006\u0010\u0005\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "", "K", "", "V", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Void;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class b extends kotlin.jvm.internal.N implements Y41.l {

        /* renamed from: l, reason: collision with root package name */
        public static final b f25780l = new b();

        public b() {
            super(1);
        }

        @Override // Y41.l
        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return null;
        }
    }

    /* compiled from: SieveCache.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u000b\u001a\u00020\b\"\b\b\u0000\u0010\u0001*\u00020\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00002\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00028\u00012\b\u0010\u0005\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\n¢\u0006\u0004\b\t\u0010\n"}, d2 = {"", "K", "V", "<anonymous parameter 0>", "<anonymous parameter 1>", "<anonymous parameter 2>", "", "<anonymous parameter 3>", "Lkotlin/G0;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Z)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class c extends kotlin.jvm.internal.N implements Y41.r<K, V, V, Boolean, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public static final c f25781l = new c();

        public c() {
            super(4);
        }

        @Override // Y41.r
        public final /* bridge */ /* synthetic */ kotlin.G0 invoke(Object obj, Object obj2, Object obj3, Boolean bool) {
            bool.booleanValue();
            return kotlin.G0.f406611a;
        }
    }

    public m1() {
        throw null;
    }

    public m1(int i12, int i13, Y41.p pVar, Y41.l lVar, Y41.r rVar, int i14, C42822w c42822w) {
        i13 = (i14 & 2) != 0 ? 6 : i13;
        pVar = (i14 & 4) != 0 ? a.f25779l : pVar;
        lVar = (i14 & 8) != 0 ? b.f25780l : lVar;
        rVar = (i14 & 16) != 0 ? c.f25781l : rVar;
        this.f25764a = pVar;
        this.f25765b = lVar;
        this.f25766c = rVar;
        this.f25767d = i1.f25727a;
        Object[] objArr = K.a.f9206c;
        this.f25768e = objArr;
        this.f25769f = objArr;
        this.f25770g = n1.f25786a;
        this.f25776m = Integer.MAX_VALUE;
        this.f25777n = Integer.MAX_VALUE;
        this.f25778o = Integer.MAX_VALUE;
        if (i12 <= 0) {
            K.f.a("maxSize must be > 0");
            throw null;
        }
        this.f25774k = i12;
        c(i1.e(i13));
    }

    public final int a(int i12) {
        int i13 = this.f25771h;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25767d;
            int i16 = i14 >> 3;
            int i17 = (i14 & 7) << 3;
            long j12 = ((jArr[i16 + 1] << (64 - i17)) & ((-i17) >> 63)) | (jArr[i16] >>> i17);
            long j13 = j12 & ((~j12) << 7) & (-9187201950435737472L);
            if (j13 != 0) {
                return (i14 + (Long.numberOfTrailingZeros(j13) >> 3)) & i13;
            }
            i15 += 8;
            i14 = (i14 + i15) & i13;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0065, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0067, code lost:
    
        r10 = -1;
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V b(@Y61.k K r14) {
        /*
            r13 = this;
            int r0 = r14.hashCode()
            r1 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r0 = r0 * r1
            int r1 = r0 << 16
            r0 = r0 ^ r1
            r1 = r0 & 127(0x7f, float:1.78E-43)
            int r2 = r13.f25771h
            int r0 = r0 >>> 7
            r0 = r0 & r2
            r3 = 0
        L13:
            long[] r4 = r13.f25767d
            int r5 = r0 >> 3
            r6 = r0 & 7
            int r6 = r6 << 3
            r7 = r4[r5]
            long r7 = r7 >>> r6
            int r5 = r5 + 1
            r9 = r4[r5]
            int r4 = 64 - r6
            long r4 = r9 << r4
            long r9 = (long) r6
            long r9 = -r9
            r6 = 63
            long r9 = r9 >> r6
            long r4 = r4 & r9
            long r4 = r4 | r7
            long r6 = (long) r1
            r8 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r6 = r6 * r8
            long r6 = r6 ^ r4
            long r8 = r6 - r8
            long r6 = ~r6
            long r6 = r6 & r8
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
        L3f:
            r10 = 0
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L5e
            int r10 = java.lang.Long.numberOfTrailingZeros(r6)
            int r10 = r10 >> 3
            int r10 = r10 + r0
            r10 = r10 & r2
            java.lang.Object[] r11 = r13.f25768e
            r11 = r11[r10]
            boolean r11 = kotlin.jvm.internal.L.f(r11, r14)
            if (r11 == 0) goto L58
            goto L68
        L58:
            r10 = 1
            long r10 = r6 - r10
            long r6 = r6 & r10
            goto L3f
        L5e:
            long r6 = ~r4
            r12 = 6
            long r6 = r6 << r12
            long r4 = r4 & r6
            long r4 = r4 & r8
            int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r4 == 0) goto L8c
            r10 = -1
        L68:
            if (r10 < 0) goto L7e
            long[] r14 = r13.f25770g
            r0 = r14[r10]
            r2 = 4611686018427387903(0x3fffffffffffffff, double:1.9999999999999998)
            long r0 = r0 & r2
            r2 = 4611686018427387904(0x4000000000000000, double:2.0)
            long r0 = r0 | r2
            r14[r10] = r0
            java.lang.Object[] r14 = r13.f25769f
            r14 = r14[r10]
            return r14
        L7e:
            Y41.l<K, V> r0 = r13.f25765b
            java.lang.Object r0 = r0.invoke(r14)
            if (r0 != 0) goto L88
            r14 = 0
            return r14
        L88:
            r13.d(r14, r0)
            return r0
        L8c:
            int r3 = r3 + 8
            int r0 = r0 + r3
            r0 = r0 & r2
            goto L13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.m1.b(java.lang.Object):java.lang.Object");
    }

    public final void c(int i12) {
        long[] jArr;
        long[] jArr2;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25771h = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr3 = new long[i13];
            Arrays.fill(jArr3, 0, i13, -9187201950435737472L);
            int i14 = iMax >> 3;
            long j12 = 255 << ((iMax & 7) << 3);
            jArr3[i14] = (jArr3[i14] & (~j12)) | j12;
            jArr = jArr3;
        }
        this.f25767d = jArr;
        this.f25772i = i1.a(this.f25771h) - this.f25773j;
        Object[] objArr = K.a.f9206c;
        this.f25768e = iMax == 0 ? objArr : new Object[iMax];
        if (iMax != 0) {
            objArr = new Object[iMax];
        }
        this.f25769f = objArr;
        if (iMax == 0) {
            jArr2 = n1.f25786a;
        } else {
            long[] jArr4 = new long[iMax];
            Arrays.fill(jArr4, 0, iMax, 4611686018427387903L);
            jArr2 = jArr4;
        }
        this.f25770g = jArr2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x03c8, code lost:
    
        r3 = (r3 | r5) << 31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x03cc, code lost:
    
        if (r6 != Integer.MAX_VALUE) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x03ce, code lost:
    
        r5 = Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x03d0, code lost:
    
        r5 = r37[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x03d4, code lost:
    
        r1[r14] = r3 | r5;
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x03db, code lost:
    
        r1 = r42.f25776m;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x03e0, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x03e2, code lost:
    
        r42.f25776m = r37[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x03e6, code lost:
    
        r1 = r42.f25777n;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x03e8, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x03ea, code lost:
    
        r42.f25777n = r37[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x03ee, code lost:
    
        r1 = r42.f25778o;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x03f0, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x03f2, code lost:
    
        r42.f25778o = r37[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x03f8, code lost:
    
        r3 = a(r31);
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x03fc, code lost:
    
        r2 = 1;
        r42.f25773j++;
        r1 = r42.f25772i;
        r4 = r42.f25767d;
        r5 = r3 >> 3;
        r6 = r4[r5];
        r8 = (r3 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0418, code lost:
    
        if (((r6 >> r8) & 255) != 128) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x041b, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x041c, code lost:
    
        r42.f25772i = r1 - r2;
        r1 = r42.f25771h;
        r6 = (r6 & (~(255 << r8))) | (r35 << r8);
        r4[r5] = r6;
        r4[(((r3 - 7) & r1) + (r1 & 7)) >> 3] = r6;
        r1 = ~r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0081, code lost:
    
        if (((((~r10) << 6) & r10) & (-9187201950435737472L)) == 0) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0083, code lost:
    
        r3 = a(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x008b, code lost:
    
        if (r42.f25772i != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009f, code lost:
    
        if (((r42.f25767d[r3 >> 3] >> ((r3 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a1, code lost:
    
        r35 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a5, code lost:
    
        r3 = r42.f25771h;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a7, code lost:
    
        if (r3 <= 8) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a9, code lost:
    
        r5 = r42.f25773j;
        r20 = kotlin.w0.f410662c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bb, code lost:
    
        if (java.lang.Long.compareUnsigned(r5 * 32, r3 * 25) > 0) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00bd, code lost:
    
        r3 = r42.f25767d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00bf, code lost:
    
        if (r3 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00c1, code lost:
    
        r31 = r7;
        r35 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00c8, code lost:
    
        r5 = r42.f25771h;
        r6 = r42.f25768e;
        r8 = r42.f25769f;
        r9 = r42.f25770g;
        r4 = new long[r5];
        java.util.Arrays.fill(r4, 0, r5, 9223372034707292159L);
        r14 = 7;
        r15 = (r5 + 7) >> 3;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00e1, code lost:
    
        if (r10 >= r15) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e3, code lost:
    
        r1 = r3[r10] & (-9187201950435737472L);
        r3[r10] = (-72340172838076674L) & ((~r1) + (r1 >>> r14));
        r10 = r10 + 1;
        r12 = r12;
        r14 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0103, code lost:
    
        r35 = r12;
        r2 = r3.length;
        r10 = r2 - 1;
        r2 = r2 - 2;
        r3[r2] = (r3[r2] & 72057594037927935L) | (-72057594037927936L);
        r3[r10] = r3[0];
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x011e, code lost:
    
        if (r14 == r5) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0120, code lost:
    
        r10 = r14 >> 3;
        r13 = (r14 & 7) << 3;
        r11 = (r3[r10] >> r13) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0132, code lost:
    
        if (r11 != 128) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0134, code lost:
    
        r15 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0135, code lost:
    
        r14 = r14 + r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0137, code lost:
    
        r15 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x013a, code lost:
    
        if (r11 == 254) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x013d, code lost:
    
        r11 = r6[r14];
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x013f, code lost:
    
        if (r11 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0141, code lost:
    
        r11 = r11.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0149, code lost:
    
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x014b, code lost:
    
        r11 = r11 * (-862048943);
        r11 = r11 ^ (r11 << 16);
        r15 = r11 >>> 7;
        r12 = a(r15);
        r15 = r15 & r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0166, code lost:
    
        if ((((r12 - r15) & r5) / 8) != (((r14 - r15) & r5) / 8)) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0168, code lost:
    
        r31 = r7;
        r32 = r8;
        r3[r10] = ((r11 & 127) << r13) | ((~(255 << r13)) & r3[r10]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0184, code lost:
    
        if (r4[r14] != 9223372034707292159L) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0186, code lost:
    
        r1 = r14;
        r4[r14] = r1 | (r1 << 32);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x018c, code lost:
    
        r3[r3.length - 1] = r3[0];
        r14 = r14 + 1;
        r7 = r31;
        r8 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x019a, code lost:
    
        r31 = r7;
        r32 = r8;
        r1 = r12 >> 3;
        r33 = r3[r1];
        r2 = (r12 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01bb, code lost:
    
        if (((r33 >> r2) & 255) != 128) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01bd, code lost:
    
        r41 = r4;
        r38 = r5;
        r3[r1] = (r33 & (~(255 << r2))) | ((r11 & 127) << r2);
        r3[r10] = (r3[r10] & (~(255 << r13))) | (128 << r13);
        r6[r12] = r6[r14];
        r6[r14] = null;
        r32[r12] = r32[r14];
        r32[r14] = null;
        r9[r12] = r9[r14];
        r9[r14] = 4611686018427387903L;
        r1 = (int) ((r41[r14] >> 32) & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01ff, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0201, code lost:
    
        r41[r1] = (r41[r1] & (-4294967296L)) | r12;
        r41[r14] = (4294967295L & r41[r14]) | (-4294967296L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0211, code lost:
    
        r41[r14] = (Integer.MAX_VALUE << 32) | r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0217, code lost:
    
        r41[r12] = (r14 << 32) | Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x021f, code lost:
    
        r41 = r4;
        r38 = r5;
        r3[r1] = ((r11 & 127) << r2) | (r33 & (~(255 << r2)));
        r1 = r6[r12];
        r6[r12] = r6[r14];
        r6[r14] = r1;
        r1 = r32[r12];
        r32[r12] = r32[r14];
        r32[r14] = r1;
        r1 = r9[r12];
        r9[r12] = r9[r14];
        r9[r14] = r1;
        r1 = (int) ((r41[r14] >> 32) & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0256, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0258, code lost:
    
        r10 = r12;
        r41[r1] = (r41[r1] & (-4294967296L)) | r10;
        r41[r14] = (4294967295L & r41[r14]) | (r10 << 32);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0268, code lost:
    
        r1 = r12;
        r41[r14] = r1 | (r1 << 32);
        r1 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x026f, code lost:
    
        r41[r12] = (r1 << 32) | r14;
        r14 = r14 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0277, code lost:
    
        r3[r3.length - 1] = r3[0];
        r14 = r14 + 1;
        r7 = r31;
        r8 = r32;
        r5 = r38;
        r4 = r41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x028a, code lost:
    
        r41 = r4;
        r31 = r7;
        r42.f25772i = androidx.collection.i1.a(r42.f25771h) - r42.f25773j;
        r1 = r42.f25770g;
        r2 = r1.length;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x029e, code lost:
    
        if (r14 >= r2) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x02a0, code lost:
    
        r5 = r1[r14];
        r3 = (int) ((r5 >> 31) & 2147483647L);
        r7 = (int) (r5 & 2147483647L);
        r5 = r5 & (-4611686018427387904L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x02b0, code lost:
    
        if (r3 != Integer.MAX_VALUE) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x02b2, code lost:
    
        r3 = Integer.MAX_VALUE;
        r11 = 4294967295L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x02b9, code lost:
    
        r11 = 4294967295L;
        r3 = (int) (r41[r3] & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x02c2, code lost:
    
        r5 = (r5 | r3) << 31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x02c6, code lost:
    
        if (r7 != Integer.MAX_VALUE) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x02c8, code lost:
    
        r3 = Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x02cc, code lost:
    
        r3 = (int) (r41[r7] & r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x02d0, code lost:
    
        r1[r14] = r5 | r3;
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x02d7, code lost:
    
        r1 = r42.f25776m;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x02e1, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x02e3, code lost:
    
        r42.f25776m = (int) (r41[r1] & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x02e9, code lost:
    
        r1 = r42.f25777n;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x02eb, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x02ed, code lost:
    
        r42.f25777n = (int) (r41[r1] & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x02f3, code lost:
    
        r1 = r42.f25778o;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x02f5, code lost:
    
        if (r1 == Integer.MAX_VALUE) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x02f7, code lost:
    
        r42.f25778o = (int) (r41[r1] & 4294967295L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0301, code lost:
    
        r31 = r7;
        r35 = r12;
        r4 = 0;
        r1 = androidx.collection.i1.c(r42.f25771h);
        r2 = r42.f25767d;
        r3 = r42.f25768e;
        r5 = r42.f25769f;
        r6 = r42.f25770g;
        r7 = r42.f25771h;
        r8 = new int[r7];
        c(r1);
        r1 = r42.f25767d;
        r9 = r42.f25768e;
        r10 = r42.f25769f;
        r11 = r42.f25770g;
        r12 = r42.f25771h;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0326, code lost:
    
        if (r14 >= r7) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x033b, code lost:
    
        if (((r2[r14 >> 3] >> ((r14 & 7) << 3)) & 255) >= 128) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x033d, code lost:
    
        r13 = r3[r14];
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x033f, code lost:
    
        if (r13 == null) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0341, code lost:
    
        r15 = r13.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0349, code lost:
    
        r15 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x034b, code lost:
    
        r15 = r15 * (-862048943);
        r15 = r15 ^ (r15 << 16);
        r4 = a(r15 >>> 7);
        r20 = r2;
        r24 = r3;
        r2 = r15 & 127;
        r15 = r4 >> 3;
        r21 = (r4 & 7) << 3;
        r34 = r7;
        r37 = r8;
        r2 = (r2 << r21) | (r1[r15] & (~(255 << r21)));
        r1[r15] = r2;
        r1[(((r4 - 7) & r12) + (r12 & 7)) >> 3] = r2;
        r9[r4] = r13;
        r10[r4] = r5[r14];
        r11[r4] = r6[r14];
        r37[r14] = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0390, code lost:
    
        r20 = r2;
        r24 = r3;
        r34 = r7;
        r37 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x039c, code lost:
    
        r14 = r14 + 1;
        r2 = r20;
        r3 = r24;
        r7 = r34;
        r8 = r37;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x03a8, code lost:
    
        r37 = r8;
        r1 = r42.f25770g;
        r2 = r1.length;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x03ae, code lost:
    
        if (r14 >= r2) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x03b0, code lost:
    
        r3 = r1[r14];
        r5 = (int) ((r3 >> 31) & 2147483647L);
        r6 = (int) (r3 & 2147483647L);
        r3 = r3 & (-4611686018427387904L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x03c0, code lost:
    
        if (r5 != Integer.MAX_VALUE) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x03c2, code lost:
    
        r5 = Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x03c4, code lost:
    
        r5 = r37[r5];
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d(@Y61.k java.lang.Object r43, @Y61.k java.lang.Object r44) {
        /*
            Method dump skipped, instructions count: 1211
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.m1.d(java.lang.Object, java.lang.Object):void");
    }

    public final void e(int i12) {
        Object obj;
        Object obj2;
        while (this.f25775l > i12 && this.f25773j != 0) {
            long[] jArr = this.f25770g;
            int i13 = this.f25778o;
            if (i13 == Integer.MAX_VALUE) {
                i13 = this.f25777n;
            }
            while (i13 != Integer.MAX_VALUE) {
                long j12 = jArr[i13];
                if (((int) ((j12 >> 62) & 1)) == 0) {
                    break;
                }
                int i14 = (int) ((j12 >> 31) & 2147483647L);
                jArr[i13] = 4611686018427387903L & j12;
                i13 = i14 != Integer.MAX_VALUE ? i14 : this.f25777n;
            }
            int i15 = (int) ((jArr[i13] >> 31) & 2147483647L);
            if (i15 == Integer.MAX_VALUE) {
                i15 = Integer.MAX_VALUE;
            }
            this.f25778o = i15;
            if (i13 == Integer.MAX_VALUE) {
                return;
            }
            Object[] objArr = this.f25768e;
            Object obj3 = objArr[i13];
            this.f25773j--;
            long[] jArr2 = this.f25767d;
            int i16 = this.f25771h;
            int i17 = i13 >> 3;
            int i18 = (i13 & 7) << 3;
            long j13 = (jArr2[i17] & (~(255 << i18))) | (254 << i18);
            jArr2[i17] = j13;
            jArr2[(((i13 - 7) & i16) + (i16 & 7)) >> 3] = j13;
            objArr[i13] = null;
            Object[] objArr2 = this.f25769f;
            Object obj4 = objArr2[i13];
            objArr2[i13] = null;
            long[] jArr3 = this.f25770g;
            long j14 = jArr3[i13];
            int i19 = (int) ((j14 >> 31) & 2147483647L);
            int i22 = (int) (j14 & 2147483647L);
            if (i19 != Integer.MAX_VALUE) {
                jArr3[i19] = (i22 & 2147483647L) | (jArr3[i19] & (-2147483648L));
            } else {
                this.f25776m = i22;
            }
            if (i22 != Integer.MAX_VALUE) {
                obj = obj4;
                obj2 = obj3;
                jArr3[i22] = (jArr3[i22] & (-4611686016279904257L)) | ((2147483647L & i19) << 31);
            } else {
                obj = obj4;
                obj2 = obj3;
                this.f25777n = i19;
            }
            if (i15 == i13) {
                this.f25778o = i19;
            }
            jArr3[i13] = 4611686018427387903L;
            if (obj != null) {
                Object obj5 = obj;
                this.f25775l -= ((Number) this.f25764a.invoke(obj2, obj5)).intValue();
                this.f25766c.invoke(obj2, obj5, null, Boolean.TRUE);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = 1
            if (r1 != r0) goto L8
            return r2
        L8:
            boolean r3 = r1 instanceof androidx.collection.m1
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.m1 r1 = (androidx.collection.m1) r1
            int r3 = r1.f25775l
            int r5 = r0.f25775l
            if (r3 != r5) goto L6c
            int r3 = r1.f25773j
            int r5 = r0.f25773j
            if (r3 == r5) goto L1d
            goto L6c
        L1d:
            java.lang.Object[] r3 = r0.f25768e
            java.lang.Object[] r5 = r0.f25769f
            long[] r6 = r0.f25767d
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L6b
            r8 = r4
        L29:
            r9 = r6[r8]
            long r11 = ~r9
            r13 = 7
            long r11 = r11 << r13
            long r11 = r11 & r9
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r11 & r13
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 == 0) goto L66
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r4
        L43:
            if (r13 >= r11) goto L64
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L60
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r3[r14]
            r14 = r5[r14]
            java.lang.Object r15 = r1.b(r15)
            boolean r14 = r14.equals(r15)
            if (r14 != 0) goto L60
            return r4
        L60:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            goto L43
        L64:
            if (r11 != r12) goto L6b
        L66:
            if (r8 == r7) goto L6b
            int r8 = r8 + 1
            goto L29
        L6b:
            return r2
        L6c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.m1.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        Object[] objArr = this.f25768e;
        Object[] objArr2 = this.f25769f;
        long[] jArr = this.f25767d;
        int length = jArr.length - 2;
        int i12 = 0;
        if (length >= 0) {
            int i13 = 0;
            int iHashCode = 0;
            while (true) {
                long j12 = jArr[i13];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8 - ((~(i13 - length)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((255 & j12) < 128) {
                            int i16 = (i13 << 3) + i15;
                            Object obj = objArr[i16];
                            iHashCode += objArr2[i16].hashCode() ^ obj.hashCode();
                        }
                        j12 >>= 8;
                    }
                    if (i14 != 8) {
                        return iHashCode;
                    }
                }
                if (i13 == length) {
                    i12 = iHashCode;
                    break;
                }
                i13++;
            }
        }
        return i12;
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("SieveCache[maxSize=");
        sb2.append(this.f25774k);
        sb2.append(", size=");
        sb2.append(this.f25775l);
        sb2.append(", capacity=");
        sb2.append(this.f25771h);
        sb2.append(", count=");
        return androidx.appcompat.app.r.t(sb2, this.f25773j, ']');
    }
}
