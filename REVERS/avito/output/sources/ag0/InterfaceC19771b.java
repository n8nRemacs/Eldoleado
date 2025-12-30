package aG0;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: TrxPromoStatusOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007¨\u0006\b"}, d2 = {"LaG0/b;", "", "a", "b", "c", "LaG0/b$a;", "LaG0/b$b;", "LaG0/b$c;", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aG0.b, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public interface InterfaceC19771b {

    /* compiled from: TrxPromoStatusOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/b$a;", "LaG0/b;", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.b$a */
    public static final class a implements InterfaceC19771b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f20845a = new a();
    }

    /* compiled from: TrxPromoStatusOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/b$b;", "LaG0/b;", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.b$b, reason: collision with other inner class name */
    public static final class C1488b implements InterfaceC19771b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1488b f20846a = new C1488b();
    }

    /* compiled from: TrxPromoStatusOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaG0/b$c;", "LaG0/b;", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.b$c */
    public static final /* data */ class c implements InterfaceC19771b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20847a;

        public c(@k DeepLink deepLink) {
            this.f20847a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof c) && L.f(this.f20847a, ((c) obj).f20847a);
        }

        public final int hashCode() {
            return this.f20847a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("HandleDeeplink(deeplink="), this.f20847a, ')');
        }
    }
}
