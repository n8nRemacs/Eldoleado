package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: ObjectList.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002\u0082\u0001\u0001\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/Z0;", "E", "", "Landroidx/collection/N0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class Z0<E> {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25659a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    public int f25660b;

    /* compiled from: ObjectList.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "E", "element", "invoke", "(Ljava/lang/Object;)Ljava/lang/CharSequence;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<E, CharSequence> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ Z0<E> f25661l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(Z0<E> z02) {
            super(1);
            this.f25661l = z02;
        }

        @Override // Y41.l
        public final CharSequence invoke(Object obj) {
            return obj == this.f25661l ? "(this)" : String.valueOf(obj);
        }
    }

    public Z0(int i12, C42822w c42822w) {
        this.f25659a = i12 == 0 ? a1.f25678a : new Object[i12];
    }

    public final E a() {
        if (!d()) {
            return (E) this.f25659a[0];
        }
        K.f.c("ObjectList is empty.");
        throw null;
    }

    public final E b(@j.F int i12) {
        if (i12 >= 0 && i12 < this.f25660b) {
            return (E) this.f25659a[i12];
        }
        e(i12);
        throw null;
    }

    public final int c(E e12) {
        int i12 = 0;
        if (e12 == null) {
            Object[] objArr = this.f25659a;
            int i13 = this.f25660b;
            while (i12 < i13) {
                if (objArr[i12] == null) {
                    return i12;
                }
                i12++;
            }
            return -1;
        }
        Object[] objArr2 = this.f25659a;
        int i14 = this.f25660b;
        while (i12 < i14) {
            if (e12.equals(objArr2[i12])) {
                return i12;
            }
            i12++;
        }
        return -1;
    }

    public final boolean d() {
        return this.f25660b == 0;
    }

    public final void e(int i12) {
        StringBuilder sbJ = androidx.camera.camera2.internal.G.j(i12, "Index ", " must be in 0..");
        sbJ.append(this.f25660b - 1);
        K.f.b(sbJ.toString());
        throw null;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof Z0) {
            Z0 z02 = (Z0) obj;
            int i12 = z02.f25660b;
            int i13 = this.f25660b;
            if (i12 == i13) {
                Object[] objArr = this.f25659a;
                Object[] objArr2 = z02.f25659a;
                kotlin.ranges.l lVarR = kotlin.ranges.s.r(0, i13);
                int i14 = lVarR.f406905b;
                int i15 = lVarR.f406906c;
                if (i14 > i15) {
                    return true;
                }
                while (kotlin.jvm.internal.L.f(objArr[i14], objArr2[i14])) {
                    if (i14 == i15) {
                        return true;
                    }
                    i14++;
                }
                return false;
            }
        }
        return false;
    }

    public final int hashCode() {
        Object[] objArr = this.f25659a;
        int i12 = this.f25660b;
        int iHashCode = 0;
        for (int i13 = 0; i13 < i12; i13++) {
            Object obj = objArr[i13];
            iHashCode += (obj != null ? obj.hashCode() : 0) * 31;
        }
        return iHashCode;
    }

    @Y61.k
    public final String toString() {
        a aVar = new a(this);
        StringBuilder sb2 = new StringBuilder("[");
        Object[] objArr = this.f25659a;
        int i12 = this.f25660b;
        int i13 = 0;
        while (true) {
            if (i13 >= i12) {
                sb2.append((CharSequence) "]");
                break;
            }
            Object obj = objArr[i13];
            if (i13 == -1) {
                sb2.append((CharSequence) "...");
                break;
            }
            if (i13 != 0) {
                sb2.append((CharSequence) ", ");
            }
            sb2.append((CharSequence) aVar.invoke(obj));
            i13++;
        }
        return sb2.toString();
    }
}
