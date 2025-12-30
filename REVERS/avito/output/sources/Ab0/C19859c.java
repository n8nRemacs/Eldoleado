package ab0;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ProfileBasicInfoEditV2Response.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0007\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"Lab0/c;", "", "", "name", "personalLink", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "_avito-discouraged_avito-api_profile-management"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ab0.c, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19859c {

    @com.google.gson.annotations.c("name")
    @l
    private final String name;

    @com.google.gson.annotations.c("personalLink")
    @l
    private final String personalLink;

    public C19859c(@l String str, @l String str2) {
        this.name = str;
        this.personalLink = str2;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final String getPersonalLink() {
        return this.personalLink;
    }
}
