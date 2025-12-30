package androidx.compose.animation;

import androidx.compose.ui.InterfaceC22215f;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/r0;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.r0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* data */ class C20381r0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22215f f26567a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> f26568b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.animation.core.V<androidx.compose.ui.unit.u> f26569c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f26570d;

    /* compiled from: EnterExitTransition.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/unit/u;", "it", "invoke-mzRDjE0", "(J)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    /* renamed from: androidx.compose.animation.r0$a */
    public static final class a extends kotlin.jvm.internal.N implements Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> {

        /* renamed from: l, reason: collision with root package name */
        public static final a f26571l = new a();

        public a() {
            super(1);
        }

        @Override // Y41.l
        public final androidx.compose.ui.unit.u invoke(androidx.compose.ui.unit.u uVar) {
            long j12 = uVar.f42872a;
            long j13 = 0;
            return androidx.compose.ui.unit.u.a((j13 & 4294967295L) | (j13 << 32));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public C20381r0(@Y61.k InterfaceC22215f interfaceC22215f, @Y61.k Y41.l<? super androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> lVar, @Y61.k androidx.compose.animation.core.V<androidx.compose.ui.unit.u> v12, boolean z12) {
        this.f26567a = interfaceC22215f;
        this.f26568b = lVar;
        this.f26569c = v12;
        this.f26570d = z12;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C20381r0)) {
            return false;
        }
        C20381r0 c20381r0 = (C20381r0) obj;
        return kotlin.jvm.internal.L.f(this.f26567a, c20381r0.f26567a) && kotlin.jvm.internal.L.f(this.f26568b, c20381r0.f26568b) && kotlin.jvm.internal.L.f(this.f26569c, c20381r0.f26569c) && this.f26570d == c20381r0.f26570d;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.f26570d) + ((this.f26569c.hashCode() + ((this.f26568b.hashCode() + (this.f26567a.hashCode() * 31)) * 31)) * 31);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ChangeSize(alignment=");
        sb2.append(this.f26567a);
        sb2.append(", size=");
        sb2.append(this.f26568b);
        sb2.append(", animationSpec=");
        sb2.append(this.f26569c);
        sb2.append(", clip=");
        return androidx.appcompat.app.r.x(sb2, this.f26570d, ')');
    }

    public /* synthetic */ C20381r0(InterfaceC22215f interfaceC22215f, Y41.l lVar, androidx.compose.animation.core.V v12, boolean z12, int i12, C42822w c42822w) {
        this(interfaceC22215f, (i12 & 2) != 0 ? a.f26571l : lVar, v12, (i12 & 8) != 0 ? true : z12);
    }
}
