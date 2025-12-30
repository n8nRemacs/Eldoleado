package androidx.compose.animation;

import androidx.compose.runtime.C22082i3;
import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22365i0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.InterfaceC22391w;
import androidx.compose.ui.layout.InterfaceC22393x;
import androidx.compose.ui.layout.K0;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/J;", "Landroidx/compose/ui/layout/i0;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class J implements InterfaceC22365i0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C20352h0 f25905a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f25906b;

    /* compiled from: AnimatedVisibility.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

        /* renamed from: l, reason: collision with root package name */
        public final /* synthetic */ ArrayList f25907l;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(ArrayList arrayList) {
            super(1);
            this.f25907l = arrayList;
        }

        @Override // Y41.l
        public final kotlin.G0 invoke(K0.a aVar) {
            K0.a aVar2 = aVar;
            ArrayList arrayList = this.f25907l;
            int size = arrayList.size();
            for (int i12 = 0; i12 < size; i12++) {
                aVar2.e((androidx.compose.ui.layout.K0) arrayList.get(i12), 0, 0, 0.0f);
            }
            return kotlin.G0.f406611a;
        }
    }

    public J(@Y61.k C20352h0 c20352h0) {
        this.f25905a = c20352h0;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    @Y61.k
    public final InterfaceC22367j0 b(@Y61.k InterfaceC22369k0 interfaceC22369k0, @Y61.k List<? extends InterfaceC22363h0> list, long j12) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        int iMax = 0;
        int iMax2 = 0;
        for (int i12 = 0; i12 < size; i12++) {
            androidx.compose.ui.layout.K0 k0I = list.get(i12).I(j12);
            iMax = Math.max(iMax, k0I.f40345b);
            iMax2 = Math.max(iMax2, k0I.f40346c);
            arrayList.add(k0I);
        }
        boolean zI1 = interfaceC22369k0.I1();
        C20352h0 c20352h0 = this.f25905a;
        if (zI1) {
            this.f25906b = true;
            ((C22082i3) c20352h0.f26466a).setValue(androidx.compose.ui.unit.u.a((4294967295L & iMax2) | (iMax << 32)));
        } else if (!this.f25906b) {
            ((C22082i3) c20352h0.f26466a).setValue(androidx.compose.ui.unit.u.a((4294967295L & iMax2) | (iMax << 32)));
        }
        return interfaceC22369k0.L0(iMax, iMax2, kotlin.collections.P0.c(), new a(arrayList));
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int c(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        if (list.isEmpty()) {
            return 0;
        }
        int iX2 = list.get(0).X(i12);
        int i13 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int iX3 = list.get(i13).X(i12);
                if (iX3 > iX2) {
                    iX2 = iX3;
                }
                if (i13 == size) {
                    break;
                }
                i13++;
            }
        }
        return iX2;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int e(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        if (list.isEmpty()) {
            return 0;
        }
        int iG2 = list.get(0).G(i12);
        int i13 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int iG3 = list.get(i13).G(i12);
                if (iG3 > iG2) {
                    iG2 = iG3;
                }
                if (i13 == size) {
                    break;
                }
                i13++;
            }
        }
        return iG2;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int f(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        if (list.isEmpty()) {
            return 0;
        }
        int iR2 = list.get(0).R(i12);
        int i13 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int iR3 = list.get(i13).R(i12);
                if (iR3 > iR2) {
                    iR2 = iR3;
                }
                if (i13 == size) {
                    break;
                }
                i13++;
            }
        }
        return iR2;
    }

    @Override // androidx.compose.ui.layout.InterfaceC22365i0
    public final int h(@Y61.k InterfaceC22393x interfaceC22393x, @Y61.k List<? extends InterfaceC22391w> list, int i12) {
        if (list.isEmpty()) {
            return 0;
        }
        int iB02 = list.get(0).b0(i12);
        int i13 = 1;
        int size = list.size() - 1;
        if (1 <= size) {
            while (true) {
                int iB03 = list.get(i13).b0(i12);
                if (iB03 > iB02) {
                    iB02 = iB03;
                }
                if (i13 == size) {
                    break;
                }
                i13++;
            }
        }
        return iB02;
    }
}
