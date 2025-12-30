package androidx.camera.extensions.internal.compat.quirk;

import android.os.Build;
import android.util.Pair;
import androidx.camera.core.impl.v0;
import com.adjust.sdk.Constants;
import j.X;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: ImageAnalysisUnavailableQuirk.java */
@X
/* loaded from: classes.dex */
public class g implements v0 {

    /* renamed from: a, reason: collision with root package name */
    public static final HashSet f24629a = new HashSet(Arrays.asList(Pair.create(Constants.REFERRER_API_SAMSUNG, "dm3q"), Pair.create(Constants.REFERRER_API_SAMSUNG, "q2q"), Pair.create(Constants.REFERRER_API_SAMSUNG, "a52sxq"), Pair.create(Constants.REFERRER_API_SAMSUNG, "b0q")));

    public g() {
        HashSet hashSet = new HashSet();
        String str = Build.BRAND;
        if (str.equalsIgnoreCase("SAMSUNG") && Build.DEVICE.equalsIgnoreCase("dm3q")) {
            hashSet.addAll(Arrays.asList(Pair.create("1", 1), Pair.create("1", 4), Pair.create("3", 1), Pair.create("3", 4)));
            return;
        }
        if (str.equalsIgnoreCase("SAMSUNG") && Build.DEVICE.equalsIgnoreCase("q2q")) {
            hashSet.addAll(Arrays.asList(Pair.create("0", 1), Pair.create("0", 4)));
            return;
        }
        if (str.equalsIgnoreCase("SAMSUNG") && Build.DEVICE.equalsIgnoreCase("a52sxq")) {
            hashSet.addAll(Arrays.asList(Pair.create("0", 1), Pair.create("0", 4)));
        } else if (str.equalsIgnoreCase("SAMSUNG") && Build.DEVICE.equalsIgnoreCase("b0q")) {
            hashSet.addAll(Arrays.asList(Pair.create("3", 1), Pair.create("3", 4)));
        }
    }
}
