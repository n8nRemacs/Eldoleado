package ab;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import com.avito.android.deep_linking.links.DeepLink;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0005\u0002\u0003\u0004\u0005\u0006\u0082\u0001\u0005\u0007\b\t\n\u000b¨\u0006\f"}, d2 = {"Lab/b;", "", "a", "b", "c", "d", "e", "Lab/b$a;", "Lab/b$b;", "Lab/b$c;", "Lab/b$d;", "Lab/b$e;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ab.b, reason: case insensitive filesystem */
/* loaded from: classes10.dex */
public interface InterfaceC19856b {

    /* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/b$a;", "Lab/b;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.b$a */
    public static final /* data */ class a implements InterfaceC19856b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f21005a;

        public a(@k DeepLink deepLink) {
            this.f21005a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && L.f(this.f21005a, ((a) obj).f21005a);
        }

        public final int hashCode() {
            return this.f21005a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("HandleDeeplink(deeplink="), this.f21005a, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/b$b;", "Lab/b;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.b$b, reason: collision with other inner class name */
    public static final /* data */ class C1498b implements InterfaceC19856b {

        /* renamed from: a, reason: collision with root package name */
        public final long f21006a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f21007b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final ArrayList f21008c;

        public C1498b(long j12, @k String str, @k ArrayList arrayList) {
            this.f21006a = j12;
            this.f21007b = str;
            this.f21008c = arrayList;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C1498b)) {
                return false;
            }
            C1498b c1498b = (C1498b) obj;
            return this.f21006a == c1498b.f21006a && this.f21007b.equals(c1498b.f21007b) && this.f21008c.equals(c1498b.f21008c);
        }

        public final int hashCode() {
            return this.f21008c.hashCode() + H.d(Long.hashCode(this.f21006a) * 31, 31, this.f21007b);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("LastDateChanged(date=");
            sb2.append(this.f21006a);
            sb2.append(", tabId=");
            sb2.append(this.f21007b);
            sb2.append(", tabIds=");
            return androidx.compose.ui.graphics.colorspace.e.p(sb2, this.f21008c, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lab/b$c;", "Lab/b;", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.b$c */
    public static final /* data */ class c implements InterfaceC19856b {

        /* renamed from: a, reason: collision with root package name */
        public final int f21009a;

        public c(int i12) {
            this.f21009a = i12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof c) && this.f21009a == ((c) obj).f21009a;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f21009a);
        }

        @k
        public final String toString() {
            return r.t(new StringBuilder("OnSelectTab(tabId="), this.f21009a, ')');
        }
    }

    /* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lab/b$d;", "Lab/b;", "<init>", "()V", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.b$d */
    public static final /* data */ class d implements InterfaceC19856b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f21010a = new d();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof d);
        }

        public final int hashCode() {
            return -486043482;
        }

        @k
        public final String toString() {
            return "OpenAdvertDetails";
        }
    }

    /* compiled from: AdvertDetailStatsMVIOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lab/b$e;", "Lab/b;", "<init>", "()V", "_avito_advert-stats_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ab.b$e */
    public static final /* data */ class e implements InterfaceC19856b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final e f21011a = new e();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof e);
        }

        public final int hashCode() {
            return -2111078813;
        }

        @k
        public final String toString() {
            return "ShowUxFeedback";
        }
    }
}
