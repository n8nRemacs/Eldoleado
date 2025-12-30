package Ak;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: B2bBusinessTripSuccessScreenAction.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005¨\u0006\u0006"}, d2 = {"LAk/a;", "", "a", "b", "LAk/a$a;", "LAk/a$b;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ak.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public interface InterfaceC13044a {

    /* compiled from: B2bBusinessTripSuccessScreenAction.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAk/a$a;", "LAk/a;", "<init>", "()V", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.a$a, reason: collision with other inner class name */
    public static final /* data */ class C0023a implements InterfaceC13044a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C0023a f592a = new C0023a();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof C0023a);
        }

        public final int hashCode() {
            return -1789028112;
        }

        @k
        public final String toString() {
            return "OnBackButtonClicked";
        }
    }

    /* compiled from: B2bBusinessTripSuccessScreenAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAk/a$b;", "LAk/a;", "_avito_btob-business-trip_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ak.a$b */
    public static final /* data */ class b implements InterfaceC13044a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f593a;

        public b(@k DeepLink deepLink) {
            this.f593a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f593a, ((b) obj).f593a);
        }

        public final int hashCode() {
            return this.f593a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("SeeResultClicked(deeplink="), this.f593a, ')');
        }
    }
}
