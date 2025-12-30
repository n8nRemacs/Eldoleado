package androidx.camera.extensions.internal.sessionprocessor;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.extensions.impl.PreviewImageProcessorImpl;
import androidx.camera.extensions.impl.ProcessResultImpl;
import androidx.camera.extensions.internal.sessionprocessor.o;
import j.B;
import j.N;
import j.X;
import java.util.List;

@X
/* loaded from: classes.dex */
class PreviewProcessor {
    private static final String TAG = "PreviewProcessor";

    @N
    final PreviewImageProcessorImpl mPreviewImageProcessor;

    @N
    final o mCaptureResultImageMatcher = new o();
    final Object mLock = new Object();

    @B
    boolean mIsClosed = false;

    public interface OnCaptureResultCallback {
        void onCaptureResult(long j12, @N List<Pair<CaptureResult.Key, Object>> list);
    }

    public PreviewProcessor(@N PreviewImageProcessorImpl previewImageProcessorImpl, @N Surface surface, @N Size size) {
        this.mPreviewImageProcessor = previewImageProcessorImpl;
        previewImageProcessorImpl.onResolutionUpdate(size);
        previewImageProcessorImpl.onOutputSurface(surface, 1);
        previewImageProcessorImpl.onImageFormatUpdate(35);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0(final OnCaptureResultCallback onCaptureResultCallback, r rVar, TotalCaptureResult totalCaptureResult, int i12) {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    rVar.b();
                    C20140q0.a(TAG);
                    return;
                }
                androidx.camera.extensions.internal.l lVar = androidx.camera.extensions.internal.l.f24636b;
                if (androidx.camera.extensions.internal.d.c(lVar) && androidx.camera.extensions.internal.e.b(lVar)) {
                    this.mPreviewImageProcessor.process(rVar.get(), totalCaptureResult, new ProcessResultImpl() { // from class: androidx.camera.extensions.internal.sessionprocessor.PreviewProcessor.1
                        public void onCaptureCompleted(long j12, @N List<Pair<CaptureResult.Key, Object>> list) {
                            onCaptureResultCallback.onCaptureResult(j12, list);
                        }

                        public void onCaptureProcessProgressed(int i13) {
                        }
                    }, androidx.camera.core.impl.utils.executor.c.c());
                } else {
                    this.mPreviewImageProcessor.process(rVar.get(), totalCaptureResult);
                }
                rVar.b();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void close() {
        synchronized (this.mLock) {
            this.mIsClosed = true;
            this.mCaptureResultImageMatcher.b();
            o oVar = this.mCaptureResultImageMatcher;
            synchronized (oVar.f24641a) {
                oVar.f24645e = null;
            }
        }
    }

    public void notifyCaptureResult(@N TotalCaptureResult totalCaptureResult) {
        this.mCaptureResultImageMatcher.a(totalCaptureResult, 0);
    }

    public void notifyImage(@N r rVar) {
        this.mCaptureResultImageMatcher.c(rVar);
    }

    public void start(@N final OnCaptureResultCallback onCaptureResultCallback) {
        o oVar = this.mCaptureResultImageMatcher;
        o.a aVar = new o.a() { // from class: androidx.camera.extensions.internal.sessionprocessor.t
            @Override // androidx.camera.extensions.internal.sessionprocessor.o.a
            public final void a(r rVar, TotalCaptureResult totalCaptureResult, int i12) {
                this.f24646a.lambda$start$0(onCaptureResultCallback, rVar, totalCaptureResult, i12);
            }
        };
        synchronized (oVar.f24641a) {
            oVar.f24645e = aVar;
        }
    }
}
