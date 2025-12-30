package Ak;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: B2bBusinessTripSuccessScreenOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007¨\u0006\b"}, d2 = {"LAk/b;", "", "a", "b", "c", "LAk/b$a;", "LAk/b$b;", "LAk/b$c;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ak.b, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public interface InterfaceC13045b {

    /* compiled from: B2bBusinessTripSuccessScreenOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAk/b$a;", "LAk/b;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.b$a */
    public static final /* data */ class a implements InterfaceC13045b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f594a;

        public a(@k DeepLink deepLink) {
            this.f594a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && L.f(this.f594a, ((a) obj).f594a);
        }

        public final int hashCode() {
            return this.f594a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("LaunchContinueDeeplink(deeplink="), this.f594a, ')');
        }
    }

    /* compiled from: B2bBusinessTripSuccessScreenOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAk/b$b;", "LAk/b;", "<init>", "()V", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.b$b, reason: collision with other inner class name */
    public static final /* data */ class C0024b implements InterfaceC13045b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C0024b f595a = new C0024b();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof C0024b);
        }

        public final int hashCode() {
            return -1770399866;
        }

        @k
        public final String toString() {
            return "NavigateBack";
        }
    }

    /* compiled from: B2bBusinessTripSuccessScreenOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAk/b$c;", "LAk/b;", "<init>", "()V", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.b$c */
    public static final /* data */ class c implements InterfaceC13045b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f596a = new c();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof c);
        }

        public final int hashCode() {
            return 1857398073;
        }

        @k
        public final String toString() {
            return "ShowAnimation";
        }
    }
}
