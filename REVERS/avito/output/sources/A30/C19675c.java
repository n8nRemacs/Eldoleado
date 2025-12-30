package a30;

import AK0.l;
import Y61.k;
import com.avito.android.bottom_navigation.space.BottomNavigationSpace;
import com.squareup.anvil.annotations.ContributesBinding;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Y;
import kotlin.jvm.internal.m0;
import kotlin.jvm.internal.s0;
import kotlin.properties.h;
import kotlin.reflect.n;

/* compiled from: NavigationConfigStorage.kt */
@s0
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La30/c;", "La30/b;", "_avito_navigation-config_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
@Singleton
/* renamed from: a30.c, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final class C19675c implements InterfaceC19674b {

    /* renamed from: b, reason: collision with root package name */
    public static final /* synthetic */ n<Object>[] f20717b = {m0.f406844a.e(new Y(C19675c.class, "storedStartSpace", "getStoredStartSpace()Ljava/lang/String;", 0))};

    /* renamed from: a, reason: collision with root package name */
    @k
    public final C1481c f20718a;

    /* compiled from: NavigationConfigStorage.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a30.c$a */
    public /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ kotlin.enums.a<BottomNavigationSpace> f20719a = kotlin.enums.c.a(BottomNavigationSpace.values());
    }

    /* compiled from: NavigationConfigStorage.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a30.c$b */
    public /* synthetic */ class b {
        static {
            int[] iArr = new int[BottomNavigationSpace.values().length];
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                BottomNavigationSpace bottomNavigationSpace = BottomNavigationSpace.f107065b;
                iArr[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: SharedPreferencesExtensions.kt */
    @s0
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001¨\u0006\u0004¸\u0006\u0000"}, d2 = {"com/avito/android/util/k5", "Lkotlin/properties/h;", "", "", "_common_preferences_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a30.c$c, reason: collision with other inner class name */
    public static final class C1481c implements h<Object, String> {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ h31.e f20720b;

        public C1481c(h31.e eVar) {
            this.f20720b = eVar;
        }

        @Override // kotlin.properties.g
        public final Object getValue(Object obj, n nVar) {
            return ((l) this.f20720b.get()).getString("startSpace");
        }

        @Override // kotlin.properties.h
        public final void setValue(Object obj, n nVar, String str) {
            ((l) this.f20720b.get()).putString("startSpace", str);
        }
    }

    @Inject
    public C19675c(@k @InterfaceC19673a h31.e<l> eVar) {
        this.f20718a = new C1481c(eVar);
    }

    @Override // a30.InterfaceC19674b
    public final void a() {
        this.f20718a.setValue(this, f20717b[0], null);
    }

    @Override // a30.InterfaceC19674b
    public final void b(@k U20.a aVar) {
        String str;
        BottomNavigationSpace bottomNavigationSpace = aVar.f16133a;
        if (bottomNavigationSpace != null) {
            int iOrdinal = bottomNavigationSpace.ordinal();
            if (iOrdinal == 0) {
                str = "Default";
            } else {
                if (iOrdinal != 1) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "AvitoForBusiness";
            }
        } else {
            str = null;
        }
        this.f20718a.setValue(this, f20717b[0], str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0040, code lost:
    
        r1 = r1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // a30.InterfaceC19674b
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final U20.a c() {
        /*
            r6 = this;
            a30.c$c r0 = r6.f20718a
            kotlin.reflect.n<java.lang.Object>[] r1 = a30.C19675c.f20717b
            r2 = 0
            r1 = r1[r2]
            java.lang.Object r0 = r0.getValue(r6, r1)
            java.lang.String r0 = (java.lang.String) r0
            r1 = 0
            if (r0 == 0) goto L42
            kotlin.enums.a<com.avito.android.bottom_navigation.space.BottomNavigationSpace> r2 = a30.C19675c.a.f20719a
            kotlin.collections.c r2 = (kotlin.collections.AbstractC42738c) r2
            java.util.Iterator r2 = r2.iterator()
        L18:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L40
            java.lang.Object r3 = r2.next()
            r4 = r3
            com.avito.android.bottom_navigation.space.BottomNavigationSpace r4 = (com.avito.android.bottom_navigation.space.BottomNavigationSpace) r4
            int r4 = r4.ordinal()
            if (r4 == 0) goto L37
            r5 = 1
            if (r4 != r5) goto L31
            java.lang.String r4 = "AvitoForBusiness"
            goto L39
        L31:
            kotlin.NoWhenBranchMatchedException r0 = new kotlin.NoWhenBranchMatchedException
            r0.<init>()
            throw r0
        L37:
            java.lang.String r4 = "Default"
        L39:
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L18
            r1 = r3
        L40:
            com.avito.android.bottom_navigation.space.BottomNavigationSpace r1 = (com.avito.android.bottom_navigation.space.BottomNavigationSpace) r1
        L42:
            U20.a r0 = new U20.a
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: a30.C19675c.c():U20.a");
    }
}
