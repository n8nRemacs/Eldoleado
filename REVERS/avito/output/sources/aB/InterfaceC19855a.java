package ab;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.C22026a;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: AdvertDetailStatsMVIAction.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0006\u0002\u0003\u0004\u0005\u0006\u0007\u0082\u0001\u0006\b\t\n\u000b\f\r¨\u0006\u000e"}, d2 = {"Lab/a;", "", "a", "b", "c", "d", "e", "f", "Lab/a$a;", "Lab/a$b;", "Lab/a$c;", "Lab/a$d;", "Lab/a$e;", "Lab/a$f;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ab.a, reason: case insensitive filesystem */
/* loaded from: classes10.dex */
public interface InterfaceC19855a {

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/a$a;", "Lab/a;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$a, reason: collision with other inner class name */
    public static final /* data */ class C1497a implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20998a;

        public C1497a(@k DeepLink deepLink) {
            this.f20998a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C1497a) && L.f(this.f20998a, ((C1497a) obj).f20998a);
        }

        public final int hashCode() {
            return this.f20998a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("HandleDeeplink(deeplink="), this.f20998a, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/a$b;", "Lab/a;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$b */
    public static final /* data */ class b implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        public final int f20999a;

        public b(int i12) {
            this.f20999a = i12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && this.f20999a == ((b) obj).f20999a;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f20999a);
        }

        @k
        public final String toString() {
            return r.t(new StringBuilder("OnSelectTab(tabPosition="), this.f20999a, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lab/a$c;", "Lab/a;", "<init>", "()V", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$c */
    public static final /* data */ class c implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f21000a = new c();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof c);
        }

        public final int hashCode() {
            return -1390002219;
        }

        @k
        public final String toString() {
            return "OpenAdvertDetails";
        }
    }

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lab/a$d;", "Lab/a;", "<init>", "()V", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$d */
    public static final /* data */ class d implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f21001a = new d();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof d);
        }

        public final int hashCode() {
            return 666128164;
        }

        @k
        public final String toString() {
            return "ScreenOpened";
        }
    }

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/a$e;", "Lab/a;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$e */
    public static final /* data */ class e implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        public final long f21002a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f21003b;

        public e(long j12, @k String str) {
            this.f21002a = j12;
            this.f21003b = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return this.f21002a == eVar.f21002a && L.f(this.f21003b, eVar.f21003b);
        }

        public final int hashCode() {
            return this.f21003b.hashCode() + (Long.hashCode(this.f21002a) * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("SetSelectedPeriod(dateFrom=");
            sb2.append(this.f21002a);
            sb2.append(", tabId=");
            return C22026a.c(sb2, this.f21003b, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lab/a$f;", "Lab/a;", "<init>", "()V", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.a$f */
    public static final /* data */ class f implements InterfaceC19855a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final f f21004a = new f();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof f);
        }

        public final int hashCode() {
            return 1270672188;
        }

        @k
        public final String toString() {
            return "UpdateUserStats";
        }
    }
}
