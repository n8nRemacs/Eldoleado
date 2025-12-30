package ad0;

import Y61.k;
import Y61.l;
import ad0.InterfaceC19870a;
import androidx.compose.foundation.H;
import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: VkLinkingStartOneTimeEvent.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007¨\u0006\b"}, d2 = {"Lad0/c;", "", "a", "b", "c", "Lad0/c$a;", "Lad0/c$b;", "Lad0/c$c;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ad0.c, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public interface InterfaceC19872c {

    /* compiled from: VkLinkingStartOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lad0/c$a;", "Lad0/c;", "<init>", "()V", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.c$a */
    public static final class a implements InterfaceC19872c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f21099a = new a();
    }

    /* compiled from: VkLinkingStartOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lad0/c$b;", "Lad0/c;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.c$b */
    public static final /* data */ class b implements InterfaceC19872c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final String f21100a;

        public b(@k String str) {
            this.f21100a = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f21100a, ((b) obj).f21100a);
        }

        public final int hashCode() {
            return this.f21100a.hashCode();
        }

        @k
        public final String toString() {
            return C22026a.c(new StringBuilder("ShowSnackbar(message="), this.f21100a, ')');
        }
    }

    /* compiled from: VkLinkingStartOneTimeEvent.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lad0/c$c;", "Lad0/c;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.c$c, reason: collision with other inner class name */
    public static final /* data */ class C1502c implements InterfaceC19872c {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final String f21101a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f21102b;

        /* renamed from: c, reason: collision with root package name */
        @k
        public final InterfaceC19870a.d f21103c;

        public C1502c(@k String str, @k String str2, @k InterfaceC19870a.d dVar) {
            this.f21101a = str;
            this.f21102b = str2;
            this.f21103c = dVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C1502c)) {
                return false;
            }
            C1502c c1502c = (C1502c) obj;
            return L.f(this.f21101a, c1502c.f21101a) && this.f21102b.equals(c1502c.f21102b) && this.f21103c.equals(c1502c.f21103c);
        }

        public final int hashCode() {
            return this.f21103c.hashCode() + H.d(this.f21101a.hashCode() * 31, 31, this.f21102b);
        }

        @k
        public final String toString() {
            return "ShowSnackbarWithAction(message=" + this.f21101a + ", buttonText=" + this.f21102b + ", action=" + this.f21103c + ')';
        }
    }
}
