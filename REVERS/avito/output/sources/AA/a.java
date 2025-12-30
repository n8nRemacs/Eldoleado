package AA;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.extended_profile_map.ExtendedProfileAddress;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: ExtendedProfileMapAction.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\t\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u0082\u0001\t\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013¨\u0006\u0014"}, d2 = {"LAA/a;", "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "LAA/a$a;", "LAA/a$b;", "LAA/a$c;", "LAA/a$d;", "LAA/a$e;", "LAA/a$f;", "LAA/a$g;", "LAA/a$h;", "LAA/a$i;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public interface a {

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/a$a;", "LAA/a;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: AA.a$a, reason: collision with other inner class name */
    public static final class C0011a implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C0011a f160a = new C0011a();
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/a$b;", "LAA/a;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class b implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f161a;

        public b(@k DeepLink deepLink) {
            this.f161a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f161a, ((b) obj).f161a);
        }

        public final int hashCode() {
            return this.f161a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("DeepLinkAction(deeplink="), this.f161a, ')');
        }
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/a$c;", "LAA/a;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class c implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f162a = new c();
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/a$d;", "LAA/a;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class d implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f163a;

        public d(@k DeepLink deepLink) {
            this.f163a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof d) && L.f(this.f163a, ((d) obj).f163a);
        }

        public final int hashCode() {
            return this.f163a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("OnBottomSheetButtonClick(deeplink="), this.f163a, ')');
        }
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/a$e;", "LAA/a;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class e implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final e f164a = new e();
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/a$f;", "LAA/a;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class f implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final f f165a = new f();
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAA/a$g;", "LAA/a;", "<init>", "()V", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class g implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final g f166a = new g();
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/a$h;", "LAA/a;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class h implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final ExtendedProfileAddress f167a;

        public h(@k ExtendedProfileAddress extendedProfileAddress) {
            this.f167a = extendedProfileAddress;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof h) && L.f(this.f167a, ((h) obj).f167a);
        }

        public final int hashCode() {
            return this.f167a.hashCode();
        }

        @k
        public final String toString() {
            return "SelectAddress(address=" + this.f167a + ')';
        }
    }

    /* compiled from: ExtendedProfileMapAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAA/a$i;", "LAA/a;", "_avito_extended-profile-map_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class i implements a {

        /* renamed from: a, reason: collision with root package name */
        public final boolean f168a;

        public i(boolean z12) {
            this.f168a = z12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof i) && this.f168a == ((i) obj).f168a;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.f168a);
        }

        @k
        public final String toString() {
            return r.x(new StringBuilder("ShowLoadingOnBottomSheet(isLoading="), this.f168a, ')');
        }
    }
}
