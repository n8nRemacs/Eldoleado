package AK;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.internal.P;
import com.avito.android.iac_dialer.impl_module.active_call_processing.iac_dialer.logic.state.appearance.FgType;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: IacMediaServiceDialerMediatorAction.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007¨\u0006\b"}, d2 = {"LAK/b;", "", "a", "b", "c", "LAK/b$a;", "LAK/b$b;", "LAK/b$c;", "_avito_iac-dialer_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public interface b {

    /* compiled from: IacMediaServiceDialerMediatorAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK/b$a;", "LAK/b;", "_avito_iac-dialer_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final Throwable f285a;

        public a(@k Throwable th2) {
            this.f285a = th2;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && L.f(this.f285a, ((a) obj).f285a);
        }

        public final int hashCode() {
            return this.f285a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.advert.item.delivery_suggests.l.t(new StringBuilder("OnMediaServiceCreatingError(throwable="), this.f285a, ')');
        }
    }

    /* compiled from: IacMediaServiceDialerMediatorAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK/b$b;", "LAK/b;", "_avito_iac-dialer_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: AK.b$b, reason: collision with other inner class name */
    public static final /* data */ class C0016b implements b {

        /* renamed from: a, reason: collision with root package name */
        public final int f286a;

        public C0016b(int i12) {
            this.f286a = i12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C0016b) && this.f286a == ((C0016b) obj).f286a;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f286a);
        }

        @k
        public final String toString() {
            return r.t(new StringBuilder("OnMediaServiceDestroyed(serviceId="), this.f286a, ')');
        }
    }

    /* compiled from: IacMediaServiceDialerMediatorAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK/b$c;", "LAK/b;", "_avito_iac-dialer_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class c implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final FgType f287a;

        /* renamed from: b, reason: collision with root package name */
        public final int f288b;

        public c(@k FgType fgType, int i12) {
            this.f287a = fgType;
            this.f288b = i12;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return L.f(this.f287a, cVar.f287a) && this.f288b == cVar.f288b;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f288b) + (this.f287a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("OnMediaServiceStarted(realFgType=");
            sb2.append(this.f287a);
            sb2.append(", serviceId=");
            return r.t(sb2, this.f288b, ')');
        }
    }
}
