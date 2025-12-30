package AA;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import com.avito.android.avito_map.AvitoMapPoint;
import com.avito.android.deep_linking.links.ExtendedProfilePhoneRequestLink;
import com.avito.android.extended_profile_map.ExtendedProfileAddress;
import com.avito.android.extended_profile_map.bottom_sheet.g;
import com.avito.android.extended_profile_phone_dialog.f;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.B0;
import kotlin.collections.P0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: ExtendedProfileMapState.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"LAA/d;", "", "a", "b", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final /* data */ class d {

    /* renamed from: g, reason: collision with root package name */
    @k
    public static final a f182g = new a(null);

    /* renamed from: h, reason: collision with root package name */
    @k
    public static final d f183h = new d(B0.f406639b, null, null, null, null, null, 32, null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Set<ExtendedProfileAddress> f184a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final AvitoMapPoint f185b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final ExtendedProfileAddress f186c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final g f187d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final b f188e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final Map<ExtendedProfilePhoneRequestLink, f> f189f;

    /* compiled from: ExtendedProfileMapState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/d$a;", "", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    /* compiled from: ExtendedProfileMapState.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/d$b;", "", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final f f190a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final FA.a f191b;

        public b(@k f fVar, @k FA.a aVar) {
            this.f190a = fVar;
            this.f191b = aVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return L.f(this.f190a, bVar.f190a) && L.f(this.f191b, bVar.f191b);
        }

        public final int hashCode() {
            return this.f191b.hashCode() + (this.f190a.hashCode() * 31);
        }

        @k
        public final String toString() {
            return "PhoneDialog(phoneInfo=" + this.f190a + ", analyticParams=" + this.f191b + ')';
        }
    }

    public d(@k Set<ExtendedProfileAddress> set, @l AvitoMapPoint avitoMapPoint, @l ExtendedProfileAddress extendedProfileAddress, @l g gVar, @l b bVar, @k Map<ExtendedProfilePhoneRequestLink, f> map) {
        this.f184a = set;
        this.f185b = avitoMapPoint;
        this.f186c = extendedProfileAddress;
        this.f187d = gVar;
        this.f188e = bVar;
        this.f189f = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static d a(d dVar, Set set, AvitoMapPoint avitoMapPoint, ExtendedProfileAddress extendedProfileAddress, g gVar, b bVar, LinkedHashMap linkedHashMap, int i12) {
        if ((i12 & 1) != 0) {
            set = dVar.f184a;
        }
        Set set2 = set;
        if ((i12 & 2) != 0) {
            avitoMapPoint = dVar.f185b;
        }
        AvitoMapPoint avitoMapPoint2 = avitoMapPoint;
        if ((i12 & 4) != 0) {
            extendedProfileAddress = dVar.f186c;
        }
        ExtendedProfileAddress extendedProfileAddress2 = extendedProfileAddress;
        if ((i12 & 8) != 0) {
            gVar = dVar.f187d;
        }
        g gVar2 = gVar;
        if ((i12 & 16) != 0) {
            bVar = dVar.f188e;
        }
        b bVar2 = bVar;
        Map map = linkedHashMap;
        if ((i12 & 32) != 0) {
            map = dVar.f189f;
        }
        dVar.getClass();
        return new d(set2, avitoMapPoint2, extendedProfileAddress2, gVar2, bVar2, map);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return L.f(this.f184a, dVar.f184a) && L.f(this.f185b, dVar.f185b) && L.f(this.f186c, dVar.f186c) && L.f(this.f187d, dVar.f187d) && L.f(this.f188e, dVar.f188e) && L.f(this.f189f, dVar.f189f);
    }

    public final int hashCode() {
        int iHashCode = this.f184a.hashCode() * 31;
        AvitoMapPoint avitoMapPoint = this.f185b;
        int iHashCode2 = (iHashCode + (avitoMapPoint == null ? 0 : avitoMapPoint.hashCode())) * 31;
        ExtendedProfileAddress extendedProfileAddress = this.f186c;
        int iHashCode3 = (iHashCode2 + (extendedProfileAddress == null ? 0 : extendedProfileAddress.hashCode())) * 31;
        g gVar = this.f187d;
        int iHashCode4 = (iHashCode3 + (gVar == null ? 0 : gVar.hashCode())) * 31;
        b bVar = this.f188e;
        return this.f189f.hashCode() + ((iHashCode4 + (bVar != null ? bVar.hashCode() : 0)) * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ExtendedProfileMapState(addresses=");
        sb2.append(this.f184a);
        sb2.append(", center=");
        sb2.append(this.f185b);
        sb2.append(", selectedAddress=");
        sb2.append(this.f186c);
        sb2.append(", bottomSheetData=");
        sb2.append(this.f187d);
        sb2.append(", phoneDialog=");
        sb2.append(this.f188e);
        sb2.append(", phones=");
        return r.w(sb2, this.f189f, ')');
    }

    public /* synthetic */ d(Set set, AvitoMapPoint avitoMapPoint, ExtendedProfileAddress extendedProfileAddress, g gVar, b bVar, Map map, int i12, C42822w c42822w) {
        this(set, avitoMapPoint, extendedProfileAddress, gVar, bVar, (i12 & 32) != 0 ? P0.c() : map);
    }
}
