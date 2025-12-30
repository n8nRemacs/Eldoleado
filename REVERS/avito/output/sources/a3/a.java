package A3;

import Y61.k;
import Y61.l;
import androidx.compose.foundation.H;
import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: AccountEvent.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LA3/a;", "", "a", "b", "LA3/a$a;", "LA3/a$b;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public interface a {

    /* compiled from: AccountEvent.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u0082\u0001\u0004\u0006\u0007\b\t¨\u0006\n"}, d2 = {"LA3/a$a;", "LA3/a;", "a", "b", "c", "d", "LA3/a$a$a;", "LA3/a$a$b;", "LA3/a$a$c;", "LA3/a$a$d;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: A3.a$a, reason: collision with other inner class name */
    public interface InterfaceC0003a extends a {

        /* compiled from: AccountEvent.kt */
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA3/a$a$a;", "LA3/a$a;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: A3.a$a$a, reason: collision with other inner class name */
        public static final /* data */ class C0004a implements InterfaceC0003a {

            /* renamed from: a, reason: collision with root package name */
            @k
            public final String f64a;

            /* renamed from: b, reason: collision with root package name */
            @k
            public final String f65b;

            /* renamed from: c, reason: collision with root package name */
            @l
            public final String f66c;

            public C0004a(String str, String str2, String str3, int i12, C42822w c42822w) {
                str3 = (i12 & 4) != 0 ? null : str3;
                this.f64a = str;
                this.f65b = str2;
                this.f66c = str3;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof C0004a)) {
                    return false;
                }
                C0004a c0004a = (C0004a) obj;
                return L.f(this.f64a, c0004a.f64a) && L.f(this.f65b, c0004a.f65b) && L.f(this.f66c, c0004a.f66c);
            }

            public final int hashCode() {
                int iD2 = H.d(this.f64a.hashCode() * 31, 31, this.f65b);
                String str = this.f66c;
                return iD2 + (str == null ? 0 : str.hashCode());
            }

            @k
            public final String toString() {
                StringBuilder sb2 = new StringBuilder("HashRotated(userId=");
                sb2.append(this.f64a);
                sb2.append(", userHashId=");
                sb2.append(this.f65b);
                sb2.append(", session=");
                return C22026a.c(sb2, this.f66c, ')');
            }
        }

        /* compiled from: AccountEvent.kt */
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA3/a$a$b;", "LA3/a$a;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: A3.a$a$b */
        public static final /* data */ class b implements InterfaceC0003a {

            /* renamed from: a, reason: collision with root package name */
            @l
            public final String f67a;

            /* renamed from: b, reason: collision with root package name */
            @l
            public final String f68b;

            /* renamed from: c, reason: collision with root package name */
            @l
            public final String f69c;

            public b(@l String str, @l String str2, @l String str3) {
                this.f67a = str;
                this.f68b = str2;
                this.f69c = str3;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof b)) {
                    return false;
                }
                b bVar = (b) obj;
                return L.f(this.f67a, bVar.f67a) && L.f(this.f68b, bVar.f68b) && L.f(this.f69c, bVar.f69c);
            }

            public final int hashCode() {
                String str = this.f67a;
                int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
                String str2 = this.f68b;
                int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
                String str3 = this.f69c;
                return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
            }

            @k
            public final String toString() {
                StringBuilder sb2 = new StringBuilder("LoggedIn(userId=");
                sb2.append(this.f67a);
                sb2.append(", userHashId=");
                sb2.append(this.f68b);
                sb2.append(", session=");
                return C22026a.c(sb2, this.f69c, ')');
            }
        }

        /* compiled from: AccountEvent.kt */
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA3/a$a$c;", "LA3/a$a;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: A3.a$a$c */
        public static final /* data */ class c implements InterfaceC0003a {

            /* renamed from: a, reason: collision with root package name */
            @k
            public final String f70a;

            /* renamed from: b, reason: collision with root package name */
            @k
            public final String f71b;

            /* renamed from: c, reason: collision with root package name */
            @k
            public final String f72c;

            public c(@k String str, @k String str2, @k String str3) {
                this.f70a = str;
                this.f71b = str2;
                this.f72c = str3;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof c)) {
                    return false;
                }
                c cVar = (c) obj;
                return L.f(this.f70a, cVar.f70a) && L.f(this.f71b, cVar.f71b) && L.f(this.f72c, cVar.f72c);
            }

            public final int hashCode() {
                return this.f72c.hashCode() + H.d(this.f70a.hashCode() * 31, 31, this.f71b);
            }

            @k
            public final String toString() {
                StringBuilder sb2 = new StringBuilder("ProfileChanged(userId=");
                sb2.append(this.f70a);
                sb2.append(", userHashId=");
                sb2.append(this.f71b);
                sb2.append(", session=");
                return C22026a.c(sb2, this.f72c, ')');
            }
        }

        /* compiled from: AccountEvent.kt */
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA3/a$a$d;", "LA3/a$a;", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: A3.a$a$d */
        public static final /* data */ class d implements InterfaceC0003a {

            /* renamed from: a, reason: collision with root package name */
            @k
            public final String f73a;

            /* renamed from: b, reason: collision with root package name */
            @l
            public final String f74b;

            public d(@k String str, @l String str2) {
                this.f73a = str;
                this.f74b = str2;
            }

            public final boolean equals(@l Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof d)) {
                    return false;
                }
                d dVar = (d) obj;
                dVar.getClass();
                return L.f(null, null) && L.f(null, null) && L.f(this.f73a, dVar.f73a) && L.f(this.f74b, dVar.f74b);
            }

            public final int hashCode() {
                int iHashCode = this.f73a.hashCode() * 31;
                String str = this.f74b;
                return iHashCode + (str == null ? 0 : str.hashCode());
            }

            @k
            public final String toString() {
                StringBuilder sb2 = new StringBuilder("SessionRefreshed(userId=null, userHashId=null, session=");
                sb2.append(this.f73a);
                sb2.append(", prevSession=");
                return C22026a.c(sb2, this.f74b, ')');
            }
        }
    }

    /* compiled from: AccountEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LA3/a$b;", "LA3/a;", "<init>", "()V", "_avito_authorization-plugin_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class b implements a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final b f75a = new b();
    }
}
