package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Range;
import androidx.camera.core.impl.v0;
import j.N;
import j.P;
import j.X;

/* compiled from: AeFpsRangeLegacyQuirk.java */
@X
/* renamed from: androidx.camera.camera2.internal.compat.quirk.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19990a implements v0 {

    /* renamed from: a, reason: collision with root package name */
    @P
    public final Range<Integer> f23211a;

    public C19990a(@N androidx.camera.camera2.internal.compat.n nVar) {
        Range[] rangeArr = (Range[]) nVar.a(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        Range<Integer> range = null;
        if (rangeArr != null && rangeArr.length != 0) {
            for (Range range2 : rangeArr) {
                Range<Integer> range3 = new Range<>(Integer.valueOf(((Integer) range2.getLower()).intValue() >= 1000 ? ((Integer) range2.getLower()).intValue() / 1000 : ((Integer) range2.getLower()).intValue()), Integer.valueOf(((Integer) range2.getUpper()).intValue() >= 1000 ? ((Integer) range2.getUpper()).intValue() / 1000 : ((Integer) range2.getUpper()).intValue()));
                if (((Integer) range3.getUpper()).intValue() == 30 && (range == null || ((Integer) range3.getLower()).intValue() < ((Integer) range.getLower()).intValue())) {
                    range = range3;
                }
            }
        }
        this.f23211a = range;
    }
}
