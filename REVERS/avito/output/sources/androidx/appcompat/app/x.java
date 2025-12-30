package androidx.appcompat.app;

import android.graphics.text.LineBreakConfig;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.inputmethod.EditorBoundsInfo;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class x {
    public static /* synthetic */ LineBreakConfig.Builder b() {
        return new LineBreakConfig.Builder();
    }

    public static /* bridge */ /* synthetic */ DynamicRangeProfiles f(Object obj) {
        return (DynamicRangeProfiles) obj;
    }

    public static /* synthetic */ BoringLayout h(CharSequence charSequence, TextPaint textPaint, int i12, Layout.Alignment alignment, BoringLayout.Metrics metrics, boolean z12, TextUtils.TruncateAt truncateAt, int i13, boolean z13) {
        return new BoringLayout(charSequence, textPaint, i12, alignment, 1.0f, 0.0f, metrics, z12, truncateAt, i13, z13);
    }

    public static /* synthetic */ EditorBoundsInfo.Builder k() {
        return new EditorBoundsInfo.Builder();
    }
}
