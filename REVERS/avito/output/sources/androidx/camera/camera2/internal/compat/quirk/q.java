package androidx.camera.camera2.internal.compat.quirk;

import androidx.camera.core.impl.H0;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.v0;
import j.X;
import java.util.Arrays;
import java.util.HashSet;

/* compiled from: ExtraSupportedSurfaceCombinationsQuirk.java */
@X
/* loaded from: classes.dex */
public class q implements v0 {

    /* renamed from: a, reason: collision with root package name */
    public static final H0 f23219a;

    /* renamed from: b, reason: collision with root package name */
    public static final H0 f23220b;

    /* renamed from: c, reason: collision with root package name */
    public static final H0 f23221c;

    /* renamed from: d, reason: collision with root package name */
    public static final HashSet f23222d;

    /* renamed from: e, reason: collision with root package name */
    public static final HashSet f23223e;

    static {
        H0 h02 = new H0();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.f24042c;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.VGA;
        h02.a(SurfaceConfig.a(configType, configSize));
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.f24041b;
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.PREVIEW;
        h02.a(SurfaceConfig.a(configType2, configSize2));
        SurfaceConfig.ConfigSize configSize3 = SurfaceConfig.ConfigSize.MAXIMUM;
        h02.a(SurfaceConfig.a(configType, configSize3));
        f23219a = h02;
        H0 h03 = new H0();
        androidx.camera.camera2.internal.G.k(configType, configSize, h03, configType, configSize2);
        h03.a(SurfaceConfig.a(configType, configSize3));
        f23220b = h03;
        H0 h04 = new H0();
        androidx.camera.camera2.internal.G.k(configType2, configSize2, h04, configType2, configSize);
        h04.a(SurfaceConfig.a(configType, configSize3));
        h04.a(SurfaceConfig.a(SurfaceConfig.ConfigType.f24044e, configSize3));
        f23221c = h04;
        f23222d = new HashSet(Arrays.asList("SM-A515F", "SM-A515U", "SM-A515U1", "SM-A515W", "SM-S515DL", "SC-54A", "SCG07", "SM-A5160", "SM-A516B", "SM-A516N", "SM-A516U", "SM-A516U1", "SM-A516V", "SM-A715F", "SM-A715W", "SM-A7160", "SM-A716B", "SM-A716U", "SM-A716U1", "SM-A716V", "SM-A8050", "SM-A805F", "SM-A805N", "SCV44", "SM-F9000", "SM-F900F", "SM-F900U", "SM-F900U1", "SM-F900W", "SM-F907B", "SM-F907N", "SM-N970F", "SM-N9700", "SM-N970U", "SM-N970U1", "SM-N970W", "SM-N971N", "SM-N770F", "SC-01M", "SCV45", "SM-N9750", "SM-N975C", "SM-N975U", "SM-N975U1", "SM-N975W", "SM-N975F", "SM-N976B", "SM-N976N", "SM-N9760", "SM-N976Q", "SM-N976V", "SM-N976U", "SM-N9810", "SM-N981N", "SM-N981U", "SM-N981U1", "SM-N981W", "SM-N981B", "SC-53A", "SCG06", "SM-N9860", "SM-N986N", "SM-N986U", "SM-N986U1", "SM-N986W", "SM-N986B", "SC-03L", "SCV41", "SM-G973F", "SM-G973N", "SM-G9730", "SM-G9738", "SM-G973C", "SM-G973U", "SM-G973U1", "SM-G973W", "SM-G977B", "SM-G977N", "SM-G977P", "SM-G977T", "SM-G977U", "SM-G770F", "SM-G770U1", "SC-04L", "SCV42", "SM-G975F", "SM-G975N", "SM-G9750", "SM-G9758", "SM-G975U", "SM-G975U1", "SM-G975W", "SC-05L", "SM-G970F", "SM-G970N", "SM-G9700", "SM-G9708", "SM-G970U", "SM-G970U1", "SM-G970W", "SC-51A", "SC51Aa", "SCG01", "SM-G9810", "SM-G981N", "SM-G981U", "SM-G981U1", "SM-G981V", "SM-G981W", "SM-G981B", "SCG03", "SM-G9880", "SM-G988N", "SM-G988Q", "SM-G988U", "SM-G988U1", "SM-G988W", "SM-G988B", "SC-52A", "SCG02", "SM-G9860", "SM-G986N", "SM-G986U", "SM-G986U1", "SM-G986W", "SM-G986B", "SCV47", "SM-F7000", "SM-F700F", "SM-F700N", "SM-F700U", "SM-F700U1", "SM-F700W", "SCG04", "SM-F7070", "SM-F707B", "SM-F707N", "SM-F707U", "SM-F707U1", "SM-F707W", "SM-F9160", "SM-F916B", "SM-F916N", "SM-F916Q", "SM-F916U", "SM-F916U1", "SM-F916W"));
        f23223e = new HashSet(Arrays.asList("PIXEL 6", "PIXEL 6 PRO", "PIXEL 7", "PIXEL 7 PRO"));
    }
}
