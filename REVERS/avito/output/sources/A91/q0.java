package a91;

import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0018\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0015\u001a\u0004\b\u0005\u0010\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0012\u001a\u0004\b\u0017\u0010\u0014R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\t\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0012\u001a\u0004\b\u001b\u0010\u0014R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\r\u001a\u0004\u0018\u00010\f8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010!R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000e\u0010\u0018\u001a\u0004\b\"\u0010\u001aR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010\u0018\u001a\u0004\b#\u0010\u001a¨\u0006$"}, d2 = {"La91/q0;", "", "", "id", "", "isStubName", "label", "", "lastActionTime", "name", "La91/g0;", "publicUserProfile", "", "rating", "statusId", "timeDiff", "<init>", "(Ljava/lang/String;ZLjava/lang/String;Ljava/lang/Long;Ljava/lang/String;La91/g0;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/Long;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Z", "()Z", "b", "Ljava/lang/Long;", "c", "()Ljava/lang/Long;", "d", "La91/g0;", "e", "()La91/g0;", "Ljava/lang/Double;", "getRating", "()Ljava/lang/Double;", "getStatusId", "f", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class q0 {

    @com.google.gson.annotations.c("id")
    @Y61.k
    private final String id;

    @com.google.gson.annotations.c("isStubName")
    private final boolean isStubName;

    @com.google.gson.annotations.c("label")
    @Y61.l
    private final String label;

    @com.google.gson.annotations.c("lastActionTime")
    @Y61.l
    private final Long lastActionTime;

    @com.google.gson.annotations.c("name")
    @Y61.k
    private final String name;

    @com.google.gson.annotations.c("publicUserProfile")
    @Y61.l
    private final g0 publicUserProfile;

    @com.google.gson.annotations.c("rating")
    @Y61.l
    private final Double rating;

    @com.google.gson.annotations.c("statusId")
    @Y61.l
    private final Long statusId;

    @com.google.gson.annotations.c("timeDiff")
    @Y61.l
    private final Long timeDiff;

    public q0(@Y61.k String str, boolean z12, @Y61.l String str2, @Y61.l Long l12, @Y61.k String str3, @Y61.l g0 g0Var, @Y61.l Double d12, @Y61.l Long l13, @Y61.l Long l14) {
        this.id = str;
        this.isStubName = z12;
        this.label = str2;
        this.lastActionTime = l12;
        this.name = str3;
        this.publicUserProfile = g0Var;
        this.rating = d12;
        this.statusId = l13;
        this.timeDiff = l14;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final Long getLastActionTime() {
        return this.lastActionTime;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Y61.l
    /* renamed from: e, reason: from getter */
    public final g0 getPublicUserProfile() {
        return this.publicUserProfile;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final Long getTimeDiff() {
        return this.timeDiff;
    }
}
