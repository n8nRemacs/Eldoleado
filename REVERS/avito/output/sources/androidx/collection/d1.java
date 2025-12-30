package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: OrderedScatterSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\u0001\u0005¨\u0006\u0006"}, d2 = {"Landroidx/collection/d1;", "E", "", "<init>", "()V", "Landroidx/collection/P0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class d1<E> {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25693a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25694b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25695c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25696d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public int f25697e;

    /* renamed from: f, reason: collision with root package name */
    @X41.f
    public int f25698f;

    /* renamed from: g, reason: collision with root package name */
    @X41.f
    public int f25699g;

    /* compiled from: OrderedScatterSet.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "E", "element", "invoke", "(Ljava/lang/Object;)Ljava/lang/CharSequence;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<E, CharSequence> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ d1<E> f25700l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(d1<E> d1Var) {
            super(1);
            this.f25700l = d1Var;
        }

        @Override // Y41.l
        public final CharSequence invoke(Object obj) {
            return obj == this.f25700l ? "(this)" : String.valueOf(obj);
        }
    }

    public /* synthetic */ d1(C42822w c42822w) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006e, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(E r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 0
            if (r1 == 0) goto Lc
            int r3 = r18.hashCode()
            goto Ld
        Lc:
            r3 = r2
        Ld:
            r4 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r3 = r3 * r4
            int r4 = r3 << 16
            r3 = r3 ^ r4
            r4 = r3 & 127(0x7f, float:1.78E-43)
            int r5 = r0.f25698f
            int r3 = r3 >>> 7
            r3 = r3 & r5
            r6 = r2
        L1c:
            long[] r7 = r0.f25693a
            int r8 = r3 >> 3
            r9 = r3 & 7
            int r9 = r9 << 3
            r10 = r7[r8]
            long r10 = r10 >>> r9
            r12 = 1
            int r8 = r8 + r12
            r13 = r7[r8]
            int r7 = 64 - r9
            long r7 = r13 << r7
            long r13 = (long) r9
            long r13 = -r13
            r9 = 63
            long r13 = r13 >> r9
            long r7 = r7 & r13
            long r7 = r7 | r10
            long r9 = (long) r4
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r9 = r9 * r13
            long r9 = r9 ^ r7
            long r13 = r9 - r13
            long r9 = ~r9
            long r9 = r9 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r9 = r9 & r13
        L48:
            r15 = 0
            int r11 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r11 == 0) goto L67
            int r11 = java.lang.Long.numberOfTrailingZeros(r9)
            int r11 = r11 >> 3
            int r11 = r11 + r3
            r11 = r11 & r5
            java.lang.Object[] r15 = r0.f25694b
            r15 = r15[r11]
            boolean r15 = kotlin.jvm.internal.L.f(r15, r1)
            if (r15 == 0) goto L61
            goto L71
        L61:
            r15 = 1
            long r15 = r9 - r15
            long r9 = r9 & r15
            goto L48
        L67:
            long r9 = ~r7
            r11 = 6
            long r9 = r9 << r11
            long r7 = r7 & r9
            long r7 = r7 & r13
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 == 0) goto L75
            r11 = -1
        L71:
            if (r11 < 0) goto L74
            r2 = r12
        L74:
            return r2
        L75:
            int r6 = r6 + 8
            int r3 = r3 + r6
            r3 = r3 & r5
            goto L1c
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.d1.a(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 1
            if (r1 != r0) goto L8
            return r2
        L8:
            boolean r3 = r1 instanceof androidx.collection.d1
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.d1 r1 = (androidx.collection.d1) r1
            int r3 = r1.f25699g
            int r5 = r0.f25699g
            if (r3 == r5) goto L17
            return r4
        L17:
            java.lang.Object[] r3 = r0.f25694b
            long[] r5 = r0.f25693a
            int r6 = r5.length
            int r6 = r6 + (-2)
            if (r6 < 0) goto L5d
            r7 = r4
        L21:
            r8 = r5[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L58
            int r10 = r7 - r6
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r4
        L3b:
            if (r12 >= r10) goto L56
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L52
            int r13 = r7 << 3
            int r13 = r13 + r12
            r13 = r3[r13]
            boolean r13 = r1.a(r13)
            if (r13 != 0) goto L52
            return r4
        L52:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L3b
        L56:
            if (r10 != r11) goto L5d
        L58:
            if (r7 == r6) goto L5d
            int r7 = r7 + 1
            goto L21
        L5d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.d1.equals(java.lang.Object):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0051 A[PHI: r0
  0x0051: PHI (r0v5 int) = (r0v4 int), (r0v6 int) binds: [B:6:0x0020, B:18:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int hashCode() {
        /*
            r15 = this;
            int r0 = r15.f25698f
            int r0 = r0 * 31
            int r1 = r15.f25699g
            int r0 = r0 + r1
            java.lang.Object[] r1 = r15.f25694b
            long[] r2 = r15.f25693a
            int r3 = r2.length
            int r3 = r3 + (-2)
            if (r3 < 0) goto L56
            r4 = 0
            r5 = r4
        L12:
            r6 = r2[r5]
            long r8 = ~r6
            r10 = 7
            long r8 = r8 << r10
            long r8 = r8 & r6
            r10 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r8 = r8 & r10
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 == 0) goto L51
            int r8 = r5 - r3
            int r8 = ~r8
            int r8 = r8 >>> 31
            r9 = 8
            int r8 = 8 - r8
            r10 = r4
        L2c:
            if (r10 >= r8) goto L4f
            r11 = 255(0xff, double:1.26E-321)
            long r11 = r11 & r6
            r13 = 128(0x80, double:6.3E-322)
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 >= 0) goto L4b
            int r11 = r5 << 3
            int r11 = r11 + r10
            r11 = r1[r11]
            boolean r12 = kotlin.jvm.internal.L.f(r11, r15)
            if (r12 != 0) goto L4b
            if (r11 == 0) goto L49
            int r11 = r11.hashCode()
            goto L4a
        L49:
            r11 = r4
        L4a:
            int r0 = r0 + r11
        L4b:
            long r6 = r6 >> r9
            int r10 = r10 + 1
            goto L2c
        L4f:
            if (r8 != r9) goto L56
        L51:
            if (r5 == r3) goto L56
            int r5 = r5 + 1
            goto L12
        L56:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.d1.hashCode():int");
    }

    @Y61.k
    public final String toString() {
        a aVar = new a(this);
        StringBuilder sb2 = new StringBuilder("[");
        Object[] objArr = this.f25694b;
        long[] jArr = this.f25695c;
        int i12 = this.f25697e;
        int i13 = 0;
        while (true) {
            if (i12 == Integer.MAX_VALUE) {
                sb2.append((CharSequence) "]");
                break;
            }
            int i14 = (int) ((jArr[i12] >> 31) & 2147483647L);
            Object obj = objArr[i12];
            if (i13 == -1) {
                sb2.append((CharSequence) "...");
                break;
            }
            if (i13 != 0) {
                sb2.append((CharSequence) ", ");
            }
            sb2.append((CharSequence) aVar.invoke(obj));
            i13++;
            i12 = i14;
        }
        return sb2.toString();
    }

    public d1() {
        this.f25693a = i1.f25727a;
        this.f25694b = K.a.f9206c;
        this.f25695c = n1.f25786a;
        this.f25696d = Integer.MAX_VALUE;
        this.f25697e = Integer.MAX_VALUE;
    }
}
