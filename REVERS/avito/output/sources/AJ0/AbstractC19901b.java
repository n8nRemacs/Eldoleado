package aj0;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.registration_self_employment_redirect_screen.models.ButtonAction;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: SelfEmploymentRedirectAction.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0004\b\t\n\u000b¨\u0006\f"}, d2 = {"Laj0/b;", "", "<init>", "()V", "a", "b", "c", "d", "Laj0/b$a;", "Laj0/b$b;", "Laj0/b$c;", "Laj0/b$d;", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aj0.b, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public abstract class AbstractC19901b {

    /* compiled from: SelfEmploymentRedirectAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laj0/b$a;", "Laj0/b;", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aj0.b$a */
    public static final /* data */ class a extends AbstractC19901b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final ButtonAction f21126a;

        public a(@k ButtonAction buttonAction) {
            super(null);
            this.f21126a = buttonAction;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && L.f(this.f21126a, ((a) obj).f21126a);
        }

        public final int hashCode() {
            return this.f21126a.hashCode();
        }

        @k
        public final String toString() {
            return "ButtonClick(buttonAction=" + this.f21126a + ')';
        }
    }

    /* compiled from: SelfEmploymentRedirectAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Laj0/b$b;", "Laj0/b;", "<init>", "()V", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aj0.b$b, reason: collision with other inner class name */
    public static final /* data */ class C1504b extends AbstractC19901b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1504b f21127a = new C1504b();

        public C1504b() {
            super(null);
        }

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof C1504b);
        }

        public final int hashCode() {
            return -1659229602;
        }

        @k
        public final String toString() {
            return "Close";
        }
    }

    /* compiled from: SelfEmploymentRedirectAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Laj0/b$c;", "Laj0/b;", "<init>", "()V", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aj0.b$c */
    public static final /* data */ class c extends AbstractC19901b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f21128a = new c();

        public c() {
            super(null);
        }

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof c);
        }

        public final int hashCode() {
            return -867869247;
        }

        @k
        public final String toString() {
            return "Refresh";
        }
    }

    /* compiled from: SelfEmploymentRedirectAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laj0/b$d;", "Laj0/b;", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aj0.b$d */
    public static final /* data */ class d extends AbstractC19901b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f21129a;

        public d(@k DeepLink deepLink) {
            super(null);
            this.f21129a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof d) && L.f(this.f21129a, ((d) obj).f21129a);
        }

        public final int hashCode() {
            return this.f21129a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("SupportButtonClick(deeplink="), this.f21129a, ')');
        }
    }

    public /* synthetic */ AbstractC19901b(C42822w c42822w) {
        this();
    }

    public AbstractC19901b() {
    }
}
