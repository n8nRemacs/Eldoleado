package aj0;

import Y61.k;
import Y61.l;
import androidx.compose.foundation.H;
import com.avito.android.deep_linking.links.DeepLink;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: SelfEmploymentRedirectInternalAction.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0004\b\t\n\u000b¨\u0006\f"}, d2 = {"Laj0/c;", "", "<init>", "()V", "a", "b", "c", "d", "Laj0/c$a;", "Laj0/c$b;", "Laj0/c$c;", "Laj0/c$d;", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public abstract class c {

    /* compiled from: SelfEmploymentRedirectInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Laj0/c$a;", "Laj0/c;", "<init>", "()V", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a extends c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f21130a = new a();

        public a() {
            super(null);
        }

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof a);
        }

        public final int hashCode() {
            return 1305015227;
        }

        @k
        public final String toString() {
            return "Close";
        }
    }

    /* compiled from: SelfEmploymentRedirectInternalAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laj0/c$b;", "Laj0/c;", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class b extends c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final String f21131a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f21132b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final ArrayList f21133c;

        /* renamed from: d, reason: collision with root package name */
        @k
        public final ArrayList f21134d;

        /* renamed from: e, reason: collision with root package name */
        @l
        public final DeepLink f21135e;

        public b(@k String str, @k String str2, @k ArrayList arrayList, @k ArrayList arrayList2, @l DeepLink deepLink) {
            super(null);
            this.f21131a = str;
            this.f21132b = str2;
            this.f21133c = arrayList;
            this.f21134d = arrayList2;
            this.f21135e = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return L.f(this.f21131a, bVar.f21131a) && L.f(this.f21132b, bVar.f21132b) && L.f(this.f21133c, bVar.f21133c) && L.f(this.f21134d, bVar.f21134d) && L.f(this.f21135e, bVar.f21135e);
        }

        public final int hashCode() {
            int iG2 = androidx.compose.ui.graphics.colorspace.e.g(this.f21134d, androidx.compose.ui.graphics.colorspace.e.g(this.f21133c, H.d(this.f21131a.hashCode() * 31, 31, this.f21132b), 31), 31);
            DeepLink deepLink = this.f21135e;
            return iG2 + (deepLink == null ? 0 : deepLink.hashCode());
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("ContentLoaded(title=");
            sb2.append(this.f21131a);
            sb2.append(", description=");
            sb2.append(this.f21132b);
            sb2.append(", steps=");
            sb2.append(this.f21133c);
            sb2.append(", actions=");
            sb2.append(this.f21134d);
            sb2.append(", supportButtonDeepLink=");
            return com.avito.android.actions_sheet.a.v(sb2, this.f21135e, ')');
        }
    }

    /* compiled from: SelfEmploymentRedirectInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Laj0/c$c;", "Laj0/c;", "<init>", "()V", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aj0.c$c, reason: collision with other inner class name */
    public static final /* data */ class C1505c extends c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1505c f21136a = new C1505c();

        public C1505c() {
            super(null);
        }

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof C1505c);
        }

        public final int hashCode() {
            return 1307043787;
        }

        @k
        public final String toString() {
            return "Error";
        }
    }

    /* compiled from: SelfEmploymentRedirectInternalAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Laj0/c$d;", "Laj0/c;", "<init>", "()V", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class d extends c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f21137a = new d();

        public d() {
            super(null);
        }

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof d);
        }

        public final int hashCode() {
            return 2143148887;
        }

        @k
        public final String toString() {
            return "StartLoading";
        }
    }

    public /* synthetic */ c(C42822w c42822w) {
        this();
    }

    public c() {
    }
}
