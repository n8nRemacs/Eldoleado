package ad0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: VkLinkingStartAction.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u0082\u0001\u0004\u0006\u0007\b\t¨\u0006\n"}, d2 = {"Lad0/a;", "", "a", "b", "c", "d", "Lad0/a$a;", "Lad0/a$b;", "Lad0/a$c;", "Lad0/a$d;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ad0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public interface InterfaceC19870a {

    /* compiled from: VkLinkingStartAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lad0/a$a;", "Lad0/a;", "<init>", "()V", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.a$a, reason: collision with other inner class name */
    public static final class C1500a implements InterfaceC19870a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1500a f21083a = new C1500a();
    }

    /* compiled from: VkLinkingStartAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lad0/a$b;", "Lad0/a;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.a$b */
    public static final /* data */ class b implements InterfaceC19870a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f21084a;

        public b(@k DeepLink deepLink) {
            this.f21084a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f21084a, ((b) obj).f21084a);
        }

        public final int hashCode() {
            return this.f21084a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("DeeplinkAction(deeplink="), this.f21084a, ')');
        }
    }

    /* compiled from: VkLinkingStartAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lad0/a$c;", "Lad0/a;", "<init>", "()V", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.a$c */
    public static final class c implements InterfaceC19870a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f21085a = new c();
    }

    /* compiled from: VkLinkingStartAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lad0/a$d;", "Lad0/a;", "_avito_profile-vk-linking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: ad0.a$d */
    public static final /* data */ class d implements InterfaceC19870a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final String f21086a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f21087b;

        public d(@k String str, @k String str2) {
            this.f21086a = str;
            this.f21087b = str2;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return L.f(this.f21086a, dVar.f21086a) && L.f(this.f21087b, dVar.f21087b);
        }

        public final int hashCode() {
            return this.f21087b.hashCode() + (this.f21086a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("SendVkTokens(code=");
            sb2.append(this.f21086a);
            sb2.append(", state=");
            return C22026a.c(sb2, this.f21087b, ')');
        }
    }
}
