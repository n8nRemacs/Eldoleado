package androidx.compose.animation.core;

import androidx.collection.C20247y0;
import androidx.compose.animation.core.B;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/n0;", "T", "Landroidx/compose/animation/core/M;", "a", "b", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.n0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20302n0<T> implements M<T> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final b<T> f26383a;

    /* compiled from: AnimationSpec.kt */
    @androidx.compose.runtime.internal.P
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0001\u0010\u00012\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00030\u0002B\u0007¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroidx/compose/animation/core/n0$b;", "T", "Landroidx/compose/animation/core/o0;", "Landroidx/compose/animation/core/n0$a;", "<init>", "()V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.n0$b */
    public static final class b<T> extends AbstractC20305o0<T, a<T>> {
        public b() {
            super(null);
        }

        @Y61.k
        public final a a(@j.F int i12, Float f12) {
            a aVar = new a(f12, null, 0, 6, null);
            this.f26395b.h(i12, aVar);
            return aVar;
        }
    }

    public C20302n0(@Y61.k b<T> bVar) {
        this.f26383a = bVar;
    }

    @Override // androidx.compose.animation.core.M, androidx.compose.animation.core.InterfaceC20307p
    @Y61.k
    /* renamed from: g, reason: merged with bridge method [inline-methods] */
    public final <V extends AbstractC20330x> V1<V> a(@Y61.k H1<T, V> h12) {
        Object obj;
        int[] iArr;
        Object[] objArr;
        long[] jArr;
        Object obj2;
        int[] iArr2;
        Object[] objArr2;
        long[] jArr2;
        int i12;
        b<T> bVar = this.f26383a;
        androidx.collection.J j12 = bVar.f26395b;
        Object obj3 = null;
        C20247y0 c20247y0 = new C20247y0(j12.f25557e + 2, null);
        androidx.collection.A0 a02 = new androidx.collection.A0(j12.f25557e);
        int[] iArr3 = j12.f25554b;
        Object[] objArr3 = j12.f25555c;
        long[] jArr3 = j12.f25553a;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i13 = 0;
            while (true) {
                long j13 = jArr3[i13];
                if ((((~j13) << 7) & j13 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8;
                    int i15 = 8 - ((~(i13 - length)) >>> 31);
                    int i16 = 0;
                    while (i16 < i15) {
                        if ((j13 & 255) < 128) {
                            int i17 = (i13 << 3) + i16;
                            int i18 = iArr3[i17];
                            a aVar = (a) objArr3[i17];
                            c20247y0.d(i18);
                            iArr2 = iArr3;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            obj2 = null;
                            a02.h(i18, new U1(h12.a().invoke(aVar.f26366a), aVar.f26367b, aVar.f26384c, null));
                            i12 = 8;
                        } else {
                            obj2 = obj3;
                            iArr2 = iArr3;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            i12 = i14;
                        }
                        j13 >>= i12;
                        i16++;
                        i14 = i12;
                        obj3 = obj2;
                        iArr3 = iArr2;
                        objArr3 = objArr2;
                        jArr3 = jArr2;
                    }
                    obj = obj3;
                    iArr = iArr3;
                    objArr = objArr3;
                    jArr = jArr3;
                    if (i15 != i14) {
                        break;
                    }
                } else {
                    obj = obj3;
                    iArr = iArr3;
                    objArr = objArr3;
                    jArr = jArr3;
                }
                if (i13 == length) {
                    break;
                }
                i13++;
                obj3 = obj;
                iArr3 = iArr;
                objArr3 = objArr;
                jArr3 = jArr;
            }
        }
        if (!j12.a(0)) {
            c20247y0.c();
        }
        if (!j12.a(bVar.f26394a)) {
            c20247y0.d(bVar.f26394a);
        }
        int i19 = c20247y0.f25545b;
        if (i19 != 0) {
            Arrays.sort(c20247y0.f25544a, 0, i19);
        }
        int i22 = bVar.f26394a;
        O o12 = Q.f26124d;
        B.f26018b.getClass();
        return new V1<>(c20247y0, a02, i22, 0, o12, 0, null);
    }

    /* compiled from: AnimationSpec.kt */
    @androidx.compose.runtime.internal.P
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010\u0002B%\b\u0000\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Landroidx/compose/animation/core/n0$a;", "T", "Landroidx/compose/animation/core/m0;", "value", "Landroidx/compose/animation/core/N;", "easing", "Landroidx/compose/animation/core/B;", "arcMode", "<init>", "(Ljava/lang/Object;Landroidx/compose/animation/core/N;ILkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.n0$a */
    public static final class a<T> extends AbstractC20299m0<T> {

        /* renamed from: c, reason: collision with root package name */
        public final int f26384c;

        public a() {
            throw null;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public a(Object obj, N n12, int i12, int i13, C42822w c42822w) {
            n12 = (i13 & 2) != 0 ? Q.f26124d : n12;
            if ((i13 & 4) != 0) {
                B.f26018b.getClass();
                i12 = 0;
            }
            this(obj, n12, i12, null);
        }

        public final boolean equals(@Y61.l Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (kotlin.jvm.internal.L.f(aVar.f26366a, this.f26366a) && kotlin.jvm.internal.L.f(aVar.f26367b, this.f26367b)) {
                int i12 = aVar.f26384c;
                int i13 = this.f26384c;
                B.a aVar2 = B.f26018b;
                if (i12 == i13) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            T t12 = this.f26366a;
            int iHashCode = t12 != null ? t12.hashCode() : 0;
            B.a aVar = B.f26018b;
            return this.f26367b.hashCode() + androidx.appcompat.app.r.e(this.f26384c, iHashCode * 31, 31);
        }

        public a(Object obj, N n12, int i12, C42822w c42822w) {
            super(obj, n12, null);
            this.f26384c = i12;
        }
    }
}
