package AA;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import com.avito.android.avito_map.AvitoMapPoint;
import com.avito.android.deep_linking.links.ExtendedProfilePhoneRequestLink;
import com.avito.android.extended_profile_map.ExtendedProfileAddress;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: ExtendedProfileMapInternalAction.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001:\b\u0002\u0003\u0004\u0005\u0006\u0007\b\t\u0082\u0001\b\n\u000b\f\r\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"LAA/b;", "", "a", "b", "c", "d", "e", "f", "g", "h", "LAA/b$a;", "LAA/b$b;", "LAA/b$c;", "LAA/b$d;", "LAA/b$e;", "LAA/b$f;", "LAA/b$g;", "LAA/b$h;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public interface b {

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/b$a;", "LAA/b;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f169a = new a();
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/b$b;", "LAA/b;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: AA.b$b, reason: collision with other inner class name */
    public static final class C0012b implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C0012b f170a = new C0012b();
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/b$c;", "LAA/b;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class c implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f171a = new c();
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/b$d;", "LAA/b;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class d implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final ExtendedProfilePhoneRequestLink f172a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final com.avito.android.extended_profile_phone_dialog.f f173b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final FA.a f174c;

        public d(@k ExtendedProfilePhoneRequestLink extendedProfilePhoneRequestLink, @k com.avito.android.extended_profile_phone_dialog.f fVar, @k FA.a aVar) {
            this.f172a = extendedProfilePhoneRequestLink;
            this.f173b = fVar;
            this.f174c = aVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return L.f(this.f172a, dVar.f172a) && L.f(this.f173b, dVar.f173b) && L.f(this.f174c, dVar.f174c);
        }

        public final int hashCode() {
            return this.f174c.hashCode() + ((this.f173b.hashCode() + (this.f172a.hashCode() * 31)) * 31);
        }

        @k
        public final String toString() {
            return "OnPhoneLoaded(deepLink=" + this.f172a + ", phoneInfo=" + this.f173b + ", analyticParams=" + this.f174c + ')';
        }
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/b$e;", "LAA/b;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class e implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final ExtendedProfileAddress f175a;

        public e(@k ExtendedProfileAddress extendedProfileAddress) {
            this.f175a = extendedProfileAddress;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof e) && L.f(this.f175a, ((e) obj).f175a);
        }

        public final int hashCode() {
            return this.f175a.hashCode();
        }

        @k
        public final String toString() {
            return "SelectAddress(address=" + this.f175a + ')';
        }
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/b$f;", "LAA/b;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class f implements b {

        /* renamed from: a, reason: collision with root package name */
        @l
        public final AvitoMapPoint f176a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final Set<ExtendedProfileAddress> f177b;

        public f(@l AvitoMapPoint avitoMapPoint, @k Set<ExtendedProfileAddress> set) {
            this.f176a = avitoMapPoint;
            this.f177b = set;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof f)) {
                return false;
            }
            f fVar = (f) obj;
            return L.f(this.f176a, fVar.f176a) && L.f(this.f177b, fVar.f177b);
        }

        public final int hashCode() {
            AvitoMapPoint avitoMapPoint = this.f176a;
            return this.f177b.hashCode() + ((avitoMapPoint == null ? 0 : avitoMapPoint.hashCode()) * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("SetAddresses(center=");
            sb2.append(this.f176a);
            sb2.append(", addresses=");
            return AK.c.u(sb2, this.f177b, ')');
        }
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/b$g;", "LAA/b;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class g implements b {

        /* renamed from: a, reason: collision with root package name */
        public final boolean f178a;

        public g(boolean z12) {
            this.f178a = z12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof g) && this.f178a == ((g) obj).f178a;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.f178a);
        }

        @k
        public final String toString() {
            return r.x(new StringBuilder("ShowLoadingOnBottomSheet(isLoading="), this.f178a, ')');
        }
    }

    /* compiled from: ExtendedProfileMapInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/b$h;", "LAA/b;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class h implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final com.avito.android.extended_profile_phone_dialog.f f179a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final FA.a f180b;

        public h(@k com.avito.android.extended_profile_phone_dialog.f fVar, @k FA.a aVar) {
            this.f179a = fVar;
            this.f180b = aVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof h)) {
                return false;
            }
            h hVar = (h) obj;
            return L.f(this.f179a, hVar.f179a) && L.f(this.f180b, hVar.f180b);
        }

        public final int hashCode() {
            return this.f180b.hashCode() + (this.f179a.hashCode() * 31);
        }

        @k
        public final String toString() {
            return "ShowPhoneDialog(phoneInfo=" + this.f179a + ", analyticParams=" + this.f180b + ')';
        }
    }
}
