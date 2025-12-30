package androidx.compose.animation;

import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.runtime.C22082i3;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22365i0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.InterfaceC22391w;
import androidx.compose.ui.layout.InterfaceC22393x;
import androidx.compose.ui.layout.K0;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.q;
import androidx.compose.ui.unit.u;
import java.util.List;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/v;", "Landroidx/compose/ui/layout/i0;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20392v implements InterfaceC22365i0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final AnimatedContentTransitionScopeImpl<?> f26598a;

    /* compiled from: AnimatedContent.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.v$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ androidx.compose.ui.layout.K0[] f26599l;

        /* renamed from: m, reason: collision with root package name */
        public final /* synthetic */ C20392v f26600m;

        /* renamed from: n, reason: collision with root package name */
        public final /* synthetic */ int f26601n;

        /* renamed from: o, reason: collision with root package name */
        public final /* synthetic */ int f26602o;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(androidx.compose.ui.layout.K0[] k0Arr, C20392v c20392v, int i12, int i13) {
            super(1);
            this.f26599l = k0Arr;
            this.f26600m = c20392v;
            this.f26601n = i12;
            this.f26602o = i13;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            for (androidx.compose.ui.layout.K0 k02 : this.f26599l) {
                if (k02 != null) {
                    u.a aVar3 = androidx.compose.ui.unit.u.f42871b;
                    long jA2 = this.f26600m.f26598a.f25842b.a((k02.f40345b << 32) | (k02.f40346c & 4294967295L), (this.f26601n << 32) | (this.f26602o & 4294967295L), LayoutDirection.f42838b);
                    q.a aVar4 = androidx.compose.ui.unit.q.f42862b;
                    aVar2.e(k02, (int) (jA2 >> 32), (int) (jA2 & 4294967295L), 0.0f);
                }
            }
            return kotlin.G0.f406611a;
        }
    }

    public C20392v(@Y61.k AnimatedContentTransitionScopeImpl<?> animatedContentTransitionScopeImpl) {
        this.f26598a = animatedContentTransitionScopeImpl;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    @Y61.k
    public final InterfaceC22367j0 b(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k List<? extends InterfaceC22363h0> list, long j12) {
        androidx.compose.ui.layout.K0 k02;
        int i12;
        int i13;
        androidx.compose.ui.layout.K0 k03;
        int i14;
        int size = list.size();
        androidx.compose.ui.layout.K0[] k0Arr = new androidx.compose.ui.layout.K0[size];
        androidx.compose.ui.unit.u.f42871b.getClass();
        List<? extends InterfaceC22363h0> list2 = list;
        int size2 = list2.size();
        long j13 = 0;
        int i15 = 0;
        while (true) {
            if (i15 >= size2) {
                break;
            }
            InterfaceC22363h0 interfaceC22363h0 = list.get(i15);
            Object f40763y = interfaceC22363h0.getF40763y();
            AnimatedContentTransitionScopeImpl.a aVar = f40763y instanceof AnimatedContentTransitionScopeImpl.a ? (AnimatedContentTransitionScopeImpl.a) f40763y : null;
            if (aVar != null && ((Boolean) ((C22082i3) aVar.f25848b).getF42167b()).booleanValue()) {
                androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(j12);
                j13 = (k0I.f40346c & 4294967295L) | (k0I.f40345b << 32);
                kotlin.G0 g02 = kotlin.G0.f406611a;
                k0Arr[i15] = k0I;
            }
            i15++;
        }
        int size3 = list2.size();
        for (int i16 = 0; i16 < size3; i16++) {
            InterfaceC22363h0 interfaceC22363h02 = list.get(i16);
            if (k0Arr[i16] == null) {
                k0Arr[i16] = interfaceC22363h02.I(j12);
            }
        }
        if (interfaceC22369k0.I1()) {
            i12 = (int) (j13 >> 32);
        } else {
            if (size == 0) {
                k02 = null;
            } else {
                k02 = k0Arr[0];
                int i17 = size - 1;
                if (i17 != 0) {
                    int i18 = k02 != null ? k02.f40345b : 0;
                    kotlin.ranges.k it = new kotlin.ranges.l(1, i17, 1).iterator();
                    while (it.f406910d) {
                        androidx.compose.ui.layout.K0 k04 = k0Arr[it.a()];
                        int i19 = k04 != null ? k04.f40345b : 0;
                        if (i18 < i19) {
                            k02 = k04;
                            i18 = i19;
                        }
                    }
                }
            }
            i12 = k02 != null ? k02.f40345b : 0;
        }
        if (interfaceC22369k0.I1()) {
            i14 = (int) (j13 & 4294967295L);
        } else {
            if (size == 0) {
                k03 = null;
                i13 = 0;
            } else {
                i13 = 0;
                k03 = k0Arr[0];
                int i22 = size - 1;
                if (i22 != 0) {
                    int i23 = k03 != null ? k03.f40346c : 0;
                    kotlin.ranges.k it2 = new kotlin.ranges.l(1, i22, 1).iterator();
                    androidx.compose.ui.layout.K0 k05 = k03;
                    while (it2.f406910d) {
                        androidx.compose.ui.layout.K0 k06 = k0Arr[it2.a()];
                        int i24 = k06 != null ? k06.f40346c : 0;
                        if (i23 < i24) {
                            k05 = k06;
                            i23 = i24;
                        }
                    }
                    k03 = k05;
                }
            }
            i14 = k03 != null ? k03.f40346c : i13;
        }
        if (!interfaceC22369k0.I1()) {
            ((C22082i3) this.f26598a.f25843c).setValue(androidx.compose.ui.unit.u.a((i12 << 32) | (i14 & 4294967295L)));
        }
        return interfaceC22369k0.L0(i12, i14, kotlin.collections.P0.c(), new a(k0Arr, this, i12, i14));
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int c(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(list.get(0).X(i12));
            int i13 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(list.get(i13).X(i12));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i13 == size) {
                        break;
                    }
                    i13++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int e(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(list.get(0).G(i12));
            int i13 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(list.get(i13).G(i12));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i13 == size) {
                        break;
                    }
                    i13++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int f(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(list.get(0).R(i12));
            int i13 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(list.get(i13).R(i12));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i13 == size) {
                        break;
                    }
                    i13++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int h(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        Integer numValueOf;
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(list.get(0).b0(i12));
            int i13 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(list.get(i13).b0(i12));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i13 == size) {
                        break;
                    }
                    i13++;
                }
            }
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 0;
    }
}
