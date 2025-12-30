package androidx.camera.core.processing;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import j.N;
import java.util.ArrayList;

/* compiled from: TargetUtils.java */
/* loaded from: classes.dex */
public class E {
    @N
    public static String a(int i12) {
        ArrayList arrayList = new ArrayList();
        if ((i12 & 4) != 0) {
            arrayList.add("IMAGE_CAPTURE");
        }
        if ((i12 & 1) != 0) {
            arrayList.add("PREVIEW");
        }
        if ((i12 & 2) != 0) {
            arrayList.add("VIDEO_CAPTURE");
        }
        return String.join(HiAnalyticsConstant.REPORT_VAL_SEPARATOR, arrayList);
    }
}
