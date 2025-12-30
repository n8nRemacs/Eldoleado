package ae;

import Y61.k;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: SocialButtonClickedEvent.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Lae/c;", "Lcom/avito/android/analytics/provider/clickstream/a;", "a", "_avito_authorization_util"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ae.c, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19875c implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f21105b;

    /* compiled from: SocialButtonClickedEvent.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lae/c$a;", "", "<init>", "()V", "_avito_authorization_util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @P41.c
    /* renamed from: ae.c$a */
    public @interface a {
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public C19875c(@Y61.k java.lang.String r9, @Y61.k java.lang.String r10) {
        /*
            r8 = this;
            r8.<init>()
            com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent r7 = new com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent
            kotlin.Q r0 = new kotlin.Q
            java.lang.String r1 = "pagetype"
            r0.<init>(r1, r9)
            int r9 = r10.hashCode()
            r1 = 3305(0xce9, float:4.631E-42)
            if (r9 == r1) goto L57
            r1 = 3548(0xddc, float:4.972E-42)
            if (r9 == r1) goto L4b
            r1 = 3765(0xeb5, float:5.276E-42)
            if (r9 == r1) goto L3f
            r1 = 93029210(0x58b835a, float:1.3119748E-35)
            if (r9 == r1) goto L33
            r1 = 112209715(0x6b02f33, float:6.6273195E-35)
            if (r9 == r1) goto L27
            goto L5f
        L27:
            java.lang.String r9 = "vk-id"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L30
            goto L5f
        L30:
            r9 = 10
            goto L64
        L33:
            java.lang.String r9 = "apple"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L3c
            goto L5f
        L3c:
            r9 = 8
            goto L64
        L3f:
            java.lang.String r9 = "vk"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L48
            goto L5f
        L48:
            r9 = 1
            goto L64
        L4b:
            java.lang.String r9 = "ok"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L54
            goto L5f
        L54:
            r9 = 3
            goto L64
        L57:
            java.lang.String r9 = "gp"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L62
        L5f:
            r9 = -1
            goto L64
        L62:
            r9 = 5
        L64:
            java.lang.String r9 = java.lang.String.valueOf(r9)
            kotlin.Q r10 = new kotlin.Q
            java.lang.String r1 = "social_id"
            r10.<init>(r1, r9)
            kotlin.Q[] r9 = new kotlin.Q[]{r0, r10}
            java.util.Map r3 = kotlin.collections.P0.g(r9)
            r2 = 0
            r4 = 0
            r1 = 2087(0x827, float:2.925E-42)
            r5 = 8
            r6 = 0
            r0 = r7
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r8.f21105b = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ae.C19875c.<init>(java.lang.String, java.lang.String):void");
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF97978b() {
        return this.f21105b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f21105b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF97979c() {
        return this.f21105b.f90248c;
    }
}
