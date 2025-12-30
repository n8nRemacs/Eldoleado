package AI0;

import MI0.d;
import PI0.b;
import RI0.b;
import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;
import vI0.InterfaceC49220b;
import xI0.InterfaceC49845b;
import yI0.InterfaceC50119b;

/* compiled from: ProfileHeaderState.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LAI0/c;", "", "a", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class c {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final d f269a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final II0.d f270b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final PI0.b f271c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final InterfaceC49845b f272d;

    /* renamed from: e, reason: collision with root package name */
    @k
    public final RI0.b f273e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final InterfaceC49220b f274f;

    /* renamed from: g, reason: collision with root package name */
    @k
    public final a f275g;

    /* renamed from: h, reason: collision with root package name */
    @k
    public final InterfaceC50119b f276h;

    /* compiled from: ProfileHeaderState.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAI0/c$a;", "", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final List<com.avito.android.user_adverts.root_screen.adverts_host.panel_view.a> f277a;

        /* renamed from: b, reason: collision with root package name */
        public final int f278b;

        public a() {
            this(null, 0, 3, null);
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return L.f(this.f277a, aVar.f277a) && this.f278b == aVar.f278b;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f278b) + (this.f277a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("ViewState(panelItems=");
            sb2.append(this.f277a);
            sb2.append(", scrollPosition=");
            return r.t(sb2, this.f278b, ')');
        }

        /* JADX WARN: Multi-variable type inference failed */
        public a(@k List<? extends com.avito.android.user_adverts.root_screen.adverts_host.panel_view.a> list, int i12) {
            this.f277a = list;
            this.f278b = i12;
        }

        public a(List list, int i12, int i13, C42822w c42822w) {
            this((i13 & 1) != 0 ? C42784z0.f406748b : list, (i13 & 2) != 0 ? -1 : i12);
        }
    }

    public c() {
        this(null, null, null, null, null, null, null, null, 255, null);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return L.f(this.f269a, cVar.f269a) && L.f(this.f270b, cVar.f270b) && L.f(this.f271c, cVar.f271c) && L.f(this.f272d, cVar.f272d) && L.f(this.f273e, cVar.f273e) && L.f(this.f274f, cVar.f274f) && L.f(this.f275g, cVar.f275g) && L.f(this.f276h, cVar.f276h);
    }

    public final int hashCode() {
        return this.f276h.hashCode() + ((this.f275g.hashCode() + ((this.f274f.hashCode() + ((this.f273e.hashCode() + ((this.f272d.hashCode() + ((this.f271c.hashCode() + ((this.f270b.hashCode() + (this.f269a.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    @k
    public final String toString() {
        return "ProfileHeaderState(panelSoaState=" + this.f269a + ", panelConfigState=" + this.f270b + ", safetyInfoState=" + this.f271c + ", charityState=" + this.f272d + ", serviceFreemiumState=" + this.f273e + ", appRaterState=" + this.f274f + ", viewState=" + this.f275g + ", deliveryRestrictionState=" + this.f276h + ')';
    }

    public c(@k d dVar, @k II0.d dVar2, @k PI0.b bVar, @k InterfaceC49845b interfaceC49845b, @k RI0.b bVar2, @k InterfaceC49220b interfaceC49220b, @k a aVar, @k InterfaceC50119b interfaceC50119b) {
        this.f269a = dVar;
        this.f270b = dVar2;
        this.f271c = bVar;
        this.f272d = interfaceC49845b;
        this.f273e = bVar2;
        this.f274f = interfaceC49220b;
        this.f275g = aVar;
        this.f276h = interfaceC50119b;
    }

    public /* synthetic */ c(d dVar, II0.d dVar2, PI0.b bVar, InterfaceC49845b interfaceC49845b, RI0.b bVar2, InterfaceC49220b interfaceC49220b, a aVar, InterfaceC50119b interfaceC50119b, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? new d(null, null, null, 7, null) : dVar, (i12 & 2) != 0 ? new II0.d(null, null, 3, null) : dVar2, (i12 & 4) != 0 ? b.a.f12955a : bVar, (i12 & 8) != 0 ? InterfaceC49845b.a.f442331a : interfaceC49845b, (i12 & 16) != 0 ? b.a.f14303a : bVar2, (i12 & 32) != 0 ? InterfaceC49220b.a.f440668a : interfaceC49220b, (i12 & 64) != 0 ? new a(null, 0, 3, null) : aVar, (i12 & 128) != 0 ? InterfaceC50119b.a.f443023a : interfaceC50119b);
    }
}
