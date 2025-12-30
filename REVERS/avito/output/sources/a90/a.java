package A90;

import com.avito.android.profile.pro.impl.generated.api.profile_tab_v_1.PassportComponentV2ProfilesItem;
import com.avito.android.profile.pro.impl.generated.api.profile_tab_v_1.ReputationAndRatingComponent;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;

/* compiled from: ProfileTabMapperV1.kt */
@s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"_avito_profile-pro_impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final class a {

    /* compiled from: ProfileTabMapperV1.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: A90.a$a, reason: collision with other inner class name */
    public /* synthetic */ class C0010a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f136a;

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int[] f137b;

        static {
            int[] iArr = new int[ReputationAndRatingComponent.ReputationColor.values().length];
            try {
                iArr[ReputationAndRatingComponent.ReputationColor.Red.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ReputationAndRatingComponent.ReputationColor.Yellow.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ReputationAndRatingComponent.ReputationColor.Green.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            f136a = iArr;
            int[] iArr2 = new int[PassportComponentV2ProfilesItem.Status.values().length];
            try {
                iArr2[PassportComponentV2ProfilesItem.Status.Warning.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[PassportComponentV2ProfilesItem.Status.Pending.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[PassportComponentV2ProfilesItem.Status.Finalized.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            f137b = iArr2;
        }
    }
}
