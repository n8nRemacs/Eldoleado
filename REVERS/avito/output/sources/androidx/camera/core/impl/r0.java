package androidx.camera.core.impl;

import android.net.wifi.ScanResult;
import androidx.camera.core.impl.Config;
import androidx.compose.ui.node.LayoutNode;
import com.avito.android.remote.model.AdvertParameters;
import com.avito.android.remote.model.ParcelableEntity;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.my.tracker.obfuscated.C37847v;
import e11.C39907t0;
import e11.V1;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import mZ0.C44034a;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class r0 implements Comparator {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24170b;

    public /* synthetic */ r0(int i12) {
        this.f24170b = i12;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i12 = 0;
        switch (this.f24170b) {
            case 0:
                r0 r0Var = s0.f24171G;
                return ((Config.a) obj).c().compareTo(((Config.a) obj2).c());
            case 1:
                return kotlin.jvm.internal.L.g(((androidx.compose.foundation.lazy.layout.T) obj).getIndex(), ((androidx.compose.foundation.lazy.layout.T) obj2).getIndex());
            case 2:
                LayoutNode layoutNode = (LayoutNode) obj;
                LayoutNode layoutNode2 = (LayoutNode) obj2;
                LayoutNode.d dVar = LayoutNode.f40602T;
                float f12 = layoutNode.f40616J.f40693p.f40826G;
                float f13 = layoutNode2.f40616J.f40693p.f40826G;
                return f12 == f13 ? kotlin.jvm.internal.L.g(layoutNode.L(), layoutNode2.L()) : Float.compare(f12, f13);
            case 3:
                kotlin.Q q12 = (kotlin.Q) obj;
                kotlin.Q q13 = (kotlin.Q) obj2;
                return (((Number) q12.f406620c).intValue() - ((Number) q12.f406619b).intValue()) - (((Number) q13.f406620c).intValue() - ((Number) q13.f406619b).intValue());
            case 4:
                return ((AdvertParameters.Parameter) obj).getTitle().compareTo(((AdvertParameters.Parameter) obj2).getTitle());
            case 5:
                com.google.android.exoplayer2.upstream.cache.f fVar = (com.google.android.exoplayer2.upstream.cache.f) obj;
                com.google.android.exoplayer2.upstream.cache.f fVar2 = (com.google.android.exoplayer2.upstream.cache.f) obj2;
                long j12 = fVar.f347847g;
                long j13 = fVar2.f347847g;
                if (j12 - j13 == 0) {
                    return fVar.compareTo(fVar2);
                }
                return j12 < j13 ? -1 : 1;
            case 6:
                return ((CrashlyticsReport.d) obj).b().compareTo(((CrashlyticsReport.d) obj2).b());
            case 7:
                return C37847v.a((ScanResult) obj, (ScanResult) obj2);
            case 8:
                return ((String) obj).compareTo((String) obj2);
            case 9:
                com.yandex.div.core.state.f fVar3 = (com.yandex.div.core.state.f) obj;
                com.yandex.div.core.state.f fVar4 = (com.yandex.div.core.state.f) obj2;
                long j14 = fVar3.f367662a;
                long j15 = fVar4.f367662a;
                if (j14 != j15) {
                    return (int) (j14 - j15);
                }
                List<kotlin.Q<String, String>> list = fVar3.f367663b;
                int size = list.size();
                List<kotlin.Q<String, String>> list2 = fVar4.f367663b;
                int iMin = Math.min(size, list2.size());
                while (i12 < iMin) {
                    int i13 = i12 + 1;
                    kotlin.Q<String, String> q14 = list.get(i12);
                    kotlin.Q<String, String> q15 = list2.get(i12);
                    int iCompareTo = q14.f406619b.compareTo(q15.f406619b);
                    if (iCompareTo != 0 || q14.f406620c.compareTo(q15.f406620c) != 0) {
                        return iCompareTo;
                    }
                    i12 = i13;
                }
                return list.size() - list2.size();
            case 10:
                return ((String) ((ParcelableEntity) obj).getId()).compareTo((String) ((ParcelableEntity) obj2).getId());
            case 11:
                return Long.compare(((File) obj2).lastModified(), ((File) obj).lastModified());
            case 12:
                return ((ScanResult) obj2).level - ((ScanResult) obj).level;
            case 13:
                return C39907t0.a(((V1) obj2).f394492f, ((V1) obj).f394492f);
            case 14:
                Charset charset = C44034a.f414575d;
                return ((File) obj2).getName().compareTo(((File) obj).getName());
            case 15:
                Charset charset2 = C44034a.f414575d;
                String name = ((File) obj).getName();
                int i14 = C44034a.f414576e;
                return name.substring(0, i14).compareTo(((File) obj2).getName().substring(0, i14));
            case 16:
                return ru.cyberity.ml.facedetector.utils.b.b((ru.cyberity.ml.facedetector.models.c) obj, (ru.cyberity.ml.facedetector.models.c) obj2);
            default:
                return ru.cyberity.ml.facedetector.utils.b.a((ru.cyberity.ml.facedetector.models.c) obj, (ru.cyberity.ml.facedetector.models.c) obj2);
        }
    }
}
