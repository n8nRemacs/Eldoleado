package AI0;

import Y61.k;
import Y61.l;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: ProfileHeaderOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007¨\u0006\b"}, d2 = {"LAI0/b;", "", "a", "b", "c", "LAI0/b$a;", "LAI0/b$b;", "LAI0/b$c;", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public interface b {

    /* compiled from: ProfileHeaderOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAI0/b$a;", "LAI0/b;", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final II0.c f266a;

        public a(@k II0.c cVar) {
            this.f266a = cVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && L.f(this.f266a, ((a) obj).f266a);
        }

        public final int hashCode() {
            return this.f266a.hashCode();
        }

        @k
        public final String toString() {
            return "PanelConfig(event=" + this.f266a + ')';
        }
    }

    /* compiled from: ProfileHeaderOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAI0/b$b;", "LAI0/b;", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: AI0.b$b, reason: collision with other inner class name */
    public static final /* data */ class C0015b implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final MI0.c f267a;

        public C0015b(@k MI0.c cVar) {
            this.f267a = cVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C0015b) && L.f(this.f267a, ((C0015b) obj).f267a);
        }

        public final int hashCode() {
            return this.f267a.hashCode();
        }

        @k
        public final String toString() {
            return "PanelSoa(event=" + this.f267a + ')';
        }
    }

    /* compiled from: ProfileHeaderOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAI0/b$c;", "LAI0/b;", "<init>", "()V", "_avito_user-adverts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class c implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f268a = new c();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof c);
        }

        public final int hashCode() {
            return -1491232213;
        }

        @k
        public final String toString() {
            return "ScrollToHeader";
        }
    }
}
