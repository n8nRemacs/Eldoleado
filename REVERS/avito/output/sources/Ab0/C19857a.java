package ab0;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ProfileBasicInfoEditV2Request.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0010\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\n\u0010\u000bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\f\u001a\u0004\b\u000f\u0010\u000eR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\t\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0013\u001a\u0004\b\u0016\u0010\u0015¨\u0006\u0017"}, d2 = {"Lab0/a;", "", "", "name", "personalLink", "Lab0/b;", "servicesProfileType", "", "specificId", "verticalId", "<init>", "(Ljava/lang/String;Ljava/lang/String;Lab0/b;Ljava/lang/Long;Ljava/lang/Long;)V", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "getPersonalLink", "Lab0/b;", "getServicesProfileType", "()Lab0/b;", "Ljava/lang/Long;", "getSpecificId", "()Ljava/lang/Long;", "getVerticalId", "_avito-discouraged_avito-api_profile-management"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ab0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19857a {

    @com.google.gson.annotations.c("name")
    @l
    private final String name;

    @com.google.gson.annotations.c("personalLink")
    @l
    private final String personalLink;

    @com.google.gson.annotations.c("servicesProfileType")
    @l
    private final C19858b servicesProfileType;

    @com.google.gson.annotations.c("specificId")
    @l
    private final Long specificId;

    @com.google.gson.annotations.c("verticalId")
    @l
    private final Long verticalId;

    public C19857a(@l String str, @l String str2, @l C19858b c19858b, @l Long l12, @l Long l13) {
        this.name = str;
        this.personalLink = str2;
        this.servicesProfileType = c19858b;
        this.specificId = l12;
        this.verticalId = l13;
    }
}
